package com.example.looksoon.ui.screens.login_register.SignUp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.looksoon.R
import com.example.looksoon.ui.screens.login_register.login.CustomOutlinedTextField
import com.example.looksoon.ui.screens.login_register.login.ImageWithText
import com.example.looksoon.ui.screens.login_register.login.PrimaryButton
import com.example.looksoon.ui.screens.login_register.login.TextDivider
import com.example.looksoon.ui.theme.PurplePrimary

@Composable
fun ArtistSignUpScreen(
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
    var artistName by remember { mutableStateOf("") }
    var realName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var subgenres by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }
    var youtube by remember { mutableStateOf("") }
    var spotify by remember { mutableStateOf("") }
    var tiktok by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    var showPasswordError by remember { mutableStateOf(false) }
    var showEmptyFieldsError by remember { mutableStateOf(false) }

    // Launcher para seleccionar imagen
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
                title = "Regístrate como Artista",
                subtitle = "Crea tu perfil profesional",
                titleColor = colors.onBackground,
                subtitleColor = colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de imagen de perfil
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
                value = artistName,
                onValueChange = { artistName = it },
                label = "Nombre artístico *",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = realName,
                onValueChange = { realName = it },
                label = "Nombre real (opcional)",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = "Ciudad base *",
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
                label = "Biografía breve *",
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

            TextDivider(text = "Redes sociales (opcional)", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = instagram,
                onValueChange = { instagram = it },
                label = "Instagram",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = youtube,
                onValueChange = { youtube = it },
                label = "YouTube",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = spotify,
                onValueChange = { spotify = it },
                label = "Spotify / Apple Music",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = tiktok,
                onValueChange = { tiktok = it },
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
                    if (artistName.isEmpty() || city.isEmpty() ||
                        genre.isEmpty() || bio.isEmpty() ||
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

                    showPasswordError = false

                    val artistData = SignUpViewModel.ProfileData.ArtistData(
                        artistName = artistName.trim(),
                        realName = realName.trim(),
                        city = city.trim(),
                        genre = genre.trim(),
                        subgenres = subgenres.trim(),
                        bio = bio.trim(),
                        instagram = instagram.trim(),
                        youtube = youtube.trim(),
                        spotify = spotify.trim(),
                        tiktok = tiktok.trim(),
                        profileImageUri = profileImageUri?.toString()
                    )

                    viewModel.registerUser(
                        email = email.trim(),
                        password = password,
                        role = "artista",
                        profileData = artistData,
                        context = context, // Pasar contexto
                        onSuccess = {
                            artistName = ""
                            realName = ""
                            email = ""
                            password = ""
                            confirmPassword = ""
                            city = ""
                            genre = ""
                            subgenres = ""
                            bio = ""
                            instagram = ""
                            youtube = ""
                            spotify = ""
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

@Composable
fun ProfileImagePicker(
    imageUri: Uri?,
    onImageClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onImageClick() },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Seleccionar foto",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Agregar foto",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            AsyncImage(
                model = imageUri,
                contentDescription = "Foto seleccionada",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}