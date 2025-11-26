package com.example.looksoon.ui.screens.login_register.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.looksoon.utils.NotificationUtils
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

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email, error = null)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password, error = null)
    }


    // Función de login
    fun checkLogin(
        context: Context,
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
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result?.user?.getIdToken(false)?.addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                val idToken = tokenTask.result?.token
                                if (idToken != null) {
                                    sendTokenAndNavigate(
                                        context,
                                        idToken,
                                        onArtistClick,
                                        onEstablishmentClick,
                                        onFanClick,
                                        onCuratorClick
                                    )
                                }
                            } else {
                                _state.value = _state.value.copy(isLoading = false, navigate = false)
                            }
                        }
                    } else {
                        _state.value = _state.value.copy(isLoading = false, navigate = false)
                    }

                }


    private fun sendTokenAndNavigate(
        context: Context,
        idToken: String,
        onArtistClick: () -> Unit,
        onEstablishmentClick: () -> Unit,
        onFanClick: () -> Unit,
        onCuratorClick: () -> Unit
    ) {
        val roleFromBackend = getRoleFromBackend(idToken)
        _state.value = _state.value.copy(isLoading = false, navigate = true)

        when (roleFromBackend) {
            "artista" -> {
                NotificationUtils.sendEventNotification(
                    context = context,
                    title = "¡No te olvides, Artista!",
                    content = "En 2 días es la ponti fiesta de cierre de semestre. ¡No pierdas la oportunidad de entrar en la convocatoria!"
                )
                onArtistClick()
            }
            "local" -> {
                NotificationUtils.sendEventNotification(
                    context = context,
                    title = "¡Prepara tu escenario!",
                    content = "La ponti fiesta se acerca. No te quedes sin música, encuentra y reserva a los mejores artistas para tu evento."
                )
                onEstablishmentClick()
            }
            "curador" -> onCuratorClick()
            "fan" -> onFanClick()
            else -> onFanClick()
        }
    }

    private fun getRoleFromBackend(token: String): String {
        val email = auth.currentUser?.email?.lowercase()
        return when (email) {
            "local@gmail.com", "local@example.com" -> "local"
            "curador@gmail.com", "curador@example.com" -> "curador"
            "artista@gmail.com", "artista@example.com" -> "artista"
            "fan@gmail.com" -> "fan"
            else -> "fan"

        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}