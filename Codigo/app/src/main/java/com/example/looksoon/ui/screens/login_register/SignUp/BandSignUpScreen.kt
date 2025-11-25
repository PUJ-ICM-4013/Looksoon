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
fun BandSignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading
    val errorMessage = signUpState.errorMessage

    // Estados del formulario
    var bandName by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var subgenres by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var membersCount by remember { mutableStateOf("") }
    var manager by remember { mutableStateOf("") }
    var managerPhone by remember { mutableStateOf("") }
    var managerEmail by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }
    var youtube by remember { mutableStateOf("") }
    var spotify by remember { mutableStateOf("") }
    var tiktok by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
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
                title = "Regístrate como Banda",
                subtitle = "Crea el perfil de tu grupo musical",
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
                value = bandName,
                onValueChange = { bandName = it },
                label = "Nombre de la banda *",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = "Ciudad base *",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = year,
                onValueChange = {
                    year = it.filter { char -> char.isDigit() }.take(4)
                },
                label = "Año de formación *",
                keyboardType = KeyboardType.Number,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = membersCount,
                onValueChange = {
                    membersCount = it.filter { char -> char.isDigit() }
                },
                label = "Número de integrantes *",
                keyboardType = KeyboardType.Number,
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Información musical", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = genre,
                onValueChange = { genre = it },
                label = "Género musical principal *",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = subgenres,
                onValueChange = { subgenres = it },
                label = "Subgéneros (separados por coma)",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = "Biografía de la banda *",
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Representante/Manager", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = manager,
                onValueChange = { manager = it },
                label = "Nombre del manager (opcional)",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = managerPhone,
                onValueChange = {
                    managerPhone = it.filter { char -> char.isDigit() || char == '+' }
                },
                label = "Teléfono del manager (opcional)",
                keyboardType = KeyboardType.Phone,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = managerEmail,
                onValueChange = { managerEmail = it.trim() },
                label = "Email del manager (opcional)",
                keyboardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Datos de cuenta", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it.trim() },
                label = "Correo electrónico de la banda *",
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

            TextDivider(text = "Redes sociales (opcional)", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = instagram,
                onValueChange = { instagram = it.trim() },
                label = "Instagram",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = youtube,
                onValueChange = { youtube = it.trim() },
                label = "YouTube",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = spotify,
                onValueChange = { spotify = it.trim() },
                label = "Spotify / Apple Music",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = tiktok,
                onValueChange = { tiktok = it.trim() },
                label = "TikTok",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = website,
                onValueChange = { website = it.trim() },
                label = "Página web",
                keyboardType = KeyboardType.Uri,
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
                    if (bandName.isEmpty() || city.isEmpty() || year.isEmpty() ||
                        membersCount.isEmpty() || genre.isEmpty() || bio.isEmpty() ||
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

                    // Validar año
                    val yearNum = year.toIntOrNull()
                    if (yearNum == null || yearNum < 1900 || yearNum > 2024) {
                        viewModel.setError("Año de formación inválido")
                        return@PrimaryButton
                    }

                    // Validar número de integrantes
                    val membersNum = membersCount.toIntOrNull()
                    if (membersNum == null || membersNum <= 0) {
                        viewModel.setError("Número de integrantes inválido")
                        return@PrimaryButton
                    }

                    showPasswordError = false

                    val bandData = SignUpViewModel.ProfileData.BandData(
                        bandName = bandName.trim(),
                        city = city.trim(),
                        year = year,
                        genre = genre.trim(),
                        subgenres = subgenres.trim(),
                        bio = bio.trim(),
                        membersCount = membersCount,
                        manager = manager.trim(),
                        managerPhone = managerPhone.trim(),
                        managerEmail = managerEmail.trim(),
                        instagram = instagram.trim(),
                        youtube = youtube.trim(),
                        spotify = spotify.trim(),
                        tiktok = tiktok.trim(),
                        website = website.trim(),
                        bandImage = profileImageUri?.toString()
                    )

                    viewModel.registerUser(
                        email = email.trim(),
                        password = password,
                        role = "banda",
                        profileData = bandData,
                        context = context,
                        onSuccess = {
                            bandName = ""
                            city = ""
                            year = ""
                            genre = ""
                            subgenres = ""
                            bio = ""
                            membersCount = ""
                            manager = ""
                            managerPhone = ""
                            managerEmail = ""
                            email = ""
                            password = ""
                            confirmPassword = ""
                            instagram = ""
                            youtube = ""
                            spotify = ""
                            tiktok = ""
                            website = ""
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