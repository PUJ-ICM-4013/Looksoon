package com.example.looksoon.ui.screens.artist.mainscreenartist

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.looksoon.data.RoutingService
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// --- CAMBIO ESENCIAL ---
// Añadimos "open" para permitir que la clase FakeMainScreenArtistViewModel herede de esta.
open class MainScreenArtistViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainScreenArtistState())
    val state: StateFlow<MainScreenArtistState> = _state.asStateFlow()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    // Se inicializa en el Composable con LocalContext.current
    fun setupLocationClient(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        setupLocationCallback()
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { loc ->
                    val newLatLng = LatLng(loc.latitude, loc.longitude)
                    _state.update { it.copy(userLocation = newLatLng) }
                    removePassedRoutePoints(newLatLng)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(hasLocationPermission: Boolean, context: Context) {
        if (!hasLocationPermission) {
            _state.update { it.copy(errorMessage = "Permiso de ubicación no concedido.") }
            return
        }

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L).build()

        fusedLocationClient.requestLocationUpdates(request, locationCallback, context.mainLooper)

        // Obtener la última ubicación conocida al iniciar
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val pos = LatLng(it.latitude, it.longitude)
                _state.update { currentState -> currentState.copy(userLocation = pos) }
            }
        }
    }

    fun stopLocationUpdates() {
        if (::fusedLocationClient.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    fun toggleFollowUser(follow: Boolean) {
        _state.update { it.copy(followUser = follow) }
    }

    // -------------------- DISTANCIA --------------------
    /**
     * Calcula la distancia entre dos LatLng y la formatea a "xxx m" o "x.xx km".
     */
    private fun calculateDistance(origin: LatLng, dest: LatLng): String {
        val start = Location("start").apply {
            latitude = origin.latitude
            longitude = origin.longitude
        }
        val end = Location("end").apply {
            latitude = dest.latitude
            longitude = dest.longitude
        }
        val meters = start.distanceTo(end)
        return if (meters < 1000) {
            "${meters.toInt()} m"
        } else {
            String.format("%.2f km", meters / 1000.0)
        }
    }

    // --- FUNCIONES PARA RUTAS (AHORA CON DESTINO Y DISTANCIA) ---

    /**
     * Calcula una ruta desde la ubicación actual del usuario hasta un destino.
     * Además guarda el destino y la distancia en el estado para permitir mostrar un marcador
     * y su info (snippet).
     */
    fun calculateRoute(destination: LatLng) {
        val origin = _state.value.userLocation ?: return

        val coordinates = "${origin.longitude},${origin.latitude};${destination.longitude},${destination.latitude}"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RoutingService.api.getRoute(coordinates)
                if (response.routes.isNotEmpty()) {
                    val points = RoutingService.decodePolyline(response.routes[0].geometry)

                    // Calcula la distancia para mostrar como snippet
                    val distanceText = calculateDistance(origin, destination)

                    _state.update {
                        it.copy(
                            routePoints = points,
                            currentDestination = destination,
                            distanceToDestination = distanceText,
                            errorMessage = null
                        )
                    }
                } else {
                    _state.update { it.copy(errorMessage = "No se encontró ruta.") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "No se pudo calcular la ruta.") }
            }
        }
    }

    /**
     * Limpia la ruta del mapa y el destino asociado.
     */
    fun clearRoute() {
        _state.update {
            it.copy(
                routePoints = emptyList(),
                currentDestination = null,
                distanceToDestination = null
            )
        }
    }

    /**
     * Limpia el mensaje de error del estado.
     */
    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
    }

    private fun removePassedRoutePoints(currentPos: LatLng) {
        val points = _state.value.routePoints
        if (points.isEmpty()) return

        // Distancia mínima para considerar un punto como "recorrido"
        val thresholdMeters = 15.0

        var indexToKeepFrom = 0

        for (i in points.indices) {
            val p = points[i]

            val results = FloatArray(1)
            android.location.Location.distanceBetween(
                currentPos.latitude, currentPos.longitude,
                p.latitude, p.longitude,
                results
            )

            // Mientras el usuario esté cerca del punto, lo eliminamos
            if (results[0] < thresholdMeters) {
                indexToKeepFrom = i
            }
        }

        // Si pasó puntos, reducimos la lista
        if (indexToKeepFrom > 0 && indexToKeepFrom < points.size) {
            val remaining = points.drop(indexToKeepFrom)
            _state.update { it.copy(routePoints = remaining) }
        }
    }

}
