package com.example.faunafinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.screens.ArtistSignUpScreen
import com.example.looksoon.ui.screens.BandSignUpScreen
import com.example.looksoon.ui.screens.CallsScreenArtist
import com.example.looksoon.ui.screens.ChatScreen
import com.example.looksoon.ui.screens.FanRegistrationScreen
import com.example.looksoon.ui.screens.FeedScreen
import com.example.looksoon.ui.screens.LoginScreen
import com.example.looksoon.ui.screens.MainScreenArtist
import com.example.looksoon.ui.screens.MessagesScreen
import com.example.looksoon.ui.screens.ProfileScreen
import com.example.looksoon.ui.screens.SignUpScreen

sealed class Screen(val route: String) {
    object Home : Screen("Inicio")
    object Convocatorias : Screen("Convocatorias")
    object Mensajes : Screen("Mensajes")
    object Perfil : Screen("Perfil")
    object Login : Screen("login")

    object SignUp : Screen("SignUp")

    object Publicar : Screen("Publicar")

    object Chat : Screen("Chat")

    object Feed : Screen("Feed")

    object SignUpInformationArtist : Screen("SignUpInformationArtist")

    object SignUpInformationFan : Screen("SignUpInformationFan")

    object SignUpInformationBand : Screen("SignUpInformationBand")

    object SignUpInformationLocal : Screen("SignUpInformationLocal")

    //Crear Screens
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Home.route) { MainScreenArtist(navController = navController) }
        composable(Screen.Convocatorias.route) { CallsScreenArtist(navController = navController) }
        composable(Screen.Mensajes.route) { MessagesScreen(navController = navController) }
        composable(Screen.Perfil.route) { ProfileScreen(navController = navController) }
        composable(Screen.Login.route) { LoginScreen(navController = navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController = navController) }
        composable(Screen.Publicar.route) { /* Pantalla de publicar */ }
        composable(Screen.Chat.route) { ChatScreen(navController = navController, contactName = "Persona") }
        //Colocar composable de cad screen
        composable(Screen.Feed.route) { FeedScreen(navController = navController) }
        composable(Screen.SignUpInformationArtist.route) { ArtistSignUpScreen(navController = navController,
            onSignUpClick = { },
            onBackClick = { navController.popBackStack() }
        ) }
        composable(Screen.SignUpInformationFan.route) { FanRegistrationScreen(navController = navController) }
        composable(Screen.SignUpInformationBand.route) { BandSignUpScreen(navController = navController) }
        //composable(Screen.SignUpInformationLocal.route) { SignUpInformationLocal(navController = navController) }
    }
}
