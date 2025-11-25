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
fun FanSignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading
    val errorMessage = signUpState.errorMessage

    var fanName by remember { mutableStateOf("") }
    var fanUsername by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var favoriteGenres by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    var showPasswordError by remember { mutableStateOf(false) }
    var showEmptyFieldsError by remember { mutableStateOf(false) }
    var showBirthdayError by remember { mutableStateOf(false) }

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
                title = "Regístrate como Fan",
                subtitle = "Únete a la comunidad musical",
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
                text = "Información personal",
                style = MaterialTheme.typography.titleMedium,
                color = colors.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = fanName,
                onValueChange = { fanName = it },
                label = "Nombre completo *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = fanUsername,
                onValueChange = {
                    fanUsername = it.filter { char ->
                        char.isLetterOrDigit() || char == '_'
                    }.lowercase()
                },
                label = "Nombre de usuario *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = birthday,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.filter { it.isDigit() }
                    birthday = when {
                        digitsOnly.length <= 2 -> digitsOnly
                        digitsOnly.length <= 4 -> "${digitsOnly.substring(0, 2)}/${digitsOnly.substring(2)}"
                        else -> "${digitsOnly.substring(0, 2)}/${digitsOnly.substring(2, 4)}/${digitsOnly.substring(4, minOf(8, digitsOnly.length))}"
                    }
                    showBirthdayError = false
                },
                label = "Fecha de nacimiento * (DD/MM/AAAA)",
                keyboardType = KeyboardType.Number
            )

            if (showBirthdayError) {
                Text(
                    text = "Formato inválido. Usa DD/MM/AAAA",
                    color = colors.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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

            Spacer(modifier = Modifier.height(24.dp))

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

            TextDivider(text = "Preferencias musicales", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = favoriteGenres,
                onValueChange = { favoriteGenres = it },
                label = "Géneros favoritos * (separados por coma)"
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Sobre ti", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = "Biografía corta (opcional)"
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
                    if (fanName.isEmpty() || fanUsername.isEmpty() ||
                        email.isEmpty() || password.isEmpty() ||
                        confirmPassword.isEmpty() || country.isEmpty() ||
                        city.isEmpty() || birthday.isEmpty() ||
                        favoriteGenres.isEmpty()) {
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

                    if (!isValidBirthday(birthday)) {
                        showBirthdayError = true
                        return@PrimaryButton
                    }

                    showPasswordError = false
                    showBirthdayError = false

                    val fanData = SignUpViewModel.ProfileData.FanData(
                        FanName = fanName.trim(),
                        FanUsername = fanUsername.trim(),
                        FanBirthday = birthday,
                        FanCountry = country.trim(),
                        FanCity = city.trim(),
                        FanBio = bio.trim(),
                        FanImage = profileImageUri?.toString(),
                        FanGenres = favoriteGenres.trim()
                    )

                    viewModel.registerUser(
                        email = email.trim(),
                        password = password,
                        role = "fan",
                        profileData = fanData,
                        onSuccess = {
                            fanName = ""
                            fanUsername = ""
                            email = ""
                            password = ""
                            confirmPassword = ""
                            country = ""
                            city = ""
                            birthday = ""
                            favoriteGenres = ""
                            bio = ""
                            profileImageUri = null
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

private fun isValidBirthday(birthday: String): Boolean {
    if (birthday.length != 10) return false
    val parts = birthday.split("/")
    if (parts.size != 3) return false
    val day = parts[0].toIntOrNull() ?: return false
    val month = parts[1].toIntOrNull() ?: return false
    val year = parts[2].toIntOrNull() ?: return false
    return day in 1..31 && month in 1..12 && year in 1900..2024
}