package com.example.looksoon.ui.screens.login_register.forgot_password

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForgotPasswordViewModel: ViewModel() {
    val _state = MutableStateFlow(ForgotPasswordState())

    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    //Los Update

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun enviarMensaje(){
        //Verificar que no esté vacío el correo y que exista en la base de datos
        if(_state.value.email.isNotEmpty()){


            //Navegar a la pantalla de confirmación del mensaje para que lo pueda confirmar
        }
        //Mostrar mensaje de error
        else{

        }

    }


}