package com.example.looksoon.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel compartido para manejar estados que necesitan persistir
 * a través de diferentes pantallas durante una misma sesión de navegación.
 */
class SharedViewModel : ViewModel() {

    // Estado privado para controlar si la notificación ya se mostró.
    private val _notificationShown = MutableStateFlow(false)
    // Estado público inmutable para que la UI lo observe.
    val notificationShown = _notificationShown.asStateFlow()

    /**
     * Marca la notificación como mostrada para que no vuelva a aparecer
     * en la misma sesión.
     */
    fun markNotificationAsShown() {
        _notificationShown.update { true }
    }
}
