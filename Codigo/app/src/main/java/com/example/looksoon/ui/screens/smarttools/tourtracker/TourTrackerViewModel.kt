package com.example.looksoon.ui.screens.smarttools.tourtracker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.*
import android.media.MediaRecorder
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.*

data class SensorPerformanceData(
    val energy: Int = 0,
    val audienceEngagement: Int = 0,
    val movementScore: Int = 0,
    val lightIntensity: Float? = null,
    val soundLevel: Int = 0,
    val locationLat: Double? = null,
    val locationLon: Double? = null,
    val compassHeading: Float? = null,
    val averageEnergy: Int? = null
)

class TourTrackerViewModel(application: Application) :
    AndroidViewModel(application), SensorEventListener {

    private val _performanceData = MutableStateFlow(SensorPerformanceData())
    val performanceData: StateFlow<SensorPerformanceData> = _performanceData

    private val context = getApplication<Application>()
    private var isTracking = false

    // Sensores físicos
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val linearAccel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    private val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    private val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    // GPS
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationCallback: LocationCallback? = null

    // Sonido
    private var recorder: MediaRecorder? = null
    private var soundJob: Job? = null

    // Energía promedio
    private val energySamples = mutableListOf<Int>()
    private var avgJob: Job? = null

    // Engagement
    private var previousEngagement = 0f

    override fun onCleared() {
        super.onCleared()
        stopTracking()
    }

    // -----------------------------------------------------------------
    // 🚀 INICIAR Y DETENER SEGUIMIENTO
    // -----------------------------------------------------------------
    fun startTracking() {
        if (isTracking) return
        isTracking = true
        _performanceData.value = SensorPerformanceData()
        registerSensors()
        startLocationUpdates()
        startSoundTracking()

        avgJob = viewModelScope.launch {
            while (isActive) {
                delay(15_000)
                val avg = if (energySamples.isNotEmpty()) energySamples.average().toInt() else 0
                _performanceData.value = _performanceData.value.copy(averageEnergy = avg)
                energySamples.clear()
            }
        }
    }

    fun stopTracking() {
        if (!isTracking) return
        isTracking = false
        stopLocationUpdates()
        stopSoundTracking()
        sensorManager.unregisterListener(this)
        avgJob?.cancel()
    }

    // -----------------------------------------------------------------
    // 📍 UBICACIÓN
    // -----------------------------------------------------------------
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (fine != PackageManager.PERMISSION_GRANTED && coarse != PackageManager.PERMISSION_GRANTED) return

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L)
            .setMinUpdateIntervalMillis(1000L)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val loc = result.lastLocation ?: return
                _performanceData.value = _performanceData.value.copy(
                    locationLat = loc.latitude,
                    locationLon = loc.longitude
                )
            }
        }
        fusedLocationClient.requestLocationUpdates(request, locationCallback!!, null)
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }

    // -----------------------------------------------------------------
    // 🎤 SONIDO
    // -----------------------------------------------------------------
    private fun startSoundTracking() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) return

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile("/dev/null")
            try {
                prepare()
                start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        soundJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(500)
                try {
                    val amplitude = recorder?.maxAmplitude ?: 0
                    val dB = if (amplitude > 0) (20 * log10(amplitude.toDouble() / 32768)).toInt() else 0
                    _performanceData.value = _performanceData.value.copy(soundLevel = dB)
                } catch (_: Exception) {
                }
            }
        }
    }

    private fun stopSoundTracking() {
        try {
            recorder?.stop()
            recorder?.release()
            recorder = null
            soundJob?.cancel()
        } catch (_: Exception) {
        }
    }

    // -----------------------------------------------------------------
    // 🧭 SENSORES
    // -----------------------------------------------------------------
    private fun registerSensors() {
        linearAccel?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
        magnetometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        lightSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (!isTracking) return

        when (event.sensor.type) {
            Sensor.TYPE_LINEAR_ACCELERATION -> updateMovement(event.values)
            Sensor.TYPE_LIGHT -> updateLight(event.values[0])
            Sensor.TYPE_MAGNETIC_FIELD -> updateCompass(event.values)
        }
    }

    private fun updateMovement(values: FloatArray) {
        // Magnitud total del movimiento
        val magnitude = sqrt(values[0].pow(2) + values[1].pow(2) + values[2].pow(2))

        // Escala más exigente: 0 (quieto), 70 (trotando), 100 (corriendo)
        val scaled = (magnitude * 15f).coerceIn(0f, 100f)
        val energy = (scaled * 0.9 + (0..10).random()).toInt().coerceIn(0, 100)

        energySamples.add(energy)

        val sound = _performanceData.value.soundLevel
        val light = _performanceData.value.lightIntensity ?: 0f

        // Engagement basado en movimiento + sonido + luz
        val engagement = (
                0.5f * scaled +
                        0.3f * (sound.coerceAtLeast(0)) +
                        0.2f * (light / 10f)
                ).coerceIn(0f, 100f)

        val smoothEngagement = (0.7f * previousEngagement + 0.3f * engagement)
        previousEngagement = smoothEngagement

        _performanceData.value = _performanceData.value.copy(
            movementScore = scaled.toInt(),
            audienceEngagement = smoothEngagement.toInt(),
            energy = energy
        )
    }

    private fun updateLight(lux: Float) {
        _performanceData.value = _performanceData.value.copy(lightIntensity = lux)
    }

    private fun updateCompass(values: FloatArray) {
        val heading = sqrt(values[0].pow(2) + values[1].pow(2) + values[2].pow(2))
        _performanceData.value = _performanceData.value.copy(compassHeading = heading)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
