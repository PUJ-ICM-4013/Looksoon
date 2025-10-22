package com.example.looksoon.ui.screens.login_register.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Estado privado mutable
    private val _state = MutableStateFlow(LoginState())

    // Estado público inmutable
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // Actualizar email
    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    // Actualizar contraseña
    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    // ... (imports y propiedades)

    // Función de login
    fun checkLogin(
        email: String,
        password: String,
        onArtistClick: () -> Unit,
        onEstablishmentClick: () -> Unit,
        onFanClick: () -> Unit,
        onCuratorClick: () -> Unit
    ) {
        if (email.isEmpty() || password.isEmpty()) return

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    // NOTA: Deja 'isLoading' en true hasta después de la llamada al backend

                    if (task.isSuccessful) {
                        val user = auth.currentUser

                        // 1. Obtener el Token ID de Firebase
                        user?.getIdToken(false)?.addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                val idToken = tokenTask.result?.token
                                if (idToken != null) {
                                    // 2. Llama a la función que enviará el token a tu backend
                                    // Esta función será la única nueva que te permitirá obtener el bono.
                                    sendTokenAndNavigate(
                                        idToken,
                                        onArtistClick,
                                        onEstablishmentClick,
                                        onFanClick,
                                        onCuratorClick
                                    )
                                }
                            }
                        }
                    } else {
                        // Solo aquí se pone isLoading = false si Firebase falla
                        _state.value = _state.value.copy(isLoading = false, navigate = false)
                        // (Mostrar error de credenciales inválidas)
                    }
                }
        }
    }

    // **NUEVA FUNCIÓN NECESARIA PARA EL BONO**
    private fun sendTokenAndNavigate(
        idToken: String,
        onArtistClick: () -> Unit,
        onEstablishmentClick: () -> Unit,
        onFanClick: () -> Unit,
        onCuratorClick: () -> Unit
    ) {
        // 1. Aquí va la llamada con Retrofit a tu API REST:
        // val roleResponse = yourApi.verifyTokenAndGetRole("Bearer $idToken")

        // **SIMULACIÓN** (Reemplazar con la respuesta real de tu backend)
        // Tu backend debe verificar el 'idToken' y devolverte el rol.
        val roleFromBackend = getRoleFromBackend(idToken)

        // Finaliza la carga
        _state.value = _state.value.copy(isLoading = false, navigate = true)

        // 2. Navegación basada en la respuesta del Backend (el rol)
        when (roleFromBackend) {
            "artista" -> onArtistClick()
            "local" -> onEstablishmentClick()
            "curador" -> onCuratorClick()
            "fan" -> onFanClick()
        }
    }

    // Lógica temporal para simular la respuesta del backend (QUITAR EN PRODUCCIÓN)
    // En LoginViewModel.kt

    // Lógica temporal para simular la respuesta del backend (QUITAR EN PRODUCCIÓN)
    private fun getRoleFromBackend(token: String): String {
        // Obtener el email actual del usuario, convertirlo a minúsculas y usar el operador Elvis
        // para devolver null si no existe (el cual será manejado por el 'when' final).
        val email = auth.currentUser?.email?.lowercase()

        // Usamos 'when' para evaluar el email y devolver el rol correspondiente.
        return when (email) {
            // Establecimiento
            "local@gmail.com" -> "local"
            "local@example.com" -> "local"

            // Curador
            "curador@gmail.com" -> "curador"
            "curador@example.com" -> "curador"

            // Artista
            "artista@gmail.com" -> "artista"
            "artista@example.com" -> "artista"

            // Fan (incluye el caso de 'andrestorres10b@ejemplo.com' si no se registró en otro rol)
            // Puedes añadir emails específicos para Fan si lo deseas:
            "fan@gmail.com" -> "fan"

            // Si el email no coincide con ninguno de los roles anteriores,
            // asumimos que es un Fan o un email no reconocido.
            else -> "fan"
        }
    }
}
