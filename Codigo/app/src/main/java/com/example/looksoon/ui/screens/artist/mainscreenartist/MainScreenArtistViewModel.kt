package com.example.looksoon.ui.screens.artist.mainscreenartist

import android.Manifest
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

    // --- FUNCIONES PARA RUTAS (SIN MARCADORES) ---

    /**
     * Calcula una ruta desde la ubicación actual del usuario hasta un destino.
     */
    fun calculateRoute(destination: LatLng) {
        val origin = _state.value.userLocation ?: return

        val coordinates = "${origin.longitude},${origin.latitude};${destination.longitude},${destination.latitude}"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RoutingService.api.getRoute(coordinates)
                if (response.routes.isNotEmpty()) {
                    val points = RoutingService.decodePolyline(response.routes[0].geometry)
                    _state.update { it.copy(routePoints = points, errorMessage = null) }
                } else {
                    _state.update { it.copy(errorMessage = "No se encontró ruta.") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "No se pudo calcular la ruta.") }
            }
        }
    }

    /**
     * Limpia la ruta del mapa.
     */
    fun clearRoute() {
        _state.update { it.copy(routePoints = emptyList()) }
    }

    /**
     * Limpia el mensaje de error del estado.
     */
    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
    }
}
