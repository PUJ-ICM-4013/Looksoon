package com.example.looksoon.ui.screens.login_register

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // <-- NECESARIO
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.looksoon.R
import com.example.looksoon.ui.screens.login_register.login.CustomOutlinedTextField
import com.example.looksoon.ui.screens.login_register.login.ImageWithText
import com.example.looksoon.ui.screens.login_register.login.PrimaryButton
import com.example.looksoon.ui.screens.login_register.login.TextDivider
import com.example.looksoon.ui.screens.login_register.viewmodels.SignUpViewModel // <-- NECESARIO
import com.example.looksoon.ui.theme.LooksoonTheme
import com.example.looksoon.ui.theme.PurplePrimary

// Pantalla principal (Entry point)
@Composable
fun SignUpInformationScreen(navController: NavHostController) {
    // 1. Instanciar el ViewModel
    val viewModel: SignUpViewModel = viewModel()
    val signUpState by viewModel.state.collectAsState()

    // Manejar la navegación después del éxito
    LaunchedEffect(key1 = signUpState.isSuccess) {
        if (signUpState.isSuccess) {
            // Limpiar el estado después del éxito y navegar
            viewModel.resetState()
            navController.navigate("home_or_profile_screen") {
                popUpTo("login_register_root") { inclusive = true }
            }
        }
    }

    // Muestra errores si existen
    LaunchedEffect(key1 = signUpState.errorMessage) {
        signUpState.errorMessage?.let { message ->
            // Aquí puedes usar un Snackbar para mostrar el error al usuario
            // Por ahora, solo puedes loggear:
            println("ERROR DE REGISTRO: $message")
            // Opcional: resetear el mensaje de error para que no se muestre dos veces
            // viewModel.resetState()
        }
    }

    ArtistSignUpScreen(
        viewModel = viewModel, // <-- Pasamos el ViewModel al Composable específico
        onBackClick = { navController.popBackStack() },
        onSignUpClick = { navController.navigate("artist_signup_screen") },
    )
}

