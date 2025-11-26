package com.example.looksoon.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.looksoon.model.Message
import com.example.looksoon.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository()
    private var messageListener: ListenerRegistration? = null
    private var typingListener: ListenerRegistration? = null
    private var typingJob: Job? = null
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // NUEVO: Estado de "está escribiendo"
    private val _isOtherUserTyping = MutableStateFlow(false)
    val isOtherUserTyping: StateFlow<Boolean> = _isOtherUserTyping.asStateFlow()

    fun startListeningToMessages(chatId: String) {
        messageListener?.remove()
        messageListener = repository.listenToMessages(chatId) { newMessages ->
            _messages.value = newMessages

            // Marcar mensajes como entregados cuando se reciben
            viewModelScope.launch {
                repository.markMessagesAsDelivered(chatId)
            }
        }

        // Escuchar el estado de escritura
        typingListener?.remove()
        typingListener = repository.listenToTypingStatus(chatId) { typingMap ->
            // Verificar si el otro usuario está escribiendo
            val otherUserId = typingMap.keys.firstOrNull { it != currentUserId }
            _isOtherUserTyping.value = otherUserId?.let { typingMap[it] } ?: false
        }
    }

    fun sendMessage(chatId: String, text: String, receiverId: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true

            // Detener el indicador de escritura antes de enviar
            setTyping(chatId, false)

            repository.sendMessage(chatId, text.trim(), receiverId)
            _isLoading.value = false
        }
    }

    fun markAsRead(chatId: String) {
        viewModelScope.launch {
            repository.markMessagesAsRead(chatId)
        }
    }

    // NUEVO: Manejar el indicador de escritura
    fun onTextChanged(chatId: String, text: String) {
        // Cancelar el job anterior
        typingJob?.cancel()

        if (text.isNotEmpty()) {
            // Activar el indicador
            setTyping(chatId, true)

            // Desactivar después de 3 segundos de inactividad
            typingJob = viewModelScope.launch {
                delay(3000)
                setTyping(chatId, false)
            }
        } else {
            // Si el texto está vacío, desactivar inmediatamente
            setTyping(chatId, false)
        }
    }

    private fun setTyping(chatId: String, isTyping: Boolean) {
        viewModelScope.launch {
            repository.setTypingStatus(chatId, isTyping)
        }
    }

    // NUEVO: Actualizar estado en línea al entrar al chat
    fun setOnlineStatus(isOnline: Boolean) {
        viewModelScope.launch {
            repository.updateOnlineStatus(isOnline)
        }
    }

    override fun onCleared() {
        super.onCleared()
        messageListener?.remove()
        typingListener?.remove()
        typingJob?.cancel()
    }
}