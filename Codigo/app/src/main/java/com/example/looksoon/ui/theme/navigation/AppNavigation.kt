package com.example.looksoon.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.Audius.TracksScreen
import com.example.looksoon.ui.screens.SharedViewModel
import com.example.looksoon.ui.screens.login_register.SignUp.ArtistSignUpScreen
import com.example.looksoon.ui.screens.login_register.SignUp.BandSignUpScreen
import com.example.looksoon.ui.screens.artist.CallsScreenArtist
import com.example.looksoon.ui.screens.mix.ChatScreen
import com.example.looksoon.ui.screens.mix.CreatePostScreen
import com.example.looksoon.ui.screens.login_register.SignUp.CuratorSignUpScreen
import com.example.looksoon.ui.screens.curator.CuratorScreen
import com.example.looksoon.ui.screens.mix.EditProfileScreen
import com.example.looksoon.ui.screens.login_register.SignUp.EstablishmentSignUpScreen
import com.example.looksoon.ui.screens.mix.EventDetailsArtistScreen
import com.example.looksoon.ui.screens.establishment.EventDetailsScreen
import com.example.looksoon.ui.screens.fan.ExploreEventsScreen
import com.example.looksoon.ui.screens.fan.FanInviteContactsScreen
import com.example.looksoon.ui.screens.login_register.SignUp.FanSignUpScreen
import com.example.looksoon.ui.screens.mix.FeedScreen
import com.example.looksoon.ui.screens.login_register.forgot_password.ForgotPasswordScreen
import com.example.looksoon.ui.screens.establishment.LocalActionsScreen
import com.example.looksoon.ui.screens.artist.mainscreenartist.MainScreenArtist
import com.example.looksoon.ui.screens.establishment.ManageApplicationsScreen
import com.example.looksoon.ui.screens.fan.ProfileFanScreen
import com.example.looksoon.ui.screens.mix.ProfileScreen
import com.example.looksoon.ui.screens.establishment.PublishEventScreen
import com.example.looksoon.ui.screens.ReservationDetailScreen
import com.example.looksoon.ui.screens.artist.mainscreenartist.MainScreenArtistViewModel
import com.example.looksoon.ui.screens.establishment.ReserveArtistScreen
import com.example.looksoon.ui.screens.login_register.SignUp.SignUpScreen
import com.example.looksoon.ui.screens.login_register.forgot_password.ForgotPasswordViewModel
import com.example.looksoon.ui.screens.login_register.login.LoginScreen
import com.example.looksoon.ui.screens.login_register.login.LoginViewModel
import com.example.looksoon.ui.screens.smarttools.PerformanceAnalyzerScreen
import com.example.looksoon.ui.screens.smarttools.SmartStoryCreatorScreen
import com.example.looksoon.ui.screens.smarttools.tourtracker.TourTrackerIntelligenceScreen
import com.example.looksoon.ui.screens.login_register.SignUp.SignUpViewModel
import com.example.looksoon.ui.viewmodels.PostViewModel
import com.example.looksoon.ui.viewmodels.ProfileViewModel

