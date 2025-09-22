package com.example.looksoon.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.faunafinder.navigation.Screen
import com.example.looksoon.R
import com.example.looksoon.ui.theme.LooksoonTheme
import com.example.looksoon.ui.theme.PurplePrimary

// Pantalla que recibirá los composable de la pantalla de registro como parámetro dependiendo la entidad
@Composable
fun SignUpInformationScreen(navController: NavHostController) {
    ArtistSignUpScreen(
        onSignUpClick = {},
        onBackClick = {},
    )
}

@Composable
fun ArtistSignUpScreen(
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme 
) {
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
                text = "Crear cuenta",
                onClick = onSignUpClick,
                enabled = artistName.isNotEmpty() &&
                        email.isNotEmpty() &&
                        password.isNotEmpty() &&
                        confirmPassword == password,
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackClick) {
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
        onSignUpClick = {},
        onBackClick = {},
        colors = MaterialTheme.colorScheme
    )
    }
}

//PAntallad e inicio de sesion para Bandas de música
//-----------------------------------------------------------

@Composable
fun BandSignUpScreen(
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme
) {
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

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo electrónico",
                keyboardType = KeyboardType.Email,

            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                isPassword = true,

            )

            Spacer(modifier = Modifier.height(16.dp))

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

            PrimaryButton(
                text = "Registrar Banda",
                onClick = onSignUpClick,
                enabled = bandName.isNotEmpty() &&
                        email.isNotEmpty() &&
                        password.isNotEmpty() &&
                        confirmPassword == password,
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackClick) {
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
            onSignUpClick = {},
            onBackClick = {},
            colors = MaterialTheme.colorScheme
        )
    }
}


