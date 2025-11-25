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
                // ✅ OBTENER INFO DEL USUARIO ACTUAL
                val currentUserInfo = getCurrentUserInfo()

                val chat = hashMapOf(
                    "chatId" to chatId,
                    "participants" to listOf(currentUserId, otherUserId),
                    "participantsInfo" to mapOf(
                        // ✅ GUARDAR INFO DEL USUARIO ACTUAL
                        currentUserId to mapOf(
                            "name" to currentUserInfo.name,
                            "profileImage" to currentUserInfo.profileImage,
                            "role" to currentUserInfo.role,
                            "isOnline" to true,
                            "lastSeen" to System.currentTimeMillis()
                        ),
                        // ✅ GUARDAR INFO DEL OTRO USUARIO
                        otherUserId to mapOf(
                            "name" to otherUserName,
                            "profileImage" to "", // Se actualizará cuando abra el chat
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
                // ✅ Si el chat ya existe, actualizar la info del usuario actual por si cambió
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

    // ✅ NUEVA FUNCIÓN: Obtener info del usuario actual
    private suspend fun getCurrentUserInfo(): UserInfo {
        val currentUserId = auth.currentUser?.uid ?: return UserInfo()

        return try {
            // Buscar en todas las colecciones posibles
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

            // Si no se encuentra en ninguna colección
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

            db.collection("Chats")
                .document(chatId)
                .collection("Messages")
                .document(messageId)
                .set(message)
                .await()

            db.collection("Chats")
                .document(chatId)
                .update(
                    mapOf(
                        "lastMessage" to text,
                        "lastMessageTime" to System.currentTimeMillis(),
                        "unreadCount.$receiverId" to FieldValue.increment(1)
                    )
                )
                .await()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun listenToMessages(
        chatId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ): ListenerRegistration {
        return db.collection("Chats")
            .document(chatId)
            .collection("Messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

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

    fun listenToUserChats(onChatsChanged: (List<Chat>) -> Unit): ListenerRegistration {
        val currentUserId = auth.currentUser?.uid ?: return ListenerRegistration { }

        return db.collection("Chats")
            .whereArrayContains("participants", currentUserId)
            .orderBy("lastMessageTime", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val chats = snapshot?.documents?.mapNotNull { doc ->
                    val participants = doc.get("participants") as? List<String> ?: emptyList()
                    val participantsInfoMap = doc.get("participantsInfo") as? Map<String, Map<String, Any>> ?: emptyMap()
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

                onChatsChanged(chats)
            }
    }

    suspend fun markMessagesAsRead(chatId: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        try {
            db.collection("Chats")
                .document(chatId)
                .update("unreadCount.$currentUserId", 0)
                .await()

            val messages = db.collection("Chats")
                .document(chatId)
                .collection("Messages")
                .whereEqualTo("read", false)
                .get()
                .await()

            val batch = db.batch()
            messages.documents.forEach { doc ->
                if (doc.getString("senderId") != currentUserId) {
                    batch.update(doc.reference, "read", true)
                }
            }
            batch.commit().await()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateOnlineStatus(isOnline: Boolean) {
        val currentUserId = auth.currentUser?.uid ?: return

        try {
            val chats = db.collection("Chats")
                .whereArrayContains("participants", currentUserId)
                .get()
                .await()

            val batch = db.batch()
            chats.documents.forEach { doc ->
                batch.update(
                    doc.reference,
                    mapOf(
                        "participantsInfo.$currentUserId.isOnline" to isOnline,
                        "participantsInfo.$currentUserId.lastSeen" to System.currentTimeMillis()
                    )
                )
            }
            batch.commit().await()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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

    fun listenToTypingStatus(
        chatId: String,
        onTypingChanged: (Map<String, Boolean>) -> Unit
    ): ListenerRegistration {
        return db.collection("Chats")
            .document(chatId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                val isTyping = snapshot?.get("isTyping") as? Map<String, Boolean> ?: emptyMap()
                onTypingChanged(isTyping)
            }
    }
}

// ✅ Data class auxiliar
data class UserInfo(
    val name: String = "Usuario",
    val profileImage: String = "",
    val role: String = "Usuario"
)