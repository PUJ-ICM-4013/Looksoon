package com.example.looksoon.ui.screens.login_register.SignUp

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



//PAntallad e inicio de sesion para Bandas de música
//-----------------------------------------------------------




//Pantalla de registro para fan




//---------------- ESTABLECIMIENTO--------------------------------



//------------------ CURADOR ---------------------------


