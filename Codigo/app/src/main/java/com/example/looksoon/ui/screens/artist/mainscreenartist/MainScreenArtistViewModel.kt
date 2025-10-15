package com.example.looksoon.ui.screens.artist.mainscreenartist

import androidx.lifecycle.ViewModel
import com.example.looksoon.ui.screens.login_register.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenArtistViewModel: ViewModel() {
    //MutableStateFlow usado para que pueda cambiar los valores del estado en tiempo real
    val _state = MutableStateFlow(MainScreenArtistState())
    //( )Estado público e inmutable (la UI solo puede leerlo, no modificarlo)
    val state: StateFlow<MainScreenArtistState> = _state.asStateFlow()


}