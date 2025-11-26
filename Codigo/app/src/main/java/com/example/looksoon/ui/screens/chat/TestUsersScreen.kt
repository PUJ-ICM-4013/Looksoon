package com.example.looksoon.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.looksoon.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

// Usuario de prueba
data class TestUser(
    val id: String,
    val name: String,
    val role: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestUsersScreen(
    navController: NavHostController
) {
    val chatRepository = ChatRepository()
    val coroutineScope = rememberCoroutineScope()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // Usuarios de prueba
    val testUsers = listOf(
        TestUser(
            id = "demo_user_1",
            name = "María González",
            role = "Artista",
            description = "Cantante de jazz y blues"
        ),
        TestUser(
            id = "demo_user_2",
            name = "Carlos Pérez",
            role = "Local",
            description = "Dueño de Café Cultural"
        ),
        TestUser(
            id = "demo_user_3",
            name = "Ana Martínez",
            role = "Curador",
            description = "Especialista en arte contemporáneo"
        ),
        TestUser(
            id = "demo_user_4",
            name = "Luis Ramírez",
            role = "Fan",
            description = "Amante de la música indie"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios de Prueba") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Mensaje informativo
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "💬 Toca un usuario para iniciar una conversación y probar el chat en tiempo real",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Lista de usuarios
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(testUsers) { user ->
                    TestUserCard(
                        user = user,
                        onClick = {
                            coroutineScope.launch {
                                // Crear o obtener el chat
                                val chatId = chatRepository.getOrCreateChat(
                                    otherUserId = user.id,
                                    otherUserName = user.name,
                                    otherUserRole = user.role
                                )

                                if (chatId.isNotEmpty()) {
                                    // NUEVA LÍNEA ⬇️
                                    navController.navigate("chat_conversation/$chatId/${user.id}/${user.name}")  // ✅ CORRECTO
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun TestUserCard(
    user: TestUser,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Info del usuario
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.role,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}