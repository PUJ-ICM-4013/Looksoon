package com.example.faunafinder.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.screens.*

// ... (La sealed class Screen se mantiene exactamente igual que en la versión anterior)
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
    object Editar : Screen("Editar")
    object SignUpInformationArtist : Screen("SignUpInformationArtist")
    object SignUpInformationFan : Screen("SignUpInformationFan")
    object SignUpInformationBand : Screen("SignUpInformationBand")
    object SignUpInformationEstablishment : Screen("SignUpInformationEstablishment")
    object SignUpInformationCurator : Screen("SignUpInformationCurator")
    object LocalActions : Screen("local_actions")
    object ReserveArtist : Screen("reserve_artist")
    object EventDetails : Screen("event_details")
    object PublishEvent : Screen("publish_event")
    object ManageApplications : Screen("manage_applications")
    object ReservationDetail : Screen("reservation_detail")
    object ManagePosts : Screen("manage_posts")
    object PostComments : Screen("post_comments/{postId}")
    object UserProfile : Screen("user_profile/{userId}")
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // --- CAMBIO 1: GUARDAMOS EL ROL DEL USUARIO AQUÍ ---
    var userRole by remember { mutableStateOf("Fan") } // Por defecto es Fan

    // Función que el LoginScreen usará para actualizar el rol
    val onLoginSuccess: (String) -> Unit = { role ->
        userRole = role
    }

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        // ... (otras rutas como Home, Convocatorias, etc., no cambian)

        // --- CAMBIO 2: LE PASAMOS EL ROL AL PROFILESCREEN ---
        composable(Screen.Perfil.route) {
            ProfileScreen(
                navController = navController,
                isMyProfile = true,
                userRole = userRole // Usamos la variable de estado
            )
        }

        // --- CAMBIO 3: LE PASAMOS LA FUNCIÓN DE ACTUALIZAR AL LOGINSCREEN ---
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = onLoginSuccess // Le pasamos la función
            )
        }

        // --- CAMBIO 4: DEFINIMOS EL ROL PARA PERFILES DE OTROS ---
        composable(Screen.UserProfile.route) {
            // Cuando vemos el perfil de otro, asumimos un rol (ej. "Artista")
            // o lo obtendríamos de los datos de ese usuario.
            ProfileScreen(
                navController = navController,
                isMyProfile = false,
                userRole = "Artista" // Simulamos que el perfil visitado es de un Artista
            )
        }

        // ... (El resto de las rutas no necesitan cambios)
        composable(Screen.Home.route) { MainScreenArtist(navController = navController) }
        composable(Screen.Convocatorias.route) { CallsScreenArtist(navController = navController) }
        composable(Screen.Mensajes.route) { MessagesScreen(navController = navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController = navController) }
        composable(Screen.Publicar.route) { /* Pantalla de publicar */ }
        composable(Screen.Chat.route) { ChatScreen(navController = navController, contactName = "Persona") }
        composable(Screen.LocalActions.route) { LocalActionsScreen(navController = navController) }
        composable(Screen.ReserveArtist.route) { ReserveArtistScreen(navController = navController) }
        composable(Screen.EventDetails.route) { EventDetailsScreen(navController = navController) }
        composable(Screen.PublishEvent.route) { PublishEventScreen(navController = navController) }
        composable(Screen.ManageApplications.route) { ManageApplicationsScreen(navController = navController) }
        composable(Screen.ReservationDetail.route) { ReservationDetailScreen(navController = navController) }
        composable(Screen.Editar.route) { EditProfileScreen(onBackClick = { navController.popBackStack() }, onSaveClick = { navController.popBackStack() }) }
        composable(Screen.Feed.route) { FeedScreen(navController = navController) }
        composable(Screen.SignUpInformationArtist.route) { ArtistSignUpScreen(navController = navController, onSignUpClick = { }, onBackClick = { navController.popBackStack() }) }
        composable(Screen.SignUpInformationFan.route) { FanRegistrationScreen(navController = navController) }
        composable(Screen.SignUpInformationBand.route) { BandSignUpScreen(navController = navController) }
        composable(Screen.SignUpInformationEstablishment.route) { EstablishmentRegistrationScreen(navController = navController) }
        composable(Screen.SignUpInformationCurator.route) { CuratorRegistrationScreen(navController = navController) }
        composable(Screen.ManagePosts.route) { ManagePostsScreen(navController = navController) }
        composable(Screen.PostComments.route) { PostCommentsScreen(navController = navController) }
    }
}
