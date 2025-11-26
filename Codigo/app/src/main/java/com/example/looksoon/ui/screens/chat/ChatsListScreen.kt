package com.example.looksoon.ui.screens.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.looksoon.model.Chat
import com.example.looksoon.ui.screens.artist.mainscreenartist.BottomNavBar
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist
import com.example.looksoon.ui.theme.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*


// Data class para guardar la info del usuario en cada chat
data class UserChatInfo(
    val name: String = "",
    val role: String = "",
    val profileImageUrl: String = "",
    val isOnline: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
    var usersInfo by remember { mutableStateOf<Map<String, UserChatInfo>>(emptyMap()) }

    // Escuchar chats en tiempo real
    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance()
            .collection("Chats")
            .whereArrayContains("participants", currentUserId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val chatList = snapshot.documents.mapNotNull { it.toObject(Chat::class.java) }
                    viewModel.setChats(chatList)
                }
            }
    }

    // Leer datos de usuarios en tiempo real
    LaunchedEffect(chats) {
        val userIds = chats.flatMap { it.participants }.distinct().filter { it != currentUserId }
        if (userIds.isNotEmpty()) {
            loadUsersInfoRealtime(userIds) { info ->
                usersInfo = info
            }
        }
    }

    val filteredChats = when (selectedFilter) {
        "No leídos" -> chats.filter { (it.unreadCount[currentUserId] ?: 0) > 0 }
        else -> chats
    }

    // Dialogo para eliminar chat
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
                ) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
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
                modifier = Modifier.fillMaxWidth().height(56.dp)
            )

            // Filtros
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = selectedFilter == "Todos",
                    onClick = { selectedFilter = "Todos" },
                    label = { Text("Todos") }
                )
                Spacer(modifier = Modifier.width(12.dp))
                FilterChip(
                    selected = selectedFilter == "No leídos",
                    onClick = { selectedFilter = "No leídos" },
                    label = { Text("No leídos") }
                )
            }

            // Lista de chats
            if (filteredChats.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes conversaciones aún", color = Color.White)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
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
                                    userInfo?.name ?: "Usuario"
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

// ----------- CARD VISUAL DEL CHAT -----------------
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatCardItem(
    chat: Chat,
    currentUserId: String,
    userInfo: UserChatInfo?,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val unreadCount = chat.unreadCount[currentUserId] ?: 0
    val otherUserId = chat.participants.firstOrNull { it != currentUserId } ?: ""

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF9B4DFF))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar con indicador en línea
            Box {
                if (userInfo?.profileImageUrl?.isNotEmpty() == true) {
                    AsyncImage(
                        model = userInfo.profileImageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Avatar por defecto con inicial
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userInfo?.name?.firstOrNull()?.toString()?.uppercase() ?: "?",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // ✅ CÍRCULO VERDE SI ESTÁ EN LÍNEA
                if (userInfo?.isOnline == true) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.BottomEnd)
                            .background(Color(0xFF00FF00), CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = userInfo?.name ?: "Usuario",
                        fontSize = 17.sp,
                        fontWeight = if (unreadCount > 0) FontWeight.Bold else FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )

                    // Badge con el rol
                    if (userInfo?.role?.isNotEmpty() == true) {
                        Text(
                            text = userInfo.role,
                            fontSize = 10.sp,
                            color = Color(0xFF9B4DFF),
                            modifier = Modifier
                                .background(
                                    Color(0xFF9B4DFF).copy(alpha = 0.2f),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                // Indicador "escribiendo..." o último mensaje
                if (chat.isTyping[otherUserId] == true) {
                    Text(
                        text = "escribiendo...",
                        color = Color(0xFF9B4DFF),
                        fontSize = 14.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                } else {
                    Text(
                        chat.lastMessage.ifEmpty { "Sin mensajes" },
                        color = if (unreadCount > 0) Color.White else Color.Gray,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = if (unreadCount > 0) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }

            // Hora y badge de no leídos
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(50.dp)
            ) {
                Text(
                    formatTime(chat.lastMessageTime),
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.weight(1f))

                if (unreadCount > 0) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF9B4DFF),
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                if (unreadCount > 99) "99+" else "$unreadCount",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

// ----------- FUNCIÓN PARA CARGAR INFO DE USUARIOS EN VIVO -----------
fun loadUsersInfoRealtime(userIds: List<String>, onResult: (Map<String, UserChatInfo>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val collections = listOf("Artista", "Bandas", "Curador", "Establecimiento", "Fan")

    var currentMap = mutableMapOf<String, UserChatInfo>()

    userIds.forEach { userId ->
        collections.forEach { collection ->
            db.collection(collection)
                .document(userId)
                .addSnapshotListener { doc, _ ->
                    if (doc != null && doc.exists()) {
                        val data = UserChatInfo(
                            name = doc.getString("nombreReal")
                                ?: doc.getString("nombreArtistico")
                                ?: doc.getString("name")
                                ?: "Usuario",

                            role = collection,

                            profileImageUrl = doc.getString("profileImageUrl")
                                ?: doc.getString("imagenPerfil")
                                ?: doc.getString("fotoPerfil")
                                ?: "",

                            isOnline = doc.getBoolean("isOnline") ?: false
                        )

                        currentMap[userId] = data
                        onResult(currentMap)  // <-- ACTUALIZA SIN BORRAR
                    }
                }
        }
    }
}


// ----------- FUNCIÓN PARA ELIMINAR CHAT (CORREGIDA) -----------
suspend fun deleteChat(chatId: String) {
    val db = FirebaseFirestore.getInstance()

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
    db.collection("Chats").document(chatId).delete().await()
}

// ----------- FORMATO DE HORA -----------
fun formatTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    return when {
        diff < 60000 -> "Ahora"
        diff < 3600000 -> "${diff / 60000}m"
        diff < 86400000 -> SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
        else -> SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date(timestamp))
    }
}
