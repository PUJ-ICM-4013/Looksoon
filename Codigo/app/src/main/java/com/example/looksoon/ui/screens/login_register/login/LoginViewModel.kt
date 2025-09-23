package com.example.looksoon.ui.screens.login_register.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


/*
* Controlar los datos que se muestran en la pantalla
* Logica de negocio
*
* */
class LoginViewModel: ViewModel()  {
    //(_)Estado privado y mutable (solo el ViewModel lo puede modificar)
    //MutableStateFlow usado para que pueda cambiar los valores del estado en tiempo real
    val _state = MutableStateFlow(LoginState())
    //( )Estado público e inmutable (la UI solo puede leerlo, no modificarlo)
    val state: StateFlow<LoginState> = _state.asStateFlow()


    //Funciones

    //Los Update
    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    //Logica de Negocio
    fun checkLogin(
        email: String,
        password: String,
        onArtistClick: () -> Unit,
        onEstablishmentClick: () -> Unit,
        onFanClick: () -> Unit,
        onCuratorClick: () -> Unit
    ) {
        if(_state.value.email.isNotEmpty() && _state.value.password.isNotEmpty()){
            when(_state.value.email){
                "artista" -> {
                    onArtistClick()
                }
                "local" -> {
                    onEstablishmentClick()
                }
                "curador" -> {
                    onCuratorClick()

                }
                "fan" -> {
                    onFanClick()

                }
            }
        }
    }
}