package com.example.looksoon.ui.screens.artist.mainscreenartist

import com.google.android.gms.maps.model.LatLng

data class MainScreenArtistState(
    // Ubicación actual del usuario (null hasta que se obtiene)
    val userLocation: LatLng? = null,
    // Si el mapa debe seguir automáticamente la ubicación del usuario
    val followUser: Boolean = true,
    // Lista de marcadores genéricos (si los necesitaras para otra cosa)
    val markers: List<Pair<LatLng, String>> = emptyList(),
    // Para el modo oscuro/claro del mapa
    val isDarkTheme: Boolean = false,
    // Mensaje de error o estado de carga
    val errorMessage: String? = null,
    // El punto de destino específico para la ruta. Es un solo punto.
    val currentDestination: LatLng? = null,
    // Lista de coordenadas que forman la polilínea de la ruta a dibujar.
    val routePoints: List<LatLng> = emptyList(),
    // Distancia calculada entre userLocation y currentDestination (por ejemplo "3.4 km" o "450 m")
    val distanceToDestination: String? = null
)
