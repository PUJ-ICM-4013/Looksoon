package com.example.looksoon.ui.screens.login_register.SignUp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.looksoon.R
import com.example.looksoon.ui.screens.login_register.login.CustomOutlinedTextField
import com.example.looksoon.ui.screens.login_register.login.ImageWithText
import com.example.looksoon.ui.screens.login_register.login.PrimaryButton
import com.example.looksoon.ui.screens.login_register.login.TextDivider
import com.example.looksoon.ui.theme.PurplePrimary

@Composable
fun EstablishmentSignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading
    val errorMessage = signUpState.errorMessage

    // Estados del formulario - Información básica
    var establishmentName by remember { mutableStateOf("") }
    var establishmentUsername by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    // Contacto
    var phonePrimary by remember { mutableStateOf("") }
    var phoneSecondary by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }
    var facebook by remember { mutableStateOf("") }
    var tiktok by remember { mutableStateOf("") }
    var twitter by remember { mutableStateOf("") }

    // Ubicación
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    // Detalles
    var type by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var foundationYear by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var services by remember { mutableStateOf("") }

    // Configuración
    var openingHours by remember { mutableStateOf("") }
    var closingHours by remember { mutableStateOf("") }
    var paymentMethods by remember { mutableStateOf("") }

    // Estados de validación
    var showPasswordError by remember { mutableStateOf(false) }
    var showEmptyFieldsError by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Scaffold(
        containerColor = colors.background,
        snackbarHost = {
            if (errorMessage != null) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.resetState() }) {
                            Text("OK")
                        }
                    }
                ) {
                    Text(errorMessage)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageWithText(
                imageRes = R.drawable.logo_looksoon,
                title = "Regístrate como Establecimiento",
                subtitle = "Crea el perfil de tu local",
                titleColor = colors.onBackground,
                subtitleColor = colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileImagePicker(
                imageUri = profileImageUri,
                onImageClick = { imagePickerLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ===== INFORMACIÓN BÁSICA =====
            Text(
                text = "Información básica",
                style = MaterialTheme.typography.titleMedium,
                color = colors.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = establishmentName,
                onValueChange = { establishmentName = it },
                label = "Nombre del establecimiento *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = establishmentUsername,
                onValueChange = {
                    establishmentUsername = it.filter { char ->
                        char.isLetterOrDigit() || char == '_'
                    }.lowercase()
                },
                label = "Nombre de usuario *"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ===== UBICACIÓN =====
            TextDivider(text = "Ubicación", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = "País *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = "Ciudad *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = "Dirección completa *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = zipCode,
                onValueChange = { zipCode = it.filter { char -> char.isDigit() } },
                label = "Código postal",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ===== DETALLES DEL ESTABLECIMIENTO =====
            TextDivider(text = "Detalles del establecimiento", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = "Tipo * (Bar, Club, Teatro, Auditorio...)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = capacity,
                onValueChange = { capacity = it.filter { char -> char.isDigit() } },
                label = "Capacidad de personas *",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = foundationYear,
                onValueChange = {
                    foundationYear = it.filter { char -> char.isDigit() }.take(4)
                },
                label = "Año de fundación",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = "Descripción del lugar *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = services,
                onValueChange = { services = it },
                label = "Servicios ofrecidos (separados por coma)"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ===== HORARIOS Y CONFIGURACIÓN =====
            TextDivider(text = "Horarios y configuración", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = openingHours,
                onValueChange = { openingHours = it },
                label = "Horario de apertura (ej: 18:00)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = closingHours,
                onValueChange = { closingHours = it },
                label = "Horario de cierre (ej: 02:00)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = paymentMethods,
                onValueChange = { paymentMethods = it },
                label = "Métodos de pago (efectivo, tarjeta...)"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ===== CUENTA =====
            TextDivider(text = "Datos de cuenta", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it.trim() },
                label = "Correo electrónico *",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña *",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    showPasswordError = false
                },
                label = "Confirmar contraseña *",
                isPassword = true
            )

            if (showPasswordError) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = colors.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ===== CONTACTO =====
            TextDivider(text = "Información de contacto", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = phonePrimary,
                onValueChange = { phonePrimary = it.filter { char -> char.isDigit() || char == '+' } },
                label = "Teléfono principal *",
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = phoneSecondary,
                onValueChange = { phoneSecondary = it.filter { char -> char.isDigit() || char == '+' } },
                label = "Teléfono secundario (opcional)",
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = website,
                onValueChange = { website = it.trim() },
                label = "Página web",
                keyboardType = KeyboardType.Uri
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ===== REDES SOCIALES =====
            TextDivider(text = "Redes sociales (opcional)", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = instagram,
                onValueChange = { instagram = it.trim() },
                label = "Instagram"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = facebook,
                onValueChange = { facebook = it.trim() },
                label = "Facebook"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = tiktok,
                onValueChange = { tiktok = it.trim() },
                label = "TikTok"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = twitter,
                onValueChange = { twitter = it.trim() },
                label = "X (Twitter)"
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (showEmptyFieldsError) {
                Text(
                    text = "Por favor completa todos los campos obligatorios (*)",
                    color = colors.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            PrimaryButton(
                text = if (isLoading) "Registrando..." else "Crear cuenta",
                onClick = {
                    // Validar campos obligatorios
                    if (establishmentName.isEmpty() || establishmentUsername.isEmpty() ||
                        country.isEmpty() || city.isEmpty() || address.isEmpty() ||
                        type.isEmpty() || capacity.isEmpty() || description.isEmpty() ||
                        email.isEmpty() || password.isEmpty() ||
                        confirmPassword.isEmpty() || phonePrimary.isEmpty()) {
                        showEmptyFieldsError = true
                        return@PrimaryButton
                    }

                    showEmptyFieldsError = false

                    if (password != confirmPassword) {
                        showPasswordError = true
                        return@PrimaryButton
                    }

                    if (password.length < 6) {
                        viewModel.setError("La contraseña debe tener al menos 6 caracteres")
                        return@PrimaryButton
                    }

                    // Validar capacidad
                    val capacityNum = capacity.toIntOrNull()
                    if (capacityNum == null || capacityNum <= 0) {
                        viewModel.setError("La capacidad debe ser un número válido")
                        return@PrimaryButton
                    }

                    showPasswordError = false

                    val establishmentData = SignUpViewModel.ProfileData.EstablishmentData(
                        EstablishmentName = establishmentName.trim(),
                        EstablishmentUsername = establishmentUsername.trim(),
                        EstablishmentImage = profileImageUri?.toString(),
                        EstablishmentPhonePrimary = phonePrimary.trim(),
                        EstablishmentPhoneSecondary = phoneSecondary.trim(),
                        EstablishmentWebsite = website.trim(),
                        EstablishmentInstagram = instagram.trim(),
                        EstablishmentFacebook = facebook.trim(),
                        EstablishmentTikTok = tiktok.trim(),
                        EstablishmentTwitter = twitter.trim(),
                        EstablishmentCountry = country.trim(),
                        EstablishmentCity = city.trim(),
                        EstablishmentAddress = address.trim(),
                        EstablishmentZipCode = zipCode.trim(),
                        EstablishmentType = type.trim(),
                        EstablishmentCapacity = capacity,
                        EstablishmentFoundationYear = foundationYear.trim(),
                        EstablishmentDescription = description.trim(),
                        EstablishmentServices = services.trim(),
                        EstablishmentOpeningHours = openingHours.trim(),
                        EstablishmentClosingHours = closingHours.trim(),
                        EstablishmentPaymentMethods = paymentMethods.trim()
                    )

                    viewModel.registerUser(
                        email = email.trim(),
                        password = password,
                        role = "establecimiento",
                        profileData = establishmentData,
                        context = context,
                        onSuccess = {
                            establishmentName = ""
                            establishmentUsername = ""
                            email = ""
                            password = ""
                            confirmPassword = ""
                            profileImageUri = null
                            phonePrimary = ""
                            phoneSecondary = ""
                            website = ""
                            instagram = ""
                            facebook = ""
                            tiktok = ""
                            twitter = ""
                            country = ""
                            city = ""
                            address = ""
                            zipCode = ""
                            type = ""
                            capacity = ""
                            foundationYear = ""
                            description = ""
                            services = ""
                            openingHours = ""
                            closingHours = ""
                            paymentMethods = ""
                            onSignUpClick()
                        }
                    )
                },
                enabled = !isLoading
            )

            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = colors.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    if (!isLoading) {
                        viewModel.resetState()
                        onBackClick()
                    }
                },
                enabled = !isLoading
            ) {
                Text(
                    text = "Volver",
                    color = if (isLoading) colors.onSurface.copy(alpha = 0.5f) else PurplePrimary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}