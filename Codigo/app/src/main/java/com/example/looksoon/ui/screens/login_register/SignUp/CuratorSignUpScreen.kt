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
fun CuratorSignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading
    val errorMessage = signUpState.errorMessage

    var curatorName by remember { mutableStateOf("") }
    var curatorUsername by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var specialties by remember { mutableStateOf("") }
    var genres by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var portfolio by remember { mutableStateOf("") }
    var linkedin by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }
    var tiktok by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageWithText(
                imageRes = R.drawable.logo_looksoon,
                title = "Regístrate como Curador",
                subtitle = "Comparte tu experiencia profesional",
                titleColor = colors.onBackground,
                subtitleColor = colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileImagePicker(
                imageUri = profileImageUri,
                onImageClick = { imagePickerLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Información básica",
                style = MaterialTheme.typography.titleMedium,
                color = colors.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = curatorName,
                onValueChange = { curatorName = it },
                label = "Nombre completo *",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = curatorUsername,
                onValueChange = {
                    curatorUsername = it.filter { char ->
                        char.isLetterOrDigit() || char == '_'
                    }.lowercase()
                },
                label = "Nombre de usuario *",
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Ubicación", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = "País *",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = "Ciudad *",
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Experiencia profesional", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = experience,
                onValueChange = {
                    experience = it.filter { char -> char.isDigit() }
                },
                label = "Años de experiencia *",
                keyboardType = KeyboardType.Number,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = specialties,
                onValueChange = { specialties = it },
                label = "Especialidades * (playlists, festivales, A&R...)",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = genres,
                onValueChange = { genres = it },
                label = "Géneros de especialización * (separados por coma)",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = "Biografía profesional *",
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Datos de cuenta", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it.trim() },
                label = "Correo electrónico *",
                keyboardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña *",
                isPassword = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    showPasswordError = false
                },
                label = "Confirmar contraseña *",
                isPassword = true,
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

            TextDivider(text = "Portafolio y enlaces profesionales", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = portfolio,
                onValueChange = { portfolio = it.trim() },
                label = "Portafolio / Enlaces de trabajos",
                keyboardType = KeyboardType.Uri,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = linkedin,
                onValueChange = { linkedin = it.trim() },
                label = "LinkedIn",
                keyboardType = KeyboardType.Uri,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = website,
                onValueChange = { website = it.trim() },
                label = "Página web / Blog",
                keyboardType = KeyboardType.Uri,
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Redes sociales (opcional)", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = instagram,
                onValueChange = { instagram = it.trim() },
                label = "Instagram",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = tiktok,
                onValueChange = { tiktok = it.trim() },
                label = "TikTok",
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
                    if (curatorName.isEmpty() || curatorUsername.isEmpty() ||
                        country.isEmpty() || city.isEmpty() ||
                        experience.isEmpty() || specialties.isEmpty() ||
                        genres.isEmpty() || bio.isEmpty() ||
                        email.isEmpty() || password.isEmpty() ||
                        confirmPassword.isEmpty()) {
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

                    val yearsExp = experience.toIntOrNull()
                    if (yearsExp == null || yearsExp < 0 || yearsExp > 50) {
                        viewModel.setError("Años de experiencia inválidos (0-50)")
                        return@PrimaryButton
                    }

                    showPasswordError = false

                    val curatorData = SignUpViewModel.ProfileData.CuratorData(
                        curatorName = curatorName.trim(),
                        curatorUsername = curatorUsername.trim(),
                        curatorCountry = country.trim(),
                        curatorCity = city.trim(),
                        curatorExperience = experience,
                        curatorSpecialties = specialties.trim(),
                        curatorGenres = genres.trim(),
                        curatorBio = bio.trim(),
                        curatorPortfolio = portfolio.trim(),
                        curatorLinkedIn = linkedin.trim(),
                        curatorWebsite = website.trim(),
                        curatorInstagram = instagram.trim(),
                        curatorTiktok = tiktok.trim(),
                        curatorImage = profileImageUri?.toString()
                    )

                    viewModel.registerUser(
                        email = email.trim(),
                        password = password,
                        role = "curador",
                        profileData = curatorData,
                        context = context,
                        onSuccess = {
                            curatorName = ""
                            curatorUsername = ""
                            email = ""
                            password = ""
                            confirmPassword = ""
                            country = ""
                            city = ""
                            experience = ""
                            specialties = ""
                            genres = ""
                            bio = ""
                            portfolio = ""
                            linkedin = ""
                            website = ""
                            instagram = ""
                            tiktok = ""
                            profileImageUri = null
                            onSignUpClick()
                        }
                    )
                },
                enabled = !isLoading,
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