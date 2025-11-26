package com.example.looksoon.ui.screens.mix

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.looksoon.R
import com.example.looksoon.model.User
import com.example.looksoon.repository.ChatRepository
import com.example.looksoon.ui.screens.artist.mainscreenartist.BottomNavBar
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist
import com.example.looksoon.ui.theme.*
import com.example.looksoon.ui.theme.navigation.Screen
import com.example.looksoon.ui.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    selectedTab: String = "Perfil",
    userId: String? = null
) {
    val user by profileViewModel.user.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState()
    val error by profileViewModel.error.collectAsState()

    val chatRepository = ChatRepository()
    val coroutineScope = rememberCoroutineScope()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val isOwnProfile = userId == null || userId == currentUserId

    // Estado para el botón de seguir
    var isFollowing by remember { mutableStateOf(false) }
    var isFollowLoading by remember { mutableStateOf(false) }

    // Cargar perfil al entrar
    LaunchedEffect(userId) {
        if (userId == null) {
            profileViewModel.loadCurrentUserProfile()
        } else {
            profileViewModel.loadUserProfile(userId)
            // Verificar si ya sigue al usuario
            isFollowing = checkIfFollowing(currentUserId, userId)
        }
    }

    // El estado en línea ahora se maneja globalmente en MainActivity

    Scaffold(
        bottomBar = {
            if (isOwnProfile) {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            popUpTo(route) { inclusive = true }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = error ?: "Error desconocido",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            profileViewModel.clearError()
                            if (userId == null) {
                                profileViewModel.loadCurrentUserProfile()
                            } else {
                                profileViewModel.loadUserProfile(userId)
                            }
                        }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            user != null -> {
                ProfileContent(
                    user = user!!,
                    isOwnProfile = isOwnProfile,
                    navController = navController,
                    innerPadding = innerPadding,
                    isFollowing = isFollowing,
                    isFollowLoading = isFollowLoading,
                    onFollowClick = {
                        coroutineScope.launch {
                            isFollowLoading = true
                            try {
                                if (isFollowing) {
                                    unfollowUser(currentUserId, userId!!)
                                    isFollowing = false
                                } else {
                                    followUser(currentUserId, userId!!)
                                    isFollowing = true
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                isFollowLoading = false
                            }
                        }
                    },
                    onMessageClick = { targetUser ->
                        coroutineScope.launch {
                            val chatId = chatRepository.getOrCreateChat(
                                otherUserId = targetUser.userId,
                                otherUserName = targetUser.name,
                                otherUserRole = targetUser.roles.firstOrNull() ?: "Usuario"
                            )
                            if (chatId.isNotEmpty()) {
                                navController.navigate("chat_conversation/$chatId/${targetUser.userId}/${targetUser.name}")
                            }
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileContent(
    user: User,
    isOwnProfile: Boolean,
    navController: NavHostController,
    innerPadding: PaddingValues,
    isFollowing: Boolean = false,
    isFollowLoading: Boolean = false,
    onFollowClick: () -> Unit = {},
    onMessageClick: (User) -> Unit,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header modificado para que la flecha funcione
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(MaterialTheme.colorScheme.primary, Color.Black)
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón izquierdo (Menú o Volver)
                IconButton(onClick = {
                    if (isOwnProfile) {
                        // TODO: Abrir menú
                    } else {
                        onBackClick() // ✅ Regresar al Feed
                    }
                }) {
                    Icon(
                        imageVector = if (isOwnProfile) Icons.Default.Menu else Icons.Default.ArrowBack,
                        contentDescription = if (isOwnProfile) "Menú" else "Volver",
                        tint = TextPrimary
                    )
                }

                // Título
                Text(
                    text = if (isOwnProfile) "Perfil" else user.name,
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                // Botón derecho (Notificaciones)
                IconButton(onClick = { /* TODO: Notificaciones */ }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = TextPrimary
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Card principal con info del usuario
            Surface(
                color = Surface,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Foto y datos básicos
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Foto de perfil
                        if (user.profileImageUrl.isNotEmpty()) {
                            AsyncImage(
                                model = user.profileImageUrl,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, PurplePrimary, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.foto),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, PurplePrimary, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = user.name,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "@${user.username}",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                            if (user.location.isNotEmpty()) {
                                Text(
                                    text = user.location,
                                    color = TextSecondary,
                                    fontSize = 14.sp
                                )
                            }

                            // Géneros musicales
                            if (user.genres.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Row {
                                    user.genres.take(2).forEach { genre ->
                                        Text(
                                            text = genre,
                                            color = TextPrimary,
                                            fontSize = 12.sp,
                                            modifier = Modifier
                                                .background(PurpleSecondary, RoundedCornerShape(8.dp))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tabs de roles
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("Artista", "Curador", "Local", "Fan").forEach { role ->
                            val isActive = user.roles.contains(role) || user.roles.contains(role.lowercase())
                            Surface(
                                color = if (isActive) PurplePrimary else Surface,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = role,
                                    color = if (isActive) TextPrimary else TextSecondary,
                                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Estadísticas
                    Surface(
                        color = Main,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "${user.eventsCount}",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text("Eventos", color = TextSecondary, fontSize = 12.sp)
                            }
                            Divider(
                                color = Divider,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(1.dp)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "${user.followersCount}",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text("Seguidores", color = TextSecondary, fontSize = 12.sp)
                            }
                            Divider(
                                color = Divider,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(1.dp)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "${user.followingCount}",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text("Siguiendo", color = TextSecondary, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Biografía
            if (user.bio.isNotEmpty()) {
                Surface(
                    color = Surface,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Biografía",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            user.bio,
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Info de contacto
            Surface(
                color = Surface,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (user.email.isNotEmpty()) {
                        ProfileInfoItem("Correo", user.email, Icons.Default.Email)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                    }
                    if (user.phone.isNotEmpty()) {
                        ProfileInfoItem("Teléfono", user.phone, Icons.Default.Call)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                    }
                    if (user.location.isNotEmpty()) {
                        ProfileInfoItem("Ubicación", user.location, Icons.Default.LocationOn)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                    }
                    ProfileInfoItem("Roles", user.roles.joinToString(", "), Icons.Default.Person)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción
            if (isOwnProfile) {
                Button(
                    onClick = { navController.navigate(Screen.Editar.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PurplePrimary,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Editar perfil", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onFollowClick,
                        enabled = !isFollowLoading,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFollowing) Surface else PurplePrimary,
                            contentColor = if (isFollowing) PurplePrimary else TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isFollowLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                if (isFollowing) Icons.Default.PersonRemove else Icons.Default.PersonAdd,
                                contentDescription = if (isFollowing) "Dejar de seguir" else "Seguir",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                if (isFollowing) "Siguiendo" else "Seguir",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Button(
                        onClick = { onMessageClick(user) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Surface,
                            contentColor = PurplePrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Default.ChatBubble,
                            contentDescription = "Mensaje",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mensaje", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = PurplePrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, color = TextPrimary, fontSize = 14.sp)
        }
    }
}

// Funciones para seguir/dejar de seguir
suspend fun checkIfFollowing(currentUserId: String, targetUserId: String): Boolean {
    return try {
        val db = FirebaseFirestore.getInstance()
        val doc = db.collection("Follows")
            .document("${currentUserId}_$targetUserId")
            .get()
            .await()
        doc.exists()
    } catch (e: Exception) {
        false
    }
}

suspend fun followUser(currentUserId: String, targetUserId: String) {
    try {
        val db = FirebaseFirestore.getInstance()

        // Crear documento de seguimiento
        db.collection("Follows")
            .document("${currentUserId}_$targetUserId")
            .set(mapOf(
                "followerId" to currentUserId,
                "followingId" to targetUserId,
                "timestamp" to System.currentTimeMillis()
            ))
            .await()

        // Actualizar contadores (buscar en todas las colecciones)
        val collections = listOf("Artista", "Bandas", "Fan", "Curador", "Establecimiento")

        for (collection in collections) {
            val currentUserDoc = db.collection(collection).document(currentUserId).get().await()
            if (currentUserDoc.exists()) {
                db.collection(collection).document(currentUserId)
                    .update("followingCount", (currentUserDoc.getLong("followingCount") ?: 0) + 1)
                    .await()
                break
            }
        }

        for (collection in collections) {
            val targetUserDoc = db.collection(collection).document(targetUserId).get().await()
            if (targetUserDoc.exists()) {
                db.collection(collection).document(targetUserId)
                    .update("followersCount", (targetUserDoc.getLong("followersCount") ?: 0) + 1)
                    .await()
                break
            }
        }
    } catch (e: Exception) {
        throw e
    }
}

suspend fun unfollowUser(currentUserId: String, targetUserId: String) {
    try {
        val db = FirebaseFirestore.getInstance()

        // Eliminar documento de seguimiento
        db.collection("Follows")
            .document("${currentUserId}_$targetUserId")
            .delete()
            .await()

        // Actualizar contadores
        val collections = listOf("Artista", "Bandas", "Fan", "Curador", "Establecimiento")

        for (collection in collections) {
            val currentUserDoc = db.collection(collection).document(currentUserId).get().await()
            if (currentUserDoc.exists()) {
                db.collection(collection).document(currentUserId)
                    .update("followingCount", maxOf(0, (currentUserDoc.getLong("followingCount") ?: 0) - 1))
                    .await()
                break
            }
        }

        for (collection in collections) {
            val targetUserDoc = db.collection(collection).document(targetUserId).get().await()
            if (targetUserDoc.exists()) {
                db.collection(collection).document(targetUserId)
                    .update("followersCount", maxOf(0, (targetUserDoc.getLong("followersCount") ?: 0) - 1))
                    .await()
                break
            }
        }
    } catch (e: Exception) {
        throw e
    }
}