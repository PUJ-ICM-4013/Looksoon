package com.example.looksoon.ui.screens.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.looksoon.R
import com.example.looksoon.model.Chat
import com.example.looksoon.ui.screens.artist.mainscreenartist.BottomNavBar
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist
import com.example.looksoon.ui.theme.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

// Data class para información del usuario
data class UserChatInfo(
    val name: String = "",
    val role: String = "",
    val profileImageUrl: String = "",
    val isOnline: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsListScreen(
    onChatClick: (chatId: String, receiverId: String, receiverName: String) -> Unit,
    onBackClick: () -> Unit,
    navController: NavController,
    viewModel: ChatsListViewModel = viewModel()
) {
    val chats by viewModel.chats.collectAsState()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val coroutineScope = rememberCoroutineScope()

    var selectedFilter by remember { mutableStateOf("Todos") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var chatToDelete by remember { mutableStateOf<Chat?>(null) }

    // Estado para almacenar la información de los usuarios
    var usersInfo by remember { mutableStateOf<Map<String, UserChatInfo>>(emptyMap()) }

    // Cargar información de usuarios cuando cambian los chats
    LaunchedEffect(chats) {
        val userIds = chats.flatMap { it.participants }.distinct().filter { it != currentUserId }
        if (userIds.isNotEmpty()) {
            val info = loadUsersInfo(userIds)
            usersInfo = info
        }
    }

    val filteredChats = when (selectedFilter) {
        "No leídos" -> chats.filter { (it.unreadCount[currentUserId] ?: 0) > 0 }
        else -> chats
    }

    // Diálogo de confirmación para eliminar
    if (showDeleteDialog && chatToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar conversación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta conversación?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            deleteChat(chatToDelete!!.chatId)
                            showDeleteDialog = false
                            chatToDelete = null
                        }
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedTab = "Mensajes",
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            HeaderArtist(
                section = "Mensajes",
                iconLeft = Icons.Default.ArrowBack,
                iconRight = Icons.Default.MoreVert,
                contentDescriptionLeft = "Atrás",
                contentDescriptionRight = "Opciones",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            // Filtros
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = selectedFilter == "Todos",
                    onClick = { selectedFilter = "Todos" },
                    label = { Text("Todos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                FilterChip(
                    selected = selectedFilter == "No leídos",
                    onClick = { selectedFilter = "No leídos" },
                    label = { Text("No leídos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )
            }

            // Lista de chats
            if (filteredChats.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (selectedFilter == "No leídos")
                                "No tienes mensajes sin leer"
                            else
                                "No tienes conversaciones aún",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Inicia una conversación desde el perfil de un usuario",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(filteredChats, key = { it.chatId }) { chat ->
                        val otherUserId = chat.participants.firstOrNull { it != currentUserId } ?: ""
                        val userInfo = usersInfo[otherUserId]

                        ChatCardItem(
                            chat = chat,
                            currentUserId = currentUserId,
                            userInfo = userInfo,
                            onClick = {
                                onChatClick(
                                    chat.chatId,
                                    otherUserId,
                                    userInfo?.name ?: chat.participantsInfo[otherUserId]?.name ?: "Usuario"
                                )
                            },
                            onLongClick = {
                                chatToDelete = chat
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatCardItem(
    chat: Chat,
    currentUserId: String,
    userInfo: UserChatInfo?,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    val otherUserId = chat.participants.firstOrNull { it != currentUserId } ?: ""
    val unreadCount = chat.unreadCount[currentUserId] ?: 0

    // Usar info de Users si está disponible, sino usar la de participantsInfo
    val userName = userInfo?.name ?: chat.participantsInfo[otherUserId]?.name ?: "Usuario"
    val userRole = userInfo?.role ?: chat.participantsInfo[otherUserId]?.role ?: ""
    val isOnline = userInfo?.isOnline ?: false
    val profileImageUrl = userInfo?.profileImageUrl ?: ""

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF2C2C2C))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar con indicador en línea
            Box {
                if (profileImageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = profileImageUrl,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.foto),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                // Indicador en línea (solo si está online)
                if (isOnline) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.BottomEnd)
                            .background(Color(0xFF4CAF50), CircleShape)
                            .padding(2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información del chat
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = userName,
                        color = Color.White,
                        fontWeight = if (unreadCount > 0) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )

                    // Mostrar rol
                    if (userRole.isNotEmpty()) {
                        Text(
                            text = userRole,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Indicador "escribiendo..."
                if (chat.isTyping[otherUserId] == true) {
                    Text(
                        text = "escribiendo...",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                } else {
                    Text(
                        text = chat.lastMessage.ifEmpty { "Sin mensajes" },
                        color = if (unreadCount > 0) Color.White else Color.Gray,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = if (unreadCount > 0) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }

            // Columna derecha
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(60.dp)
            ) {
                Text(
                    text = formatTime(chat.lastMessageTime),
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                if (unreadCount > 0) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = if (unreadCount > 99) "99+" else unreadCount.toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

// Función para cargar información de usuarios desde Firestore
// Busca en todas las colecciones: Artista, Bandas, Curador, Establecimiento, Fan
suspend fun loadUsersInfo(userIds: List<String>): Map<String, UserChatInfo> {
    val db = FirebaseFirestore.getInstance()
    val usersMap = mutableMapOf<String, UserChatInfo>()

    // Colecciones donde buscar usuarios
    val collections = listOf("Artista", "Bandas", "Curador", "Establecimiento", "Fan")

    try {
        userIds.forEach { userId ->
            try {
                // Buscar en cada colección hasta encontrar el usuario
                for (collection in collections) {
                    val userDoc = db.collection(collection)
                        .document(userId)
                        .get()
                        .await()

                    if (userDoc.exists()) {
                        val userInfo = UserChatInfo(
                            name = userDoc.getString("name") ?:
                            userDoc.getString("fullName") ?:
                            userDoc.getString("artisticName") ?:
                            userDoc.getString("venueName") ?: "",
                            role = collection, // El nombre de la colección es el rol
                            profileImageUrl = userDoc.getString("profileImageUrl") ?:
                            userDoc.getString("profileImage") ?:
                            userDoc.getString("photoUrl") ?: "",
                            isOnline = userDoc.getBoolean("isOnline") ?: false
                        )
                        usersMap[userId] = userInfo
                        break // Ya encontramos el usuario, salimos del loop
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return usersMap
}

// Función para eliminar chat
suspend fun deleteChat(chatId: String) {
    try {
        val db = FirebaseFirestore.getInstance()

        // Eliminar todos los mensajes
        val messages = db.collection("Chats")
            .document(chatId)
            .collection("Messages")
            .get()
            .await()

        val batch = db.batch()
        messages.documents.forEach { doc ->
            batch.delete(doc.reference)
        }
        batch.commit().await()

        // Eliminar el chat
        db.collection("Chats")
            .document(chatId)
            .delete()
            .await()

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun formatTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60000 -> "Ahora"
        diff < 3600000 -> "${diff / 60000}m"
        diff < 86400000 -> {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
        diff < 604800000 -> {
            val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
        else -> {
            val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}