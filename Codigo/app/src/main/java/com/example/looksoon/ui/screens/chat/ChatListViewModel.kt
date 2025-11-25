package com.example.looksoon.ui.screens.chat

import androidx.lifecycle.ViewModel
import com.example.looksoon.model.Chat
import com.example.looksoon.repository.ChatRepository
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatsListViewModel : ViewModel() {
    private val repository = ChatRepository()
    private var chatsListener: ListenerRegistration? = null

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    init {
        startListeningToChats()
    }

    private fun startListeningToChats() {
        chatsListener = repository.listenToUserChats { newChats ->
            _chats.value = newChats
        }
    }

    override fun onCleared() {
        super.onCleared()
        chatsListener?.remove()
    }
}