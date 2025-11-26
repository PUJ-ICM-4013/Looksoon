package com.example.looksoon.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.looksoon.model.Chat
import com.example.looksoon.repository.ChatRepository
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatsListViewModel(
    private val repository: ChatRepository = ChatRepository() // Puedes inyectarlo luego con Hilt
) : ViewModel() {

    private var chatsListener: ListenerRegistration? = null

    // Estado de chats
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    // Estado de errores (opcional)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        startListeningToChats()
    }

    // 📌 SOLUCIÓN: esta función evita el error 'Unresolved reference setChats'
    fun setChats(chatList: List<Chat>) {
        _chats.value = chatList
    }

    private fun startListeningToChats() {
        chatsListener = repository.listenToUserChats { newChats, exception ->
            viewModelScope.launch {
                if (exception != null) {
                    _error.value = "Error cargando los chats: ${exception.message}"
                } else {
                    _chats.value = newChats ?: emptyList()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        chatsListener?.remove() // Para evitar fugas de memoria
    }
}
