package com.example.looksoon.ui.screens.login_register.SignUp

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.looksoon.utils.CloudinaryUploader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    companion object {
        private const val TAG = "SignUpViewModel"
    }

    // --- ESTADO ---
    data class SignUpState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String? = null
    )

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    // --- DATA CLASSES PARA PERFILES ---
    sealed class ProfileData {

        data class ArtistData(
            val artistName: String,
            val realName: String,
            val city: String,
            val genre: String,
            val subgenres: String,
            val bio: String,
            val instagram: String,
            val youtube: String,
            val spotify: String,
            val tiktok: String,
            val profileImageUri: String?
        ) : ProfileData()

        data class BandData(
            val bandName: String,
            val city: String,
            val year: String,
            val genre: String,
            val subgenres: String,
            val bio: String,
            val membersCount: String,
            val manager: String,
            val managerPhone: String,
            val managerEmail: String,
            val instagram: String,
            val youtube: String,
            val spotify: String,
            val tiktok: String,
            val website: String,
            val bandImage: String?
        ) : ProfileData()

        data class FanData(
            val FanName: String,
            val FanUsername: String,
            val FanBirthday: String,
            val FanCountry: String,
            val FanCity: String,
            val FanBio: String,
            val FanImage: String?,
            val FanGenres: String
        ) : ProfileData()

        data class CuratorData(
            val curatorName: String,
            val curatorUsername: String,
            val curatorCountry: String,
            val curatorCity: String,
            val curatorExperience: String,
            val curatorSpecialties: String,
            val curatorGenres: String,
            val curatorBio: String,
            val curatorPortfolio: String,
            val curatorLinkedIn: String,
            val curatorWebsite: String,
            val curatorInstagram: String,
            val curatorTiktok: String,
            val curatorImage: String?
        ) : ProfileData()

        data class EstablishmentData(
            val EstablishmentName: String,
            val EstablishmentUsername: String,
            val EstablishmentImage: String?,
            val EstablishmentPhonePrimary: String,
            val EstablishmentPhoneSecondary: String,
            val EstablishmentWebsite: String,
            val EstablishmentInstagram: String,
            val EstablishmentFacebook: String,
            val EstablishmentTikTok: String,
            val EstablishmentTwitter: String,
            val EstablishmentCountry: String,
            val EstablishmentCity: String,
            val EstablishmentAddress: String,
            val EstablishmentZipCode: String,
            val EstablishmentType: String,
            val EstablishmentCapacity: String,
            val EstablishmentFoundationYear: String,
            val EstablishmentDescription: String,
            val EstablishmentServices: String,
            val EstablishmentOpeningHours: String,
            val EstablishmentClosingHours: String,
            val EstablishmentPaymentMethods: String
        ) : ProfileData()
    }

    // --- REGISTRO PRINCIPAL ---
    fun registerUser(
        email: String,
        password: String,
        role: String,
        profileData: ProfileData,
        context: Context? = null, // Agregar contexto para Cloudinary
        onSuccess: () -> Unit
    ) {
        // Validaciones básicas
        if (email.isEmpty() || password.isEmpty()) {
            _state.value = _state.value.copy(
                errorMessage = "El email y la contraseña son obligatorios."
            )
            return
        }

        if (password.length < 6) {
            _state.value = _state.value.copy(
                errorMessage = "La contraseña debe tener al menos 6 caracteres."
            )
            return
        }

        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                Log.d(TAG, "Iniciando registro para: $email con rol: $role")

                // 1. Subir imagen a Cloudinary si existe
                val imageUrl = uploadProfileImage(context, profileData)
                val updatedProfileData = updateProfileDataWithImageUrl(profileData, imageUrl)

                // 2. Crear usuario en Firebase Auth
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val uid = authResult.user?.uid

                if (uid != null) {
                    Log.d(TAG, "Usuario creado en Auth con UID: $uid")

                    // 3. Guardar perfil en Firestore
                    saveProfileToFirestore(uid, email, role, updatedProfileData)

                    Log.d(TAG, "Perfil guardado exitosamente en Firestore")

                    // 4. Actualizar estado a éxito
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        errorMessage = null
                    )

                    // 5. Llamar callback de éxito
                    onSuccess()

                } else {
                    Log.e(TAG, "No se pudo obtener el UID del usuario")
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = "No se pudo obtener el UID del usuario."
                    )
                }

            } catch (e: FirebaseAuthException) {
                Log.e(TAG, "Error de Firebase Auth: ${e.errorCode}", e)
                val errorMsg = when (e.errorCode) {
                    "ERROR_EMAIL_ALREADY_IN_USE" ->
                        "Este correo ya está registrado."
                    "ERROR_INVALID_EMAIL" ->
                        "El formato del correo es inválido."
                    "ERROR_WEAK_PASSWORD" ->
                        "La contraseña es muy débil. Usa al menos 6 caracteres."
                    "ERROR_NETWORK_REQUEST_FAILED" ->
                        "Error de conexión. Verifica tu internet."
                    else ->
                        "Error al registrar: ${e.message}"
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = errorMsg
                )

            } catch (e: Exception) {
                Log.e(TAG, "Error general en registro", e)
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error desconocido al registrar usuario."
                )
            }
        }
    }

    // --- SUBIR IMAGEN A CLOUDINARY ---
    private suspend fun uploadProfileImage(context: Context?, profileData: ProfileData): String? {
        if (context == null) {
            Log.w(TAG, "Contexto nulo, no se puede subir imagen")
            return null
        }

        val imageUriString = when (profileData) {
            is ProfileData.ArtistData -> profileData.profileImageUri
            is ProfileData.FanData -> profileData.FanImage
            is ProfileData.CuratorData -> profileData.curatorImage
            is ProfileData.BandData -> profileData.bandImage
            is ProfileData.EstablishmentData -> profileData.EstablishmentImage
        }

        if (imageUriString.isNullOrEmpty()) {
            Log.d(TAG, "No hay imagen para subir")
            return null
        }

        return try {
            val imageUri = Uri.parse(imageUriString)
            Log.d(TAG, "Subiendo imagen a Cloudinary...")
            val cloudinaryUrl = CloudinaryUploader.uploadImage(imageUri, context)

            Log.d(TAG, "Imagen subida exitosamente: $cloudinaryUrl")
            cloudinaryUrl
        } catch (e: Exception) {
            Log.e(TAG, "Error al subir imagen", e)
            null
        }
    }

    // --- ACTUALIZAR PROFILE DATA CON URL DE CLOUDINARY ---
    private fun updateProfileDataWithImageUrl(profileData: ProfileData, imageUrl: String?): ProfileData {
        return when (profileData) {
            is ProfileData.ArtistData -> profileData.copy(profileImageUri = imageUrl)
            is ProfileData.FanData -> profileData.copy(FanImage = imageUrl)
            is ProfileData.CuratorData -> profileData.copy(curatorImage = imageUrl)
            is ProfileData.BandData -> profileData.copy(bandImage = imageUrl)
            is ProfileData.EstablishmentData -> profileData.copy(EstablishmentImage = imageUrl)
        }
    }

    // --- GUARDAR PERFIL EN FIRESTORE ---
    private suspend fun saveProfileToFirestore(
        uid: String,
        email: String,
        role: String,
        profileData: ProfileData
    ) {
        // Determinar la colección según el rol
        val collection = when (role.lowercase()) {
            "artista" -> "Artista"
            "banda" -> "Bandas"
            "establecimiento" -> "Establecimiento"
            "fan" -> "Fan"
            "curador" -> "Curador"
            else -> "Fan" // Default
        }

        // Crear el mapa base con datos comunes
        val baseData = hashMapOf(
            "uid" to uid,
            "email" to email,
            "role" to role,
            "createdAt" to com.google.firebase.Timestamp.now()
        )

        // Agregar datos específicos del perfil
        val profileMap = profileDataToMap(profileData)
        val fullDataMap = baseData + profileMap

        try {
            Log.d(TAG, "Guardando en colección: $collection con datos: $fullDataMap")

            // Guardar en Firestore
            db.collection(collection)
                .document(uid)
                .set(fullDataMap)
                .await()

            Log.d(TAG, "Documento guardado exitosamente en $collection/$uid")

        } catch (e: Exception) {
            Log.e(TAG, "Error al guardar en Firestore", e)
            // Si falla Firestore, eliminar el usuario de Auth
            auth.currentUser?.delete()?.await()
            throw Exception("Error al guardar el perfil: ${e.message}")
        }
    }

    // --- CONVERTIR PERFIL A MAP ---
    private fun profileDataToMap(profile: ProfileData): Map<String, Any> {
        return when (profile) {
            is ProfileData.ArtistData -> mapOf(
                "nombreArtistico" to profile.artistName,
                "nombreReal" to profile.realName,
                "ciudadBase" to profile.city,
                "generoPrincipal" to profile.genre,
                "subgeneros" to profile.subgenres,
                "biografia" to profile.bio,
                "imagenPerfil" to (profile.profileImageUri ?: ""),
                "redesSociales" to mapOf(
                    "instagram" to profile.instagram,
                    "youtube" to profile.youtube,
                    "spotify" to profile.spotify,
                    "tiktok" to profile.tiktok
                )
            )

            is ProfileData.BandData -> mapOf(
                "nombreBanda" to profile.bandName,
                "ciudadBase" to profile.city,
                "añoFormacion" to profile.year,
                "generoPrincipal" to profile.genre,
                "subgeneros" to profile.subgenres,
                "biografia" to profile.bio,
                "numeroMiembros" to profile.membersCount,
                "manager" to profile.manager,
                "telefonoManager" to profile.managerPhone,
                "emailManager" to profile.managerEmail,
                "imagenPerfil" to (profile.bandImage ?: ""),
                "redesSociales" to mapOf(
                    "instagram" to profile.instagram,
                    "youtube" to profile.youtube,
                    "spotify" to profile.spotify,
                    "tiktok" to profile.tiktok,
                    "website" to profile.website
                )
            )

            is ProfileData.FanData -> mapOf(
                "nombre" to profile.FanName,
                "username" to profile.FanUsername,
                "fechaNacimiento" to profile.FanBirthday,
                "pais" to profile.FanCountry,
                "ciudad" to profile.FanCity,
                "biografia" to profile.FanBio,
                "imagen" to (profile.FanImage ?: ""),
                "generosFavoritos" to profile.FanGenres
            )

            is ProfileData.CuratorData -> mapOf(
                "nombre" to profile.curatorName,
                "username" to profile.curatorUsername,
                "pais" to profile.curatorCountry,
                "ciudad" to profile.curatorCity,
                "experiencia" to profile.curatorExperience,
                "especialidades" to profile.curatorSpecialties,
                "generos" to profile.curatorGenres,
                "biografia" to profile.curatorBio,
                "imagenPerfil" to (profile.curatorImage ?: ""),
                "redesSociales" to mapOf(
                    "portfolio" to profile.curatorPortfolio,
                    "linkedIn" to profile.curatorLinkedIn,
                    "instagram" to profile.curatorInstagram,
                    "tiktok" to profile.curatorTiktok,
                    "website" to profile.curatorWebsite
                )
            )

            is ProfileData.EstablishmentData -> mapOf(
                "nombre" to profile.EstablishmentName,
                "username" to profile.EstablishmentUsername,
                "imagen" to (profile.EstablishmentImage ?: ""),
                "telefonoPrincipal" to profile.EstablishmentPhonePrimary,
                "telefonoSecundario" to profile.EstablishmentPhoneSecondary,
                "ubicacion" to mapOf(
                    "pais" to profile.EstablishmentCountry,
                    "ciudad" to profile.EstablishmentCity,
                    "direccion" to profile.EstablishmentAddress,
                    "codigoPostal" to profile.EstablishmentZipCode
                ),
                "detalles" to mapOf(
                    "tipo" to profile.EstablishmentType,
                    "capacidad" to profile.EstablishmentCapacity,
                    "añoFundacion" to profile.EstablishmentFoundationYear,
                    "descripcion" to profile.EstablishmentDescription,
                    "servicios" to profile.EstablishmentServices,
                    "horarioApertura" to profile.EstablishmentOpeningHours,
                    "horarioCierre" to profile.EstablishmentClosingHours,
                    "metodosPago" to profile.EstablishmentPaymentMethods
                ),
                "redesSociales" to mapOf(
                    "instagram" to profile.EstablishmentInstagram,
                    "facebook" to profile.EstablishmentFacebook,
                    "tiktok" to profile.EstablishmentTikTok,
                    "twitter" to profile.EstablishmentTwitter,
                    "website" to profile.EstablishmentWebsite
                )
            )
        }
    }

    // --- FUNCIONES AUXILIARES ---
    fun setError(message: String) {
        _state.value = _state.value.copy(errorMessage = message)
    }

    fun resetState() {
        _state.value = SignUpState()
    }
}