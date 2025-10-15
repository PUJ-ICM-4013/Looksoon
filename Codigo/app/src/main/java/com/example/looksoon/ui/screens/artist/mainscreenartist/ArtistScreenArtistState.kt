package com.example.looksoon.ui.screens.artist.mainscreenartist

import com.google.android.gms.maps.model.LatLng


data class MainScreenArtistState(
    // Ubicación actual del usuario (null hasta que se obtiene)
    val userLocation: LatLng? = null,
    // Si el mapa debe seguir automáticamente la ubicación del usuario
    val followUser: Boolean = true,
    // Lista de marcadores (ubicación y título)
    val markers: List<Pair<LatLng, String>> = emptyList(),
    // Para el modo oscuro/claro del mapa (si quieres usar el sensor de luz como en el ejemplo)
    val isDarkTheme: Boolean = false,
    // Mensaje de error o estado de carga
    val errorMessage: String? = null,

    val currentDestination: LatLng? = null
)