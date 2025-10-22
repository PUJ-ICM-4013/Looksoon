package com.example.looksoon.ui.screens.login_register.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // --- ESTADOS ---
    data class SignUpState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String? = null
    )
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    // --- DATA CLASSES PARA PERFILES (Datos a enviar a tu Backend) ---
    sealed class ProfileData {
        // Datos específicos del artista
        data class ArtistData(
            val artistName: String, val realName: String, val city: String, val genre: String, val subgenres: String, val bio: String,
            val instagram: String, val youtube: String, val spotify: String, val tiktok: String
        ) : ProfileData()

        // Datos específicos de la banda
        data class BandData(
            val bandName: String, val city: String, val year: String, val genre: String, val subgenres: String, val bio: String,
            val membersCount: String, val manager: String, val managerPhone: String, val managerEmail: String,
            val instagram: String, val youtube: String, val spotify: String, val tiktok: String, val website: String
        ) : ProfileData()

        // Datos específicos del fan
        data class FanData(
            val FanName: String, val FanUsername: String, val FanBirthday: String, val FanCountry: String, val FanCity: String, val FanBio: String,
            val FanImage: String? // Nota: la URL de la imagen si se sube a Firebase Storage
        ) : ProfileData()

        // Datos específicos del curador
        data class CuratorData(
            val curatorName: String, val curatorUsername: String, val curatorCountry: String, val curatorCity: String, val curatorExperience: String,
            val curatorSpecialties: String, val curatorGenres: String, val curatorBio: String, val curatorPortfolio: String,
            val curatorLinkedIn: String, val curatorWebsite: String, val curatorInstagram: String, val curatorTiktok: String
        ) : ProfileData()

        // Datos específicos del ESTABLECIMIENTO
        data class EstablishmentData(
            // Basic
            val EstablishmentName: String,
            val EstablishmentUsername: String,
            val EstablishmentImage: String?,

            // Contact/Social
            val EstablishmentPhonePrimary: String,
            val EstablishmentPhoneSecondary: String,
            val EstablishmentWebsite: String,
            val EstablishmentInstagram: String,
            val EstablishmentFacebook: String,
            val EstablishmentTikTok: String,
            val EstablishmentTwitter: String,

            // Location
            val EstablishmentCountry: String,
            val EstablishmentCity: String,
            val EstablishmentAddress: String,
            val EstablishmentZipCode: String,

            // Details/Config
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

    // --- FUNCIÓN PRINCIPAL DE REGISTRO (MODIFICADA) ---
    fun registerUser(
        email: String,
        password: String,
        role: String,
        profileData: ProfileData,
        onSuccess: () -> Unit // <--- ¡PARÁMETRO AÑADIDO!
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            _state.value = _state.value.copy(errorMessage = "El email y la contraseña son obligatorios.")
            return
        }

        _state.value = _state.value.copy(isLoading = true, errorMessage = null, isSuccess = false)

        viewModelScope.launch {
            // 1. Crear usuario en Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            // 2. Éxito en Firebase -> Enviar datos al Backend propio
                            sendProfileDataToCustomBackend(userId, role, profileData, onSuccess) // <--- PASAR onSuccess
                        } else {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorMessage = "Usuario creado, pero no se pudo obtener el ID de Firebase."
                            )
                        }
                    } else {
                        // 3. Error en Firebase Auth
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = task.exception?.localizedMessage ?: "Error desconocido de registro."
                        )
                    }
                }
        }
    }

    // --- INTEGRACIÓN CLAVE (MODIFICADA) ---
    private fun sendProfileDataToCustomBackend(
        firebaseUid: String,
        role: String,
        profileData: ProfileData,
        onSuccess: () -> Unit // <--- PARÁMETRO AÑADIDO
    ) {
        // ** Aquí DEBE ir la llamada con Retrofit a tu API REST **
        // Envía el firebaseUid, el 'role' y el 'profileData' (serializado a JSON)

        // Simulación: asume éxito tras 1 segundo de latencia
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)

            // La simulación de éxito incluye llamar a onSuccess
            _state.value = _state.value.copy(
                isLoading = false,
                isSuccess = true // ¡El backend guardó la data!
            )
            onSuccess() // <--- ¡EJECUTAMOS LA NAVEGACIÓN DESPUÉS DEL ÉXITO!
        }
    }

    // Función para resetear el estado (útil después de un registro exitoso o error)
    fun resetState() {
        _state.value = SignUpState()
    }
}