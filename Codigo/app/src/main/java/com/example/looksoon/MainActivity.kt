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