// IMPORTS PARA CHAT
import com.example.looksoon.ui.screens.chat.ChatScreen as NewChatScreen
import com.example.looksoon.ui.screens.chat.ChatsListScreen
import com.example.looksoon.ui.screens.chat.TestUsersScreen

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
    object SignUpInformationCurator : Screen("SignUpInformationCurador")
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
    object Curator: Screen("mainCurador")
    object CreatePost: Screen("create_post")
    object TourTrackerIntelligence : Screen("tour_tracker_intelligence")
    object SmartStoryCreator : Screen("smart_story_creator")
    object PerformanceAnalyzer : Screen("performance_analyzer")

    // RUTAS PARA CHAT
    object ChatsList : Screen("chats_list")
    object ChatConversation : Screen("chat_conversation")

    object OtherUserProfile : Screen("other_user_profile")


    object audiusApi: Screen("audius")

}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // ViewModels compartidos
    val profileViewModel: ProfileViewModel = viewModel()
    val postViewModel: PostViewModel = viewModel()
    val signUpViewModel: SignUpViewModel = viewModel()
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        // ============================================
        // INICIO DE SESIÓN Y REGISTRO
        // ============================================

        composable(Screen.Login.route) {
            val loginViewModel = viewModel<LoginViewModel>()
            LoginScreen(
                onArtistClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onEstablishmentClick = {
                    navController.navigate(Screen.LocalActions.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onFanClick = {
                    navController.navigate(Screen.Feed.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onCuratorClick = {
                    navController.navigate(Screen.Curator.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onSignUpClick = {
                    navController.navigate(Screen.SignUp.route)
                },
                viewModel = loginViewModel
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onArtistClick = { navController.navigate(Screen.SignUpInformationArtist.route) },
                onBandClick = { navController.navigate(Screen.SignUpInformationBand.route) },
                onFanClick = { navController.navigate(Screen.SignUpInformationFan.route) },
                onEstablishmentClick = { navController.navigate(Screen.SignUpInformationEstablishment.route) },
                onCuratorClick = { navController.navigate(Screen.SignUpInformationCurator.route) },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SignUpInformationFan.route) {
            FanSignUpScreen(
                onBackClick = { navController.popBackStack() },
                onSignUpClick = { navController.navigate(Screen.Login.route) },
                viewModel = signUpViewModel
            )
        }

        composable(Screen.SignUpInformationBand.route) {
            BandSignUpScreen(
                onBackClick = { navController.popBackStack() },
                onSignUpClick = { navController.navigate(Screen.Login.route) },
                viewModel = signUpViewModel
            )
        }

        composable(Screen.SignUpInformationEstablishment.route) {
            EstablishmentSignUpScreen(
                onBackClick = { navController.popBackStack() },
                onSignUpClick = { navController.navigate(Screen.Login.route) },
                viewModel = signUpViewModel
            )
        }

        composable(Screen.SignUpInformationCurator.route) {
            CuratorSignUpScreen(
                onBackClick = { navController.popBackStack() },
                onSignUpClick = { navController.navigate(Screen.Login.route) },
                viewModel = signUpViewModel
            )
        }

        composable(Screen.SignUpInformationArtist.route) {
            ArtistSignUpScreen(
                onSignUpClick = { navController.navigate(Screen.Login.route) },
                onBackClick = { navController.popBackStack() },
                viewModel = signUpViewModel
            )
        }

        composable(Screen.ForgotPassword.route) {
            val forgotPasswordViewModel = viewModel<ForgotPasswordViewModel>()
            ForgotPasswordScreen(
                onLinkClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onButtonClick = {},
                viewModel = forgotPasswordViewModel
            )
        }

        // ============================================
        // PANTALLAS PRINCIPALES
        // ============================================

        composable(Screen.Home.route) {
            val mainScreenArtistViewModel = viewModel<MainScreenArtistViewModel>()
            MainScreenArtist(
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(Screen.Home.route)
                    }
                },
                seeMoreClick = {},
                viewModel = mainScreenArtistViewModel,
                role = "Artista",
                onSmartToolSelected = { tool ->
                    when (tool) {
                        "tour_tracker" -> navController.navigate(Screen.TourTrackerIntelligence.route)
                        "story_creator" -> navController.navigate(Screen.SmartStoryCreator.route)
                        "performance_analyzer" -> navController.navigate(Screen.PerformanceAnalyzer.route)
                        "test_users" -> navController.navigate("test_users")
                    }
                }
                ,
                onIconRightClick = {
                    navController.navigate(Screen.audiusApi.route)
                }
            )
        }

        composable(Screen.Convocatorias.route) {
            CallsScreenArtist(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }

        // ✅ LISTA DE CHATS (solo una vez)
        composable(Screen.Mensajes.route) {
            ChatsListScreen(
                onChatClick = { chatId, receiverId, receiverName ->
                    // ✅ SIN BARRA AL FINAL
                    navController.navigate("chat_conversation/$chatId/$receiverId/$receiverName")
                },
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }

        // ✅ PANTALLA DE CONVERSACIÓN INDIVIDUAL
        composable("chat_conversation/{chatId}/{receiverId}/{receiverName}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val receiverId = backStackEntry.arguments?.getString("receiverId") ?: ""
            val receiverName = backStackEntry.arguments?.getString("receiverName") ?: ""

            NewChatScreen(
                chatId = chatId,
                receiverId = receiverId,
                receiverName = receiverName,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ✅ USUARIOS DE PRUEBA
        composable("test_users") {
            TestUsersScreen(navController = navController)
        }

        // Perfil propio
        composable(Screen.Perfil.route) {
            ProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel,
                userId = null
            )
        }

        // Perfil de otro usuario
        composable("user_profile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel,
                userId = userId
            )
        }

        composable(Screen.Publicar.route) {
            CreatePostScreen(
                navController = navController,
                postViewModel = postViewModel
            )
        }

        composable(Screen.Feed.route) {
            FeedScreen(
                navController = navController,
                postViewModel = postViewModel
            )
        }

        composable(Screen.Editar.route) {
            EditProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }

        composable(Screen.CreatePost.route) {
            CreatePostScreen(
                navController = navController,
                postViewModel = postViewModel
            )
        }

        // ============================================
        // HERRAMIENTAS INTELIGENTES
        // ============================================

        composable(Screen.TourTrackerIntelligence.route) {
            TourTrackerIntelligenceScreen(
                onBack = { navController.popBackStack() },
                onSmartToolSelected = { tool ->
                    when (tool) {
                        "tour_tracker" -> navController.navigate(Screen.TourTrackerIntelligence.route)
                        "story_creator" -> navController.navigate(Screen.SmartStoryCreator.route)
                        "performance_analyzer" -> navController.navigate(Screen.PerformanceAnalyzer.route)
                    }
                }
            )
        }

        composable(Screen.SmartStoryCreator.route) {
            SmartStoryCreatorScreen(
                onBack = { navController.popBackStack() },
                onSmartToolSelected = { tool ->
                    when (tool) {
                        "tour_tracker" -> navController.navigate(Screen.TourTrackerIntelligence.route)
                        "story_creator" -> navController.navigate(Screen.SmartStoryCreator.route)
                        "performance_analyzer" -> navController.navigate(Screen.PerformanceAnalyzer.route)
                    }
                }
            )
        }

        composable(Screen.PerformanceAnalyzer.route) {
            PerformanceAnalyzerScreen(
                onBack = { navController.popBackStack() },
                onSmartToolSelected = { tool ->
                    when (tool) {
                        "tour_tracker" -> navController.navigate(Screen.TourTrackerIntelligence.route)
                        "story_creator" -> navController.navigate(Screen.SmartStoryCreator.route)
                        "performance_analyzer" -> navController.navigate(Screen.PerformanceAnalyzer.route)
                    }
                }
            )
        }

        // ============================================
        // ESTABLECIMIENTO
        // ============================================

        composable(Screen.LocalActions.route) {
            LocalActionsScreen(navController = navController)
        }

        composable(Screen.ReserveArtist.route) {
            ReserveArtistScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }

        composable(Screen.EventDetails.route) {
            EventDetailsScreen(navController = navController)
        }

        composable(Screen.PublishEvent.route) {
            PublishEventScreen(navController = navController)
        }

        composable(Screen.ManageApplications.route) {
            ManageApplicationsScreen(navController = navController)
        }

        composable(Screen.ReservationDetail.route) {
            ReservationDetailScreen(navController = navController)
        }

        // ============================================
        // OTRAS PANTALLAS
        // ============================================

        composable(Screen.EventDetailsArtist.route) {
            EventDetailsArtistScreen(navController = navController)
        }

        composable(Screen.ExploreEventsFan.route) {
            ExploreEventsScreen(navController = navController)
        }

        composable(Screen.ProfileFan.route) {
            ProfileFanScreen(navController = navController)
        }

        composable(Screen.MainFan.route) {
            ExploreEventsScreen(navController = navController)
        }

        composable(Screen.Invite.route) {
            FanInviteContactsScreen(navController = navController)
        }

        composable(Screen.Curator.route) {
            CuratorScreen()
        }

        // Chat viejo (si aún lo usas)
        composable(Screen.Chat.route) {
            ChatScreen(navController = navController, contactName = "Persona")
        }

        // ============================================
        // AUDIUS
        // ============================================

        composable(Screen.audiusApi.route) {

            TracksScreen()
        }
    }
}