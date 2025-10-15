package com.example.looksoon.ui.screens.artist.mainscreenartist


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainScreenArtistViewModel : ViewModel() {

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
                for (loc in result.locations) {
                    val newLatLng = LatLng(loc.latitude, loc.longitude)
                    _state.update { it.copy(userLocation = newLatLng) }
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

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L).build()

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

    /**
     * Busca una dirección por texto usando Geocoder y añade un marcador.
     */
    fun searchAddress(context: Context, address: String) {
        if (address.isBlank()) return
        viewModelScope.launch {
            val result: Pair<LatLng, String>? = withContext(Dispatchers.IO) {
                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocationName(address, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val a = addresses[0]
                        Pair(LatLng(a.latitude, a.longitude), a.getAddressLine(0) ?: address)
                    } else null
                } catch (e: Exception) {
                    null
                }
            }

            result?.let { (latLng, title) ->
                val newMarkers = _state.value.markers.toMutableList()
                newMarkers.add(latLng to title)
                _state.update { it.copy(markers = newMarkers.toList()) }
            }
        }
    }

    // añadir aquí la lógica del sensor de luz 
    // fun setDarkTheme(isDark: Boolean) { ... }

}