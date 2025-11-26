package com.example.looksoon.ui.screens.login_register.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.looksoon.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val userRepository = UserRepository()

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // Actualizar email
    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email, error = null)
    }

    // Actualizar contraseña
    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password, error = null)
    }

    // Función de login
    fun checkLogin(
        email: String,
        password: String,
        onArtistClick: () -> Unit,
        onEstablishmentClick: () -> Unit,
        onFanClick: () -> Unit,
        onCuratorClick: () -> Unit
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            _state.value = _state.value.copy(error = "Por favor completa todos los campos")
            return
        }

        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                // 1. Autenticar con Firebase
                auth.signInWithEmailAndPassword(email, password).await()

                val user = auth.currentUser
                if (user == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Error al iniciar sesión"
                    )
                    return@launch
                }

                // 2. ✅ ACTUALIZAR ESTADO EN LÍNEA
                userRepository.updateOnlineStatus(true)

                // 3. Obtener el rol del usuario
                val roleFromBackend = getRoleFromBackend(email)

                _state.value = _state.value.copy(isLoading = false, navigate = true)

                // 4. Navegación basada en el rol
                when (roleFromBackend) {
                    "artista" -> onArtistClick()
                    "local" -> onEstablishmentClick()
                    "curador" -> onCuratorClick()
                    "fan" -> onFanClick()
                    else -> onArtistClick() // Por defecto
                }

            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("password") == true -> "Contraseña incorrecta"
                    e.message?.contains("user") == true ||
                            e.message?.contains("email") == true -> "Usuario no encontrado"
                    e.message?.contains("network") == true -> "Error de conexión"
                    else -> "Error al iniciar sesión: ${e.message}"
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    navigate = false,
                    error = errorMessage
                )
            }
        }
    }

    // Obtener rol del backend (simulación temporal)
    private fun getRoleFromBackend(email: String): String {
        val emailLower = email.lowercase()

        return when {
            emailLower.contains("local") || emailLower.contains("establecimiento") -> "local"
            emailLower.contains("curador") -> "curador"
            emailLower.contains("artista") -> "artista"
            emailLower.contains("fan") -> "fan"
            else -> "fan" // Por defecto
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}