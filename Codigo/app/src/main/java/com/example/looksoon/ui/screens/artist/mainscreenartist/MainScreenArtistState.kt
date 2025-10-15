package com.example.looksoon.ui.screens.artist.mainscreenartist

import com.example.looksoon.ui.screens.establishment.Artist

data class MainScreenArtistState(
    val isLoading: Boolean = false,
    val error: String = "",
    val artist: Artist? = null //Cambiar atributo
)
