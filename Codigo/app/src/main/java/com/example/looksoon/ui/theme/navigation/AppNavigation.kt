package com.example.faunafinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.screens.ArtistSignUpScreen
import com.example.looksoon.ui.screens.BandSignUpScreen
import com.example.looksoon.ui.screens.artist.CallsScreenArtist
import com.example.looksoon.ui.screens.mix.ChatScreen
import com.example.looksoon.ui.screens.mix.CreatePostScreen
import com.example.looksoon.ui.screens.CuratorRegistrationScreen
import com.example.looksoon.ui.screens.curator.CuratorScreen
import com.example.looksoon.ui.screens.mix.EditProfileScreen
import com.example.looksoon.ui.screens.EstablishmentRegistrationScreen
import com.example.looksoon.ui.screens.mix.EventDetailsArtistScreen
import com.example.looksoon.ui.screens.establishment.EventDetailsScreen
import com.example.looksoon.ui.screens.fan.ExploreEventsScreen
import com.example.looksoon.ui.screens.fan.FanInviteContactsScreen
import com.example.looksoon.ui.screens.FanRegistrationScreen
import com.example.looksoon.ui.screens.mix.FeedScreen
import com.example.looksoon.ui.screens.ForgotPasswordScreen
import com.example.looksoon.ui.screens.establishment.LocalActionsScreen
import com.example.looksoon.ui.screens.LoginScreen
import com.example.looksoon.ui.screens.artist.MainScreenArtist
import com.example.looksoon.ui.screens.establishment.ManageApplicationsScreen
import com.example.looksoon.ui.screens.mix.MessagesScreen
import com.example.looksoon.ui.screens.fan.ProfileFanScreen
import com.example.looksoon.ui.screens.mix.ProfileScreen
import com.example.looksoon.ui.screens.establishment.PublishEventScreen
import com.example.looksoon.ui.screens.ReservationDetailScreen
import com.example.looksoon.ui.screens.establishment.ReserveArtistScreen
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

    object EventDetailsArtist : Screen("event_details_artist")


    object ForgotPassword : Screen("forgot_password")

    object ExploreEventsFan: Screen("explore_events_fan")

    object ProfileFan : Screen("Perfil Fan")

    object MainFan : Screen("Inicio Fan")

    object Invite: Screen("Invitar")

    object Search: Screen("Buscar")

    object curator: Screen("mainCurator")

    object CreatePost: Screen("create_post")

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
        composable(Screen.Publicar.route) { CreatePostScreen(navController = navController) }
        composable(Screen.Chat.route) { ChatScreen(navController = navController, contactName = "Persona") }

        composable(Screen.LocalActions.route) { LocalActionsScreen(navController = navController) }
        composable(Screen.ReserveArtist.route) { ReserveArtistScreen(navController = navController) }
        composable(Screen.EventDetails.route) { EventDetailsScreen(navController = navController) }
        composable(Screen.PublishEvent.route) { PublishEventScreen(navController = navController) }
        composable(Screen.ManageApplications.route) { ManageApplicationsScreen(navController = navController) }
        composable(Screen.ReservationDetail.route) { ReservationDetailScreen(navController = navController) }

        //Colocar composable de cad screen
        composable(Screen.Editar.route) {
            EditProfileScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Feed.route) { FeedScreen(navController = navController) }
        composable(Screen.SignUpInformationArtist.route) { ArtistSignUpScreen(navController = navController,
            onSignUpClick = { },
            onBackClick = { navController.popBackStack() }
        ) }

        composable(Screen.SignUpInformationFan.route) { FanRegistrationScreen(navController = navController) }
        composable(Screen.SignUpInformationBand.route) { BandSignUpScreen(navController = navController) }
        composable(Screen.SignUpInformationEstablishment.route) { EstablishmentRegistrationScreen(navController = navController) }
        composable(Screen.SignUpInformationCurator.route) {  CuratorRegistrationScreen(navController = navController) }

        composable(Screen.EventDetailsArtist.route) { EventDetailsArtistScreen(navController = navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController = navController) }

        composable(Screen.ExploreEventsFan.route) { ExploreEventsScreen(navController = navController) }

        composable(Screen.ProfileFan.route) { ProfileFanScreen(navController = navController) }
        composable(Screen.MainFan.route) { ExploreEventsScreen(navController = navController) }

        composable(Screen.Invite.route) { FanInviteContactsScreen(navController = navController) }

        composable(Screen.curator.route) { CuratorScreen() }

        composable(Screen.CreatePost.route) { CreatePostScreen(navController = navController) }
    }
}
