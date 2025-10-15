package com.example.faunafinder.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.screens.login_register.ArtistSignUpScreen
import com.example.looksoon.ui.screens.login_register.BandSignUpScreen
import com.example.looksoon.ui.screens.artist.CallsScreenArtist
import com.example.looksoon.ui.screens.mix.ChatScreen
import com.example.looksoon.ui.screens.mix.CreatePostScreen
import com.example.looksoon.ui.screens.login_register.CuratorRegistrationScreen
import com.example.looksoon.ui.screens.curator.CuratorScreen
import com.example.looksoon.ui.screens.mix.EditProfileScreen
import com.example.looksoon.ui.screens.login_register.EstablishmentRegistrationScreen
import com.example.looksoon.ui.screens.mix.EventDetailsArtistScreen
import com.example.looksoon.ui.screens.establishment.EventDetailsScreen
import com.example.looksoon.ui.screens.fan.ExploreEventsScreen
import com.example.looksoon.ui.screens.fan.FanInviteContactsScreen
import com.example.looksoon.ui.screens.login_register.FanRegistrationScreen
import com.example.looksoon.ui.screens.mix.FeedScreen
import com.example.looksoon.ui.screens.login_register.forgot_password.ForgotPasswordScreen
import com.example.looksoon.ui.screens.establishment.LocalActionsScreen
import com.example.looksoon.ui.screens.login_register.login.LoginScreen
import com.example.looksoon.ui.screens.artist.mainscreenartist.MainScreenArtist
import com.example.looksoon.ui.screens.establishment.ManageApplicationsScreen
import com.example.looksoon.ui.screens.mix.MessagesScreen
import com.example.looksoon.ui.screens.fan.ProfileFanScreen
import com.example.looksoon.ui.screens.mix.ProfileScreen
import com.example.looksoon.ui.screens.establishment.PublishEventScreen
import com.example.looksoon.ui.screens.ReservationDetailScreen
import com.example.looksoon.ui.screens.artist.mainscreenartist.MainScreenArtistViewModel
import com.example.looksoon.ui.screens.establishment.ReserveArtistScreen
import com.example.looksoon.ui.screens.login_register.SignUpScreen
import com.example.looksoon.ui.screens.login_register.forgot_password.ForgotPasswordViewModel
import com.example.looksoon.ui.screens.login_register.login.LoginViewModel

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

    object Curator: Screen("mainCurator")

    object CreatePost: Screen("create_post")

    //Crear Screens
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        //Inicio de Sesion y Registro

        //Para LoginScreen------------------------------------------------------------

        composable(Screen.Login.route) {
            val loginViewModel = viewModel<LoginViewModel>()

            LoginScreen(onArtistClick = {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
        }}, onEstablishmentClick = {
            navController.navigate(Screen.LocalActions.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }, onFanClick = {
            navController.navigate(Screen.Feed.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }, onCuratorClick = {
            navController.navigate(Screen.Curator.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }, onForgotPasswordClick = {
            navController.navigate(Screen.ForgotPassword.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }, onSignUpClick = {
            navController.navigate(Screen.SignUp.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        },
                viewModel = loginViewModel
            ) }

        //Para SignUpScreen------------------------------------------------------------
        composable(Screen.SignUp.route) { SignUpScreen(onArtistClick = {
            navController.navigate(Screen.SignUpInformationArtist.route) {
                popUpTo(Screen.SignUpInformationArtist.route) { inclusive = true }
            }
        }, onBandClick = {
            navController.navigate(Screen.SignUpInformationBand.route) {
                popUpTo(Screen.SignUpInformationBand.route) { inclusive = true }
            }
        }, onFanClick = {
            navController.navigate(Screen.SignUpInformationFan.route) {
                popUpTo(Screen.SignUpInformationFan.route) { inclusive = true }
            }
        }, onEstablishmentClick = {
            navController.navigate(Screen.SignUpInformationEstablishment.route) {
                popUpTo(Screen.SignUpInformationEstablishment.route) { inclusive = true }
            }
        }, onCuratorClick = {
            navController.navigate(Screen.SignUpInformationCurator.route) {
                popUpTo(Screen.SignUpInformationCurator.route) { inclusive = true }
            }
        },
            onLoginClick = {navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }}) }


        //Para SignUpInformationScreen------------------------------------------------------------


        composable(Screen.SignUpInformationFan.route) { FanRegistrationScreen(
            onBackClick = {navController.popBackStack()},
            onSignUpClick = {navController.navigate(Screen.SignUp.route){
                popUpTo(Screen.SignUp.route) { inclusive = true }
            } }
        ) }
        composable(Screen.SignUpInformationBand.route) { BandSignUpScreen(
            onBackClick = {navController.popBackStack()},
            onSignUpClick = {navController.navigate(Screen.SignUp.route){
                popUpTo(Screen.SignUp.route) { inclusive = true }
            } }
        ) }
        composable(Screen.SignUpInformationEstablishment.route) { EstablishmentRegistrationScreen(
            onBackClick = {navController.popBackStack()},
            onSignUpClick = {navController.navigate(Screen.SignUp.route){
                popUpTo(Screen.SignUp.route) { inclusive = true }
            } }) }
        composable(Screen.SignUpInformationCurator.route) {  CuratorRegistrationScreen(
            onBackClick = {navController.popBackStack()},
            onSignUpClick = {navController.navigate(Screen.SignUp.route){
                popUpTo(Screen.SignUp.route) { inclusive = true }
            } }) }
        composable(Screen.SignUpInformationArtist.route) { ArtistSignUpScreen(
            onSignUpClick = { navController.navigate(Screen.SignUp.route){
                popUpTo(Screen.SignUp.route) { inclusive = true }
            } },
            onBackClick = { navController.popBackStack() }
        ) }


        //Para pantallas de Artista

        composable(Screen.Home.route) {
            val mainScreenArtistViewModel = viewModel<MainScreenArtistViewModel>()
            MainScreenArtist(
            onTabSelected = {
                    route ->
                navController.navigate(route) {
                    launchSingleTop = true
                    popUpTo(Screen.Home.route)
                }
            },
            seeMoreClick = {

            },
                viewModel = mainScreenArtistViewModel


        ) }


        // Para forgot password
        composable(Screen.ForgotPassword.route) {
            val viewModel = viewModel<ForgotPasswordViewModel>()
            ForgotPasswordScreen(
            onLinkClick = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            },
            onButtonClick = {

            },
                viewModel = viewModel
        ) }

        composable(Screen.Convocatorias.route) { CallsScreenArtist(navController = navController) }
        composable(Screen.Mensajes.route) { MessagesScreen(navController = navController) }
        composable(Screen.Perfil.route) { ProfileScreen(navController = navController) }


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




        composable(Screen.EventDetailsArtist.route) { EventDetailsArtistScreen(navController = navController) }

        composable(Screen.ExploreEventsFan.route) { ExploreEventsScreen(navController = navController) }

        composable(Screen.ProfileFan.route) { ProfileFanScreen(navController = navController) }
        composable(Screen.MainFan.route) { ExploreEventsScreen(navController = navController) }

        composable(Screen.Invite.route) { FanInviteContactsScreen(navController = navController) }

        composable(Screen.Curator.route) { CuratorScreen() }

        composable(Screen.CreatePost.route) { CreatePostScreen(navController = navController) }
    }
}
