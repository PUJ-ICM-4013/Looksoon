package com.example.faunafinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.theme.CallsScreenArtist
import com.example.looksoon.ui.theme.LoginScreen
import com.example.looksoon.ui.theme.MainScreenArtist
import com.example.looksoon.ui.theme.SignUpScreen

sealed class Screen(val route: String) {
    object Home : Screen("Inicio")
    object Convocatorias : Screen("Convocatorias")
    object Mensajes : Screen("Mensajes")
    object Perfil : Screen("Perfil")
    object Login : Screen("login")

    object SignUp : Screen("SignUp")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Home.route) { MainScreenArtist(navController = navController) }
        composable(Screen.Convocatorias.route) { CallsScreenArtist(navController = navController) }
        composable(Screen.Mensajes.route) { /* Pantalla de mensajes */ }
        composable(Screen.Perfil.route) { /* Pantalla de perfil */ }
        composable(Screen.Login.route) { LoginScreen(navController = navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController = navController) }
    }
}
