package com.example.looksoon.model

data class Message(
    val messageId: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val read: Boolean = false,
    val delivered: Boolean = false,  // NUEVO: mensaje entregado
    val imageUrl: String? = null
)