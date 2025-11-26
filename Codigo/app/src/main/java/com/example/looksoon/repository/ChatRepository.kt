package com.example.looksoon.repository

import com.example.looksoon.model.Chat
import com.example.looksoon.model.Message
import com.example.looksoon.model.ParticipantInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChatRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // ⭐ Crea o obtiene ID del chat entre dos usuarios
    suspend fun getOrCreateChat(
        otherUserId: String,
        otherUserName: String,
        otherUserRole: String
    ): String {
        val currentUserId = auth.currentUser?.uid ?: return ""

        val chatId = if (currentUserId < otherUserId) {
            "${currentUserId}_${otherUserId}"
        } else {
            "${otherUserId}_${currentUserId}"
        }

        return try {
            val chatDoc = db.collection("Chats").document(chatId).get().await()

            if (!chatDoc.exists()) {
                val currentUserInfo = getCurrentUserInfo()

                val chat = hashMapOf(
                    "chatId" to chatId,
                    "participants" to listOf(currentUserId, otherUserId),
                    "participantsInfo" to mapOf(
                        currentUserId to mapOf(
                            "name" to currentUserInfo.name,
                            "profileImage" to currentUserInfo.profileImage,
                            "role" to currentUserInfo.role,
                            "isOnline" to true,
                            "lastSeen" to System.currentTimeMillis()
                        ),
                        otherUserId to mapOf(
                            "name" to otherUserName,
                            "profileImage" to "",
                            "role" to otherUserRole,
                            "isOnline" to false,
                            "lastSeen" to System.currentTimeMillis()
                        )
                    ),
                    "lastMessage" to "",
                    "lastMessageTime" to System.currentTimeMillis(),
                    "unreadCount" to mapOf(
                        currentUserId to 0,
                        otherUserId to 0
                    ),
                    "isTyping" to mapOf(
                        currentUserId to false,
                        otherUserId to false
                    )
                )

                db.collection("Chats").document(chatId).set(chat).await()
            } else {
                val currentUserInfo = getCurrentUserInfo()
                db.collection("Chats").document(chatId).update(
                    mapOf(
                        "participantsInfo.$currentUserId.name" to currentUserInfo.name,
                        "participantsInfo.$currentUserId.profileImage" to currentUserInfo.profileImage,
                        "participantsInfo.$currentUserId.role" to currentUserInfo.role,
                        "participantsInfo.$currentUserId.isOnline" to true,
                        "participantsInfo.$currentUserId.lastSeen" to System.currentTimeMillis()
                    )
                ).await()
            }
            chatId
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    // ⭐ Datos del usuario actual
    private suspend fun getCurrentUserInfo(): UserInfo {
        val currentUserId = auth.currentUser?.uid ?: return UserInfo()

        return try {
            val collections = listOf("Artista", "Bandas", "Fan", "Curador", "Establecimiento")

            for (collection in collections) {
                val userDoc = db.collection(collection).document(currentUserId).get().await()
                if (userDoc.exists()) {
                    return UserInfo(
                        name = userDoc.getString("nombreReal")
                            ?: userDoc.getString("nombreArtistico")
                            ?: userDoc.getString("name")
                            ?: "Usuario",
                        profileImage = userDoc.getString("profileImageUrl") ?: "",
                        role = userDoc.getString("role")?.capitalize() ?: collection
                    )
                }
            }

            UserInfo(
                name = auth.currentUser?.displayName ?: "Usuario",
                profileImage = "",
                role = "Usuario"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            UserInfo()
        }
    }

    // ⭐ Enviar mensaje
    suspend fun sendMessage(chatId: String, text: String, receiverId: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        try {
            val messageId = UUID.randomUUID().toString()
            val message = hashMapOf(
                "messageId" to messageId,
                "senderId" to currentUserId,
                "text" to text,
                "timestamp" to System.currentTimeMillis(),
                "read" to false,
                "delivered" to false
            )

            db.collection("Chats").document(chatId)
                .collection("Messages").document(messageId)
                .set(message).await()

            db.collection("Chats").document(chatId).update(
                mapOf(
                    "lastMessage" to text,
                    "lastMessageTime" to System.currentTimeMillis(),
                    "unreadCount.$receiverId" to FieldValue.increment(1)
                )
            ).await()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ⭐ Escuchar mensajes en un chat específico
    fun listenToMessages(
        chatId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ): ListenerRegistration {
        return db.collection("Chats")
            .document(chatId)
            .collection("Messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                val messages = snapshot?.documents?.mapNotNull { doc ->
                    Message(
                        messageId = doc.getString("messageId") ?: "",
                        senderId = doc.getString("senderId") ?: "",
                        text = doc.getString("text") ?: "",
                        timestamp = doc.getLong("timestamp") ?: 0L,
                        read = doc.getBoolean("read") ?: false,
                        delivered = doc.getBoolean("delivered") ?: false
                    )
                } ?: emptyList()

                onMessagesChanged(messages)
            }
    }

    // ⭐ Escuchar todos los chats del usuario
    fun listenToUserChats(
        onChatsChanged: (List<Chat>?, Exception?) -> Unit
    ): ListenerRegistration {
        val currentUserId = auth.currentUser?.uid
            ?: return ListenerRegistration { }

        return db.collection("Chats")
            .whereArrayContains("participants", currentUserId)
            .orderBy("lastMessageTime", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    onChatsChanged(null, error)
                    return@addSnapshotListener
                }

                val chats = snapshot?.documents?.mapNotNull { doc ->
                    val participants = doc.get("participants") as? List<String> ?: emptyList()
                    val participantsInfoMap =
                        doc.get("participantsInfo") as? Map<String, Map<String, Any>> ?: emptyMap()

                    val unreadCount = doc.get("unreadCount") as? Map<String, Long> ?: emptyMap()
                    val isTypingMap = doc.get("isTyping") as? Map<String, Boolean> ?: emptyMap()

                    val participantsInfo = participantsInfoMap.mapValues { (_, info) ->
                        ParticipantInfo(
                            name = info["name"] as? String ?: "",
                            profileImage = info["profileImage"] as? String ?: "",
                            role = info["role"] as? String ?: "",
                            isOnline = info["isOnline"] as? Boolean ?: false,
                            lastSeen = info["lastSeen"] as? Long ?: 0L
                        )
                    }

                    Chat(
                        chatId = doc.getString("chatId") ?: "",
                        participants = participants,
                        participantsInfo = participantsInfo,
                        lastMessage = doc.getString("lastMessage") ?: "",
                        lastMessageTime = doc.getLong("lastMessageTime") ?: 0L,
                        unreadCount = unreadCount.mapValues { it.value.toInt() },
                        isTyping = isTypingMap
                    )
                } ?: emptyList()

                onChatsChanged(chats, null)
            }
    }

    // ⭐ Marcar mensajes como leídos
    suspend fun markMessagesAsRead(chatId: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        try {
            db.collection("Chats")
                .document(chatId)
                .update("unreadCount.$currentUserId", 0)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ⭐ Marcar mensajes como entregados
    suspend fun markMessagesAsDelivered(chatId: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        try {
            val messages = db.collection("Chats")
                .document(chatId)
                .collection("Messages")
                .whereEqualTo("delivered", false)
                .get()
                .await()

            val batch = db.batch()
            messages.documents.forEach { doc ->
                if (doc.getString("senderId") != currentUserId) {
                    batch.update(doc.reference, "delivered", true)
                }
            }
            batch.commit().await()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ⭐ Escuchar estado "escribiendo" (typing)
    fun listenToTypingStatus(
        chatId: String,
        onTypingChanged: (Map<String, Boolean>) -> Unit
    ): ListenerRegistration {
        return db.collection("Chats").document(chatId)
            .addSnapshotListener { snapshot, _ ->
                val isTyping = snapshot?.get("isTyping") as? Map<String, Boolean> ?: emptyMap()
                onTypingChanged(isTyping)
            }
    }

    // ⭐ ACTUALIZAR estado "escribiendo"
    suspend fun setTypingStatus(chatId: String, isTyping: Boolean) {
        val currentUserId = auth.currentUser?.uid ?: return
        try {
            db.collection("Chats")
                .document(chatId)
                .update("isTyping.$currentUserId", isTyping)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ⭐ ACTUALIZAR estado ONLINE / LAST SEEN
    suspend fun updateOnlineStatus(isOnline: Boolean) {
        val currentUserId = auth.currentUser?.uid ?: return
        try {
            val chats = db.collection("Chats")
                .whereArrayContains("participants", currentUserId)
                .get()
                .await()

            val batch = db.batch()
            chats.documents.forEach { chatDoc ->
                batch.update(
                    db.collection("Chats").document(chatDoc.id),
                    "participantsInfo.$currentUserId.isOnline", isOnline,
                    "participantsInfo.$currentUserId.lastSeen", System.currentTimeMillis()
                )
            }
            batch.commit().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

// ⭐ Modelo auxiliar
data class UserInfo(
    val name: String = "Usuario",
    val profileImage: String = "",
    val role: String = "Usuario"
)
