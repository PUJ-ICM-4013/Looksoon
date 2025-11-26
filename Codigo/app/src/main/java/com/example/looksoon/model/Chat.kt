package com.example.looksoon.model

data class Chat(
    val chatId: String = "",
    val participants: List<String> = emptyList(),
    val participantsInfo: Map<String, ParticipantInfo> = emptyMap(),
    val lastMessage: String = "",
    val lastMessageTime: Long = 0L,
    val unreadCount: Map<String, Int> = emptyMap(),
    val isTyping: Map<String, Boolean> = emptyMap()  // NUEVO: quién está escribiendo
)

data class ParticipantInfo(
    val name: String = "",
    val profileImage: String = "",
    val role: String = "",
    val isOnline: Boolean = false,  // NUEVO: está en línea
    val lastSeen: Long = 0L  // NUEVO: última conexión
)