@Composable
fun ArtistSignUpScreen(
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), // <-- Recibir el ViewModel
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme
) {
    // 2. Observar el estado de carga
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading

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

    Scaffold(containerColor = colors.background) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Header con logo
            ImageWithText(
                imageRes = R.drawable.logo_looksoon,
                title = "Regístrate como Artista",
                subtitle = "Crea tu perfil profesional",
                titleColor = colors.onBackground,
                subtitleColor = colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nombre artístico
            CustomOutlinedTextField(
                value = artistName,
                onValueChange = { artistName = it },
                label = "Nombre artístico",

                )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre real
            CustomOutlinedTextField(
                value = realName,
                onValueChange = { realName = it },
                label = "Nombre real (opcional)",

                )

            Spacer(modifier = Modifier.height(16.dp))

            // Ciudad base
            CustomOutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = "Ciudad base",

                )

            Spacer(modifier = Modifier.height(16.dp))

            // Género principal
            CustomOutlinedTextField(
                value = genre,
                onValueChange = { genre = it },
                label = "Género musical principal",

                )

            Spacer(modifier = Modifier.height(16.dp))

            // Subgéneros
            CustomOutlinedTextField(
                value = subgenres,
                onValueChange = { subgenres = it },
                label = "Subgéneros (separados por coma)",

                )

            Spacer(modifier = Modifier.height(16.dp))

            // Biografía
            CustomOutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = "Biografía breve",

                )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Contacto", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(24.dp))

            // Correo
            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo electrónico",
                keyboardType = KeyboardType.Email,

                )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                isPassword = true,

                )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmar contraseña
            CustomOutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar contraseña",
                isPassword = true,

                )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Redes sociales", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(24.dp))

            // Redes sociales
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

            // Botón de registro
            PrimaryButton(
                // 3. Mostrar estado de carga en el botón
                text = if (isLoading) "Registrando..." else "Crear cuenta",
                onClick = {
                    // 4. Lógica de registro con el ViewModel
                    if (password == confirmPassword) {
                        val artistData = SignUpViewModel.ProfileData.ArtistData(
                            artistName = artistName,
                            realName = realName,
                            city = city,
                            genre = genre,
                            subgenres = subgenres,
                            bio = bio,
                            instagram = instagram,
                            youtube = youtube,
                            spotify = spotify,
                            tiktok = tiktok
                        )

                        viewModel.registerUser(
                            email = email,
                            password = password,
                            role = "artista",
                            profileData = artistData,
                            onSuccess = onSignUpClick
                        )
                    } else {
                        // Manejo de error: Las contraseñas no coinciden
                        println("ERROR: Las contraseñas no coinciden.")
                    }
                },
                // 5. Deshabilitar si está cargando o faltan campos
                enabled = !isLoading && artistName.isNotEmpty() &&
                        email.isNotEmpty() &&
                        password.isNotEmpty() &&
                        confirmPassword == password,
            )

            // 6. Indicador de progreso (si no está ya en el botón)
            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackClick, enabled = !isLoading) {
                Text(
                    text = "Volver",
                    color = PurplePrimary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun SignUpInformationArtistScreenPreview() {
    LooksoonTheme {
        ArtistSignUpScreen(
            viewModel = viewModel(), // Usar un ViewModel de prueba para el Preview
            onBackClick = {},
            onSignUpClick = {},
            colors = MaterialTheme.colorScheme
        )
    }
}

//PAntallad e inicio de sesion para Bandas de música
//-----------------------------------------------------------

@Composable
fun BandSignUpScreen(
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), // <-- Recibir el ViewModel
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme
) {
    // 1. Observar el estado del ViewModel (para carga y errores)
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading

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

    Scaffold(containerColor = colors.background) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header con logo
            ImageWithText(
                imageRes = R.drawable.logo_looksoon,
                title = "Registra tu Banda",
                subtitle = "Crea el perfil profesional de tu grupo",
                titleColor = colors.onBackground,
                subtitleColor = colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nombre de la banda
            CustomOutlinedTextField(
                value = bandName,
                onValueChange = { bandName = it },
                label = "Nombre de la banda",
            )
            // ... (Resto de CustomOutlinedTextFields permanecen igual) ...

            Spacer(modifier = Modifier.height(16.dp))

            // Ciudad base
            CustomOutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = "Ciudad base",
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Año de formación
            CustomOutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = "Año de formación",
                keyboardType = KeyboardType.Number,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Género principal
            CustomOutlinedTextField(
                value = genre,
                onValueChange = { genre = it },
                label = "Género musical principal",
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subgéneros
            CustomOutlinedTextField(
                value = subgenres,
                onValueChange = { subgenres = it },
                label = "Subgéneros (separados por coma)",
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Biografía
            CustomOutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = "Biografía breve",
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Integrantes", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(24.dp))

            CustomOutlinedTextField(
                value = membersCount,
                onValueChange = { membersCount = it },
                label = "Número de integrantes",
                keyboardType = KeyboardType.Number,
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Gestión y contacto", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(24.dp))

            CustomOutlinedTextField(
                value = manager,
                onValueChange = { manager = it },
                label = "Manager / Representante",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = managerPhone,
                onValueChange = { managerPhone = it },
                label = "Teléfono de contacto",
                keyboardType = KeyboardType.Phone,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = managerEmail,
                onValueChange = { managerEmail = it },
                label = "Correo de contacto",
                keyboardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Cuenta de acceso", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(24.dp))

            // Correo de acceso (Firebase Auth)
            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo electrónico",
                keyboardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña (Firebase Auth)
            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                isPassword = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmar contraseña
            CustomOutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar contraseña",
                isPassword = true,
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextDivider(text = "Redes sociales", textColor = colors.secondary)

            Spacer(modifier = Modifier.height(24.dp))

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
            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = website,
                onValueChange = { website = it },
                label = "Página web oficial",
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Botón de registro modificado
            PrimaryButton(
                text = if (isLoading) "Registrando..." else "Registrar Banda",
                onClick = {
                    if (password == confirmPassword) {
                        // 3. Recoger datos de perfil de banda
                        val bandData = SignUpViewModel.ProfileData.BandData(
                            bandName = bandName, city = city, year = year, genre = genre,
                            subgenres = subgenres, bio = bio, membersCount = membersCount,
                            manager = manager, managerPhone = managerPhone, managerEmail = managerEmail,
                            instagram = instagram, youtube = youtube, spotify = spotify,
                            tiktok = tiktok, website = website
                        )

                        // 4. Llamar al ViewModel para registrar (Firebase + Backend)
                        viewModel.registerUser(
                            email = email,
                            password = password,
                            role = "local", // Usar el rol correcto para bandas/locales si aplica, o un nuevo "banda"
                            profileData = bandData,
                            onSuccess = onSignUpClick
                        )
                    } else {
                        println("ERROR: Las contraseñas no coinciden.")
                    }
                },
                // 5. Deshabilitar si está cargando o faltan campos
                enabled = !isLoading && bandName.isNotEmpty() &&
                        email.isNotEmpty() &&
                        password.isNotEmpty() &&
                        confirmPassword == password,
            )

            // Indicador de progreso
            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackClick, enabled = !isLoading) {
                Text(
                    text = "Volver",
                    color = PurplePrimary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun BandSignUpScreenPreview() {
    LooksoonTheme {
        BandSignUpScreen(
            viewModel = viewModel(),
            onBackClick = {},
            onSignUpClick = {},
            colors = MaterialTheme.colorScheme
        )
    }
}


//Pantalla de registro para fan
@Composable
fun FanRegistrationScreen(
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), // <-- Recibir el ViewModel
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    // 1. Observar el estado del ViewModel (para carga, éxito y errores)
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading

    var FanName by remember { mutableStateOf("") }
    var FanUsername by remember { mutableStateOf("") }
    var FanEmail by remember { mutableStateOf("") }
    var FanPassword by remember { mutableStateOf("") }
    var FanConfirmPassword by remember { mutableStateOf("") }
    var FanBirthday by remember { mutableStateOf("") }
    var FanCountry by remember { mutableStateOf("") }
    var FanCity by remember { mutableStateOf("") }
    var FanBio by remember { mutableStateOf("") }
    var FanImage by remember { mutableStateOf("") } // Para guardar la URI de la imagen
    var FanGenres by remember { mutableStateOf("") } // Asumo que este campo era el de "Géneros favoritos"


    Scaffold (containerColor = MaterialTheme.colorScheme.background){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro de Fan",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))


            ProfileImagePicker(
                onImageSelected = { FanImage = it } // Guardar la URI de la imagen seleccionada
            )

            Spacer(modifier = Modifier.height(32.dp))


            TextDivider(
                text = "Información Personal",
                textColor = MaterialTheme.colorScheme.secondary
            )

            CustomOutlinedTextField(
                value = FanName,
                onValueChange = { FanName = it },
                label = "Nombre completo"
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = FanUsername,
                onValueChange = { FanUsername = it },
                label = "Nombre de usuario"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = FanEmail,
                onValueChange = { FanEmail = it },
                label = "Correo electrónico",
                keyboardType = KeyboardType.Email // Añadido tipo de teclado
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = FanPassword,
                onValueChange = { FanPassword = it },
                label = "Contraseña",
                isPassword = true,

                )

            Spacer(modifier = Modifier.height(32.dp))

            CustomOutlinedTextField(
                value = FanConfirmPassword,
                onValueChange = { FanConfirmPassword = it },
                label = "Confirmar contraseña",
                isPassword = true,

                )


            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(text = "Ubicación", textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(
                value = FanCountry,
                onValueChange = { FanCountry = it },
                label = "País"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = FanCity,
                onValueChange = { FanCity = it },
                label = "Ciudad"
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(
                text = "Fecha de nacimiento",
                textColor = MaterialTheme.colorScheme.secondary
            )

            CustomOutlinedTextField(
                value = FanBirthday,
                onValueChange = { FanBirthday = it },
                label = "DD/MM/AAAA",
                keyboardType = KeyboardType.Text // Puedes usar Date o Text dependiendo del formato
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(
                text = "Preferencias musicales",
                textColor = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = FanGenres, // Usar la variable FanGenres
                onValueChange = { FanGenres = it },
                label = "Géneros favoritos"
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(text = "Sobre ti", textColor = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = FanBio,
                onValueChange = { FanBio = it },
                label = "Biografía corta",
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de registro modificado
            PrimaryButton(
                text = if (isLoading) "Registrando..." else "Registrarse",
                onClick = {
                    // 2. Lógica de registro con el ViewModel
                    if (FanPassword == FanConfirmPassword) {
                        // 3. Recoger datos de perfil de fan
                        val fanData = SignUpViewModel.ProfileData.FanData(
                            FanName = FanName,
                            FanUsername = FanUsername,
                            FanBirthday = FanBirthday,
                            FanCountry = FanCountry,
                            FanCity = FanCity,
                            FanBio = FanBio,
                            FanImage = FanImage
                            // Nota: FanGenres se puede incluir en ProfileData si tu backend lo necesita
                        )

                        // 4. Llamar al ViewModel para registrar (Firebase + Backend)
                        viewModel.registerUser(
                            email = FanEmail,
                            password = FanPassword,
                            role = "fan", // Se envía el rol
                            profileData = fanData,
                            onSuccess = onSignUpClick
                        )
                    } else {
                        // Aquí se debe mostrar un error al usuario (ej: Snackbar)
                        println("ERROR: Las contraseñas no coinciden.")
                    }
                },
                // 5. Deshabilitar si está cargando o faltan campos
                enabled = !isLoading && FanName.isNotEmpty() &&
                        FanEmail.isNotEmpty() &&
                        FanPassword.isNotEmpty() &&
                        FanConfirmPassword == FanPassword,
            )

            // Indicador de progreso
            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {onBackClick()}, enabled = !isLoading) {
                Text(
                    text = "Volver",
                    color = PurplePrimary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

}

@Composable
fun ProfileImagePicker(onImageSelected: (String) -> Unit) {
    var imageUri by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                // TODO: Aquí va la lógica para abrir el selector de imagen
                // y actualizar 'imageUri' y llamar a 'onImageSelected(nuevaUri)'
            },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Foto de perfil",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(60.dp)
            )
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

@Preview
@Composable
fun FanRegistrationScreenPreview(){
    LooksoonTheme {
        FanRegistrationScreen(
            // Usar un ViewModel de prueba para el Preview
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
            onBackClick = {},
            onSignUpClick = {}
        )
    }
}



//---------------- ESTABLECIMIENTO--------------------------------
@Composable
fun EstablishmentRegistrationScreen(
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), // <-- Inyección del ViewModel
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    // 1. Observar el estado del ViewModel
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading

    // Variables de estado
    var EstablishmentName by remember { mutableStateOf("") }
    var EstablishmentUsername by remember { mutableStateOf("") }
    var EstablishmentEmail by remember { mutableStateOf("") }
    var EstablishmentPassword by remember { mutableStateOf("") }
    var EstablishmentConfirmPassword by remember { mutableStateOf("") }
    var EstablishmentImage by remember { mutableStateOf("") } // Logo/Imagen

    // Contacto
    var EstablishmentPhonePrimary by remember { mutableStateOf("") }
    var EstablishmentPhoneSecondary by remember { mutableStateOf("") }
    var EstablishmentWebsite by remember { mutableStateOf("") }
    var EstablishmentInstagram by remember { mutableStateOf("") }
    var EstablishmentFacebook by remember { mutableStateOf("") }
    var EstablishmentTikTok by remember { mutableStateOf("") }
    var EstablishmentTwitter by remember { mutableStateOf("") }

    // Ubicación
    var EstablishmentCountry by remember { mutableStateOf("") }
    var EstablishmentCity by remember { mutableStateOf("") }
    var EstablishmentAddress by remember { mutableStateOf("") }
    var EstablishmentZipCode by remember { mutableStateOf("") }

    // Detalles
    var EstablishmentType by remember { mutableStateOf("") }
    var EstablishmentCapacity by remember { mutableStateOf("") }
    var EstablishmentFoundationYear by remember { mutableStateOf("") }
    var EstablishmentDescription by remember { mutableStateOf("") }
    var EstablishmentServices by remember { mutableStateOf("") }

    // Configuración
    var EstablishmentOpeningHours by remember { mutableStateOf("") }
    var EstablishmentClosingHours by remember { mutableStateOf("") }
    var EstablishmentPaymentMethods by remember { mutableStateOf("") }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro de Establecimiento",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileImagePicker(
                onImageSelected = { EstablishmentImage = it } // <-- Capturar la URI
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextDivider(
                text = "Información básica",
                textColor = MaterialTheme.colorScheme.secondary
            )

            CustomOutlinedTextField(
                value = EstablishmentName,
                onValueChange = { EstablishmentName = it },
                label = "Nombre del establecimiento"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentUsername,
                onValueChange = { EstablishmentUsername = it },
                label = "Nombre de usuario"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentEmail,
                onValueChange = { EstablishmentEmail = it },
                label = "Correo electrónico",
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = EstablishmentPassword,
                onValueChange = { EstablishmentPassword = it },
                label = "Contraseña",
                isPassword = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = EstablishmentConfirmPassword,
                onValueChange = { EstablishmentConfirmPassword = it },
                label = "Confirmar contraseña",
                isPassword = true,

                )
            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(
                text = "Información de contacto",
                textColor = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = EstablishmentPhonePrimary,
                onValueChange = { EstablishmentPhonePrimary = it },
                label = "Teléfono principal",
                keyboardType = KeyboardType.Phone
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentPhoneSecondary,
                onValueChange = { EstablishmentPhoneSecondary = it },
                label = "Teléfono secundario (opcional)",
                keyboardType = KeyboardType.Phone
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentWebsite,
                onValueChange = { EstablishmentWebsite = it },
                label = "Página web"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentInstagram,
                onValueChange = { EstablishmentInstagram = it },
                label = "Instagram"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentFacebook,
                onValueChange = { EstablishmentFacebook = it },
                label = "Facebook"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentTikTok,
                onValueChange = { EstablishmentTikTok = it },
                label = "TikTok"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentTwitter,
                onValueChange = { EstablishmentTwitter = it },
                label = "X (Twitter)"
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextDivider(text = "Ubicación", textColor = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = EstablishmentCountry,
                onValueChange = { EstablishmentCountry = it },
                label = "País"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentCity,
                onValueChange = { EstablishmentCity = it },
                label = "Ciudad"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentAddress,
                onValueChange = { EstablishmentAddress = it },
                label = "Dirección"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentZipCode,
                onValueChange = { EstablishmentZipCode = it },
                label = "Código postal",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextDivider(
                text = "Detalles del establecimiento",
                textColor = MaterialTheme.colorScheme.secondary
            )

            CustomOutlinedTextField(
                value = EstablishmentType,
                onValueChange = { EstablishmentType = it },
                label = "Tipo de establecimiento"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentCapacity,
                onValueChange = { EstablishmentCapacity = it },
                label = "Capacidad de personas",
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentFoundationYear,
                onValueChange = { EstablishmentFoundationYear = it },
                label = "Año de fundación",
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentDescription,
                onValueChange = { EstablishmentDescription = it },
                label = "Descripción breve",
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentServices,
                onValueChange = { EstablishmentServices = it },
                label = "Servicios ofrecidos",
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextDivider(
                text = "Configuración adicional",
                textColor = MaterialTheme.colorScheme.secondary
            )

            CustomOutlinedTextField(
                value = EstablishmentOpeningHours,
                onValueChange = { EstablishmentOpeningHours = it },
                label = "Horario de apertura"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentClosingHours,
                onValueChange = { EstablishmentClosingHours = it },
                label = "Horario de cierre"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = EstablishmentPaymentMethods,
                onValueChange = { EstablishmentPaymentMethods = it },
                label = "Métodos de pago aceptados"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de registro
            PrimaryButton(
                text = if (isLoading) "Registrando..." else "Registrarse",
                onClick = {
                    if (EstablishmentPassword == EstablishmentConfirmPassword) {
                        // Crear el objeto EstablishmentData para el ViewModel
                        val establishmentData = SignUpViewModel.ProfileData.EstablishmentData(
                            EstablishmentName = EstablishmentName,
                            EstablishmentUsername = EstablishmentUsername,
                            EstablishmentImage = EstablishmentImage,
                            EstablishmentPhonePrimary = EstablishmentPhonePrimary,
                            EstablishmentPhoneSecondary = EstablishmentPhoneSecondary,
                            EstablishmentWebsite = EstablishmentWebsite,
                            EstablishmentInstagram = EstablishmentInstagram,
                            EstablishmentFacebook = EstablishmentFacebook,
                            EstablishmentTikTok = EstablishmentTikTok,
                            EstablishmentTwitter = EstablishmentTwitter,
                            EstablishmentCountry = EstablishmentCountry,
                            EstablishmentCity = EstablishmentCity,
                            EstablishmentAddress = EstablishmentAddress,
                            EstablishmentZipCode = EstablishmentZipCode,
                            EstablishmentType = EstablishmentType,
                            EstablishmentCapacity = EstablishmentCapacity,
                            EstablishmentFoundationYear = EstablishmentFoundationYear,
                            EstablishmentDescription = EstablishmentDescription,
                            EstablishmentServices = EstablishmentServices,
                            EstablishmentOpeningHours = EstablishmentOpeningHours,
                            EstablishmentClosingHours = EstablishmentClosingHours,
                            EstablishmentPaymentMethods = EstablishmentPaymentMethods
                        )

                        // Llamar a la función de registro del ViewModel
                        viewModel.registerUser(
                            email = EstablishmentEmail,
                            password = EstablishmentPassword,
                            role = "establecimiento",
                            profileData = establishmentData,
                            onSuccess = onSignUpClick
                        )
                    } else {
                        println("ERROR: Las contraseñas no coinciden.")
                    }
                },
                // Deshabilitar si está cargando o faltan campos obligatorios
                enabled = !isLoading && EstablishmentName.isNotEmpty() &&
                        EstablishmentEmail.isNotEmpty() &&
                        EstablishmentPassword.isNotEmpty() &&
                        EstablishmentPassword == EstablishmentConfirmPassword,
            )

            // Indicador de progreso
            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackClick, enabled = !isLoading) {
                Text(text = "Volver", color = PurplePrimary)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun EstablishmentRegistrationScreenPreview(){
    LooksoonTheme {
        EstablishmentRegistrationScreen(
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
            onBackClick = {},
            onSignUpClick = {}
        )
    }
}


//------------------ CURADOR ---------------------------
@Composable
fun CuratorRegistrationScreen(
    viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), // <-- Inyección del ViewModel
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    // 1. Observar el estado del ViewModel
    val signUpState by viewModel.state.collectAsState()
    val isLoading = signUpState.isLoading

    // Variables de estado
    var curatorName by remember { mutableStateOf("") }
    var curatorUsername by remember { mutableStateOf("") }
    var curatorEmail by remember { mutableStateOf("") }
    var curatorPassword by remember { mutableStateOf("") }
    var curatorConfirmPassword by remember { mutableStateOf("") }
    var curatorCountry by remember { mutableStateOf("") }
    var curatorCity by remember { mutableStateOf("") }
    var curatorExperience by remember { mutableStateOf("") }
    var curatorSpecialties by remember { mutableStateOf("") }
    var curatorGenres by remember { mutableStateOf("") }
    var curatorBio by remember { mutableStateOf("") }
    var curatorPortfolio by remember { mutableStateOf("") }
    var curatorLinkedIn by remember { mutableStateOf("") }
    var curatorWebsite by remember { mutableStateOf("") }
    var curatorInstagram by remember { mutableStateOf("") }
    var curatorTiktok by remember { mutableStateOf("") }
    var curatorImage by remember { mutableStateOf("") } // Para la URL de la imagen de perfil

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro de Curador Musical",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileImagePicker(onImageSelected = { curatorImage = it }) // <-- Capturar la URI

            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(
                text = "Información Personal",
                textColor = MaterialTheme.colorScheme.secondary
            )

            CustomOutlinedTextField(
                value = curatorName,
                onValueChange = { curatorName = it },
                label = "Nombre completo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorUsername,
                onValueChange = { curatorUsername = it },
                label = "Nombre de usuario"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorEmail,
                onValueChange = { curatorEmail = it },
                label = "Correo electrónico",
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorPassword,
                onValueChange = { curatorPassword = it },
                label = "Contraseña",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorConfirmPassword,
                onValueChange = { curatorConfirmPassword = it },
                label = "Confirmar contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(32.dp))


            TextDivider(text = "Ubicación", textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(
                value = curatorCountry,
                onValueChange = { curatorCountry = it },
                label = "País"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorCity,
                onValueChange = { curatorCity = it },
                label = "Ciudad"
            )

            Spacer(modifier = Modifier.height(32.dp))


            TextDivider(
                text = "Perfil Profesional",
                textColor = MaterialTheme.colorScheme.secondary
            )

            CustomOutlinedTextField(
                value = curatorExperience,
                onValueChange = { curatorExperience = it },
                label = "Años de experiencia",
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorSpecialties,
                onValueChange = { curatorSpecialties = it },
                label = "Especialidades (playlists, festivales...)"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorGenres,
                onValueChange = { curatorGenres = it },
                label = "Géneros de especialización"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorBio,
                onValueChange = { curatorBio = it },
                label = "Biografía profesional"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorPortfolio,
                onValueChange = { curatorPortfolio = it },
                label = "Portafolio / enlaces de trabajos"
            )

            Spacer(modifier = Modifier.height(32.dp))


            TextDivider(
                text = "Redes Profesionales",
                textColor = MaterialTheme.colorScheme.secondary
            )

            CustomOutlinedTextField(
                value = curatorLinkedIn,
                onValueChange = { curatorLinkedIn = it },
                label = "LinkedIn"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorWebsite,
                onValueChange = { curatorWebsite = it },
                label = "Página web / blog"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorInstagram,
                onValueChange = { curatorInstagram = it },
                label = "Instagram"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = curatorTiktok,
                onValueChange = { curatorTiktok = it },
                label = "TikTok"
            )

            Spacer(modifier = Modifier.height(32.dp))


            // Botón de registro
            PrimaryButton(
                text = if (isLoading) "Registrando..." else "Registrarse",
                onClick = {
                    if (curatorPassword == curatorConfirmPassword) {
                        // Crear el objeto CuratorData para el ViewModel
                        val curatorData = SignUpViewModel.ProfileData.CuratorData(
                            curatorName = curatorName,
                            curatorUsername = curatorUsername,
                            curatorCountry = curatorCountry,
                            curatorCity = curatorCity,
                            curatorExperience = curatorExperience,
                            curatorSpecialties = curatorSpecialties,
                            curatorGenres = curatorGenres,
                            curatorBio = curatorBio,
                            curatorPortfolio = curatorPortfolio,
                            curatorLinkedIn = curatorLinkedIn,
                            curatorWebsite = curatorWebsite,
                            curatorInstagram = curatorInstagram,
                            curatorTiktok = curatorTiktok
                            // curatorImage se manejaría aparte si es necesario
                        )

                        // Llamar a la función de registro del ViewModel
                        viewModel.registerUser(
                            email = curatorEmail,
                            password = curatorPassword,
                            role = "curador",
                            profileData = curatorData,
                            onSuccess = onSignUpClick
                        )
                    } else {
                        println("ERROR: Las contraseñas no coinciden.")
                    }
                },
                // Deshabilitar si está cargando o faltan campos obligatorios
                enabled = !isLoading && curatorName.isNotEmpty() &&
                        curatorEmail.isNotEmpty() &&
                        curatorPassword.isNotEmpty() &&
                        curatorConfirmPassword == curatorPassword
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackClick, enabled = !isLoading) {
                Text(text = "Volver", color = PurplePrimary)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun CuratorRegistratorScreenPreview(){
    LooksoonTheme {
        CuratorRegistrationScreen(
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
            onBackClick = {},
            onSignUpClick = {}
        )
    }
}