//Pantalla de registro para fan
@Composable
fun FanRegistrationScreen(
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,) {

    var FanName by remember { mutableStateOf("") }
    var FanUsername by remember { mutableStateOf("") }
    var FanEmail by remember { mutableStateOf("") }
    var FanPassword by remember { mutableStateOf("") }
    var FanConfirmPassword by remember { mutableStateOf("") }
    var FanBirthday by remember { mutableStateOf("") }
    var FanCountry by remember { mutableStateOf("") }
    var FanCity by remember { mutableStateOf("") }
    var FanBio by remember { mutableStateOf("") }
    var FanImage by remember { mutableStateOf("") }


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
                onImageSelected = { /* manejar imagen */ }
            )

            Spacer(modifier = Modifier.height(32.dp))


            TextDivider(text = "Información Personal", textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(
                value = FanName,
                onValueChange = {FanName = it },
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
                onValueChange = {FanEmail = it },
                label = "Correo electrónico"
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

            TextDivider(text = "Ubicación",  textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(
                value = FanCountry,
                onValueChange = { FanCountry = it },
                label = "País"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = FanCity,
                onValueChange = {FanCity = it},
                label = "Ciudad"
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(text = "Fecha de nacimiento",  textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(
                value = FanBirthday,
                onValueChange = { FanBirthday = it },
                label = "DD/MM/AAAA"
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(text = "Preferencias musicales",  textColor = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = "",
                onValueChange = {},
                label = "Géneros favoritos"
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(text = "Sobre ti", textColor = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = FanBio,
                onValueChange = {FanBio = it},
                label = "Biografía corta",
            )

            Spacer(modifier = Modifier.height(32.dp))


            PrimaryButton(
                text = "Registrarse",
                onClick = onSignUpClick ,
                enabled = FanName.isNotEmpty() &&
                        FanEmail.isNotEmpty() &&
                        FanPassword.isNotEmpty() &&
                        FanConfirmPassword == FanPassword,
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {onBackClick()}) {
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
                // abrir selector de imagen
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
            onSignUpClick = {},
            onBackClick = {},)
    }
}



//---------------- ESTABLECIMIENTO--------------------------------
@Composable
fun EstablishmentRegistrationScreen(
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    var EstablishmentName by remember { mutableStateOf("") }
    var EstablishmentUsername by remember { mutableStateOf("") }
    var EstablishmentEmail by remember { mutableStateOf("") }
    var EstablishmentPassword by remember { mutableStateOf("") }
    var EstablishmentConfirmPassword by remember { mutableStateOf("") }
    var EstablishmentCountry by remember { mutableStateOf("") }
    var EstablishmentCity by remember { mutableStateOf("") }
    var EstablishmentBirthday by remember { mutableStateOf("") }
    var EstablishmentBio by remember { mutableStateOf("") }

    Scaffold(containerColor =MaterialTheme.colorScheme.background){innerPadding ->
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
                onImageSelected = { /* manejar logo */ }
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextDivider(text = "Información básica", textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Nombre del establecimiento")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Nombre de usuario")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Correo electrónico")
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

            TextDivider(text = "Información de contacto",  textColor = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Teléfono principal")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Teléfono secundario (opcional)")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Página web")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Instagram")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Facebook")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "TikTok")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "X (Twitter)")

            Spacer(modifier = Modifier.height(16.dp))


            TextDivider(text = "Ubicación",  textColor = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(value = "", onValueChange = {}, label = "País")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Ciudad")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Dirección")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Código postal")

            Spacer(modifier = Modifier.height(16.dp))


            TextDivider(text = "Detalles del establecimiento",  textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Tipo de establecimiento")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Capacidad de personas")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Año de fundación")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = "",
                onValueChange = {},
                label = "Descripción breve",
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = "",
                onValueChange = {},
                label = "Servicios ofrecidos",
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextDivider(text = "Configuración adicional",  textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Horario de apertura")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Horario de cierre")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = "", onValueChange = {}, label = "Métodos de pago aceptados")

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Registrarse",
                onClick = onSignUpClick,
                /*enabled = EstablishmentName.isNotEmpty() &&
                        EstablishmentUsername.isNotEmpty() &&
                        EstablishmentEmail.isNotEmpty() &&
                        EstablishmentPassword.isNotEmpty() &&
                        EstablishmentPassword == EstablishmentConfirmPassword,*/
                enabled = true,
            )

            TextButton(onClick = onBackClick) {
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
            onSignUpClick = {},
            onBackClick = {},)
    }
}


//------------------ CURADOR ---------------------------
@Composable
fun CuratorRegistrationScreen(onSignUpClick: () -> Unit, onBackClick: () -> Unit) {

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

            ProfileImagePicker(onImageSelected = { /* manejar foto */ })

            Spacer(modifier = Modifier.height(32.dp))

            TextDivider(text = "Información Personal", textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(value = curatorName, onValueChange = { curatorName = it }, label = "Nombre completo")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorUsername, onValueChange = { curatorUsername = it }, label = "Nombre de usuario")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorEmail, onValueChange = { curatorEmail = it }, label = "Correo electrónico")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorPassword, onValueChange = { curatorPassword = it }, label = "Contraseña", isPassword = true)
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorConfirmPassword, onValueChange = { curatorConfirmPassword = it }, label = "Confirmar contraseña", isPassword = true)

            Spacer(modifier = Modifier.height(32.dp))


            TextDivider(text = "Ubicación", textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(value = curatorCountry, onValueChange = { curatorCountry = it }, label = "País")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorCity, onValueChange = { curatorCity = it }, label = "Ciudad")

            Spacer(modifier = Modifier.height(32.dp))


            TextDivider(text = "Perfil Profesional", textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(value = curatorExperience, onValueChange = { curatorExperience = it }, label = "Años de experiencia")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorSpecialties, onValueChange = { curatorSpecialties = it }, label = "Especialidades (playlists, festivales...)")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorGenres, onValueChange = { curatorGenres = it }, label = "Géneros de especialización")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorBio, onValueChange = { curatorBio = it }, label = "Biografía profesional")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorPortfolio, onValueChange = { curatorPortfolio = it }, label = "Portafolio / enlaces de trabajos")

            Spacer(modifier = Modifier.height(32.dp))


            TextDivider(text = "Redes Profesionales", textColor = MaterialTheme.colorScheme.secondary)

            CustomOutlinedTextField(value = curatorLinkedIn, onValueChange = { curatorLinkedIn = it }, label = "LinkedIn")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorWebsite, onValueChange = { curatorWebsite = it }, label = "Página web / blog")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorInstagram, onValueChange = { curatorInstagram = it }, label = "Instagram")
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(value = curatorTiktok, onValueChange = { curatorTiktok = it }, label = "TikTok")

            Spacer(modifier = Modifier.height(32.dp))


            PrimaryButton(
                text = "Registrarse",
                onClick = onSignUpClick,
                enabled = curatorName.isNotEmpty() &&
                        curatorEmail.isNotEmpty() &&
                        curatorPassword.isNotEmpty() &&
                        curatorConfirmPassword == curatorPassword
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackClick) {
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
            onSignUpClick = {},
            onBackClick = {},
        )
    }
}

