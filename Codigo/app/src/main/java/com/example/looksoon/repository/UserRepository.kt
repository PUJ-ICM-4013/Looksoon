package com.example.looksoon.repository

import android.content.Context
import android.net.Uri
import com.example.looksoon.model.*
import com.example.looksoon.utils.CloudinaryUploader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Nombres de colecciones por rol
    private val collectionsByRole = mapOf(
        "Artista" to "Artista",
        "Banda" to "Bandas",
        "Fan" to "Fan",
        "Curador" to "Curador",
        "Local" to "Establecimiento"
    )

    // Obtener usuario actual
    suspend fun getCurrentUser(): User? {
        val currentUserId = auth.currentUser?.uid ?: return null
        return getUserById(currentUserId)
    }

    // Obtener usuario por ID - Busca en todas las colecciones
    suspend fun getUserById(userId: String): User? {
        return try {
            // Buscar en cada colección hasta encontrar el usuario
            for ((roleName, collectionName) in collectionsByRole) {
                val document = db.collection(collectionName).document(userId).get().await()

                if (document.exists()) {
                    return parseUserFromDocument(document, roleName)
                }
            }

            // Si no se encuentra en ninguna colección
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Parsear documento a User según la colección
    private fun parseUserFromDocument(document: DocumentSnapshot, roleName: String): User {
        // Obtener géneros
        val generoPrincipal = document.getString("generoPrincipal") ?: ""
        val subgeneros = document.getString("subgeneros") ?: ""
        val genres = mutableListOf<String>()
        if (generoPrincipal.isNotEmpty()) genres.add(generoPrincipal)
        if (subgeneros.isNotEmpty()) genres.add(subgeneros)

        return User(
            userId = document.id,
            name = document.getString("nombreReal") ?: document.getString("name") ?: "",
            username = document.getString("nombreArtistico") ?: document.getString("username") ?: "",
            email = document.getString("email") ?: "",
            phone = document.getString("telefono") ?: document.getString("phone") ?: "",
            location = document.getString("ciudadBase") ?: document.getString("location") ?: "",
            bio = document.getString("biografia") ?: document.getString("bio") ?: "",
            profileImageUrl = document.getString("profileImageUrl") ?: "",
            roles = listOf(roleName),
            genres = genres,
            eventsCount = document.getLong("eventsCount")?.toInt() ?: 0,
            followersCount = document.getLong("followersCount")?.toInt() ?: 0,
            followingCount = document.getLong("followingCount")?.toInt() ?: 0,
            isOnline = document.getBoolean("isOnline") ?: false,
            lastSeen = document.getLong("lastSeen") ?: 0L,
            artistInfo = if (roleName == "Artista" || roleName == "Banda") parseArtistInfoFromDocument(document) else null,
            localInfo = if (roleName == "Local") parseLocalInfoFromDocument(document) else null,
            curatorInfo = if (roleName == "Curador") parseCuratorInfoFromDocument(document) else null,
            fanInfo = if (roleName == "Fan") parseFanInfoFromDocument(document, genres) else null
        )
    }

    // Actualizar estado en línea - Actualiza en la colección correcta
    suspend fun updateOnlineStatus(isOnline: Boolean) {
        val currentUserId = auth.currentUser?.uid ?: return

        try {
            // Buscar en qué colección está el usuario
            for (collectionName in collectionsByRole.values) {
                val docRef = db.collection(collectionName).document(currentUserId)
                val doc = docRef.get().await()

                if (doc.exists()) {
                    docRef.update(
                        mapOf(
                            "isOnline" to isOnline,
                            "lastSeen" to System.currentTimeMillis()
                        )
                    ).await()
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Subir imagen usando Cloudinary
    suspend fun uploadProfileImage(uri: Uri, context: Context): String {
        return CloudinaryUploader.uploadImage(uri, context)
    }

    // Actualizar perfil completo - Actualiza en la colección correcta
    suspend fun updateUserProfile(
        name: String,
        username: String,
        location: String,
        email: String,
        phone: String,
        bio: String,
        profileImageUrl: String
    ) {
        val userId = auth.currentUser?.uid ?: throw Exception("Usuario no autenticado")

        try {
            // Buscar en qué colección está el usuario
            for (collectionName in collectionsByRole.values) {
                val docRef = db.collection(collectionName).document(userId)
                val doc = docRef.get().await()

                if (doc.exists()) {
                    // Actualizar en ambos formatos para compatibilidad
                    val updates = mutableMapOf<String, Any>(
                        // Formato nuevo
                        "name" to name,
                        "username" to username,
                        "location" to location,
                        "email" to email,
                        "phone" to phone,
                        "bio" to bio,
                        "profileImageUrl" to profileImageUrl,
                        // Formato antiguo
                        "nombreReal" to name,
                        "nombreArtistico" to username,
                        "ciudadBase" to location,
                        "biografia" to bio,
                        "telefono" to phone
                    )

                    docRef.update(updates).await()
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error al actualizar perfil: ${e.message}")
        }
    }

    // Actualizar perfil con mapa genérico - Para compatibilidad
    suspend fun updateUserProfile(userId: String, updates: Map<String, Any>): Boolean {
        return try {
            // Buscar en qué colección está el usuario
            for (collectionName in collectionsByRole.values) {
                val docRef = db.collection(collectionName).document(userId)
                val doc = docRef.get().await()

                if (doc.exists()) {
                    docRef.update(updates).await()
                    return true
                }
            }
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Actualizar géneros musicales
    suspend fun updateGenres(genres: List<String>): Boolean {
        val userId = auth.currentUser?.uid ?: return false

        return try {
            for (collectionName in collectionsByRole.values) {
                val docRef = db.collection(collectionName).document(userId)
                val doc = docRef.get().await()

                if (doc.exists()) {
                    val updates = mutableMapOf<String, Any>()
                    if (genres.isNotEmpty()) {
                        updates["generoPrincipal"] = genres[0]
                        if (genres.size > 1) {
                            updates["subgeneros"] = genres.drop(1).joinToString(", ")
                        }
                    }
                    docRef.update(updates).await()
                    return true
                }
            }
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Parsear info de artista desde documento
    private fun parseArtistInfoFromDocument(document: DocumentSnapshot): ArtistInfo? {
        val redesSociales = document.get("redesSociales") as? Map<String, Any> ?: emptyMap()

        return ArtistInfo(
            artisticName = document.getString("nombreArtistico") ?: "",
            instruments = document.get("instrumentos") as? List<String> ?: emptyList(),
            yearsOfExperience = 0,
            spotifyUrl = redesSociales["spotify"] as? String ?: "",
            youtubeUrl = redesSociales["youtube"] as? String ?: "",
            instagramUrl = redesSociales["instagram"] as? String ?: ""
        )
    }

    // Parsear info de local desde documento
    private fun parseLocalInfoFromDocument(document: DocumentSnapshot): LocalInfo? {
        return LocalInfo(
            venueName = document.getString("nombreLocal") ?: "",
            capacity = document.getLong("capacidad")?.toInt() ?: 0,
            address = document.getString("direccion") ?: "",
            venueType = document.getString("tipoLocal") ?: ""
        )
    }

    // Parsear info de curador desde documento
    private fun parseCuratorInfoFromDocument(document: DocumentSnapshot): CuratorInfo? {
        return CuratorInfo(
            specialty = document.getString("especialidad") ?: "",
            organization = document.getString("organizacion") ?: "",
            certifications = document.get("certificaciones") as? List<String> ?: emptyList()
        )
    }

    // Parsear info de fan desde documento
    private fun parseFanInfoFromDocument(document: DocumentSnapshot, genres: List<String>): FanInfo? {
        return FanInfo(
            favoriteGenres = genres,
            eventsAttended = document.getLong("eventosAsistidos")?.toInt() ?: 0
        )
    }

    // Métodos de parsing antiguos (mantener para compatibilidad)
    private fun parseLocalInfo(data: Map<String, Any>?): LocalInfo? {
        if (data == null) return null
        return LocalInfo(
            venueName = data["venueName"] as? String ?: "",
            capacity = (data["capacity"] as? Long)?.toInt() ?: 0,
            address = data["address"] as? String ?: "",
            venueType = data["venueType"] as? String ?: ""
        )
    }

    private fun parseCuratorInfo(data: Map<String, Any>?): CuratorInfo? {
        if (data == null) return null
        return CuratorInfo(
            specialty = data["specialty"] as? String ?: "",
            organization = data["organization"] as? String ?: "",
            certifications = data["certifications"] as? List<String> ?: emptyList()
        )
    }

    private fun parseFanInfo(data: Map<String, Any>?): FanInfo? {
        if (data == null) return null
        return FanInfo(
            favoriteGenres = data["favoriteGenres"] as? List<String> ?: emptyList(),
            eventsAttended = (data["eventsAttended"] as? Long)?.toInt() ?: 0
        )
    }
}