package com.example.looksoon.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.looksoon.model.User
import com.example.looksoon.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Cargar perfil del usuario actual
    fun loadCurrentUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val userData = userRepository.getCurrentUser()
                _user.value = userData

                if (userData == null) {
                    _error.value = "No se pudo cargar el perfil"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Cargar perfil de otro usuario
    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val userData = userRepository.getUserById(userId)
                _user.value = userData

                if (userData == null) {
                    _error.value = "Usuario no encontrado"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Actualizar estado en línea
    fun updateOnlineStatus(isOnline: Boolean) {
        viewModelScope.launch {
            userRepository.updateOnlineStatus(isOnline)
        }
    }

    // Limpiar error
    fun clearError() {
        _error.value = null
    }
}