package com.example.looksoon.ui.screens

import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.theme.LooksoonTheme
import com.example.looksoon.ui.screens.BottomNavBar
import com.example.looksoon.ui.screens.HeaderArtist


data class Message(
    val text: String,
    val isUser: Boolean
)

// ---------------------------
// Burbuja de mensaje
// ---------------------------
@Composable
fun MessageBubble(message: Message) {
    val bubbleColor = if (message.isUser) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (message.isUser) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 2.dp
        ) {
            Text(
                text = message.text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

// ---------------------------
// Caja de entrada de texto
// ---------------------------
@Composable
fun MessageInput(
    onSend: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Escribe un mensaje...") },
            singleLine = true
        )
        Spacer(modifier = Modifier.width(8.dp))
        FilledIconButton(
            onClick = {
                if (text.isNotBlank()) {
                    onSend(text)
                    text = ""
                }
            }
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Enviar")
        }
    }
}

// ---------------------------
// Pantalla principal del chat
// ---------------------------
@Composable
fun ChatScreen(
    navController: NavHostController,
    contactName: String,
) {
    var messages by remember {
        mutableStateOf(
            listOf(
                Message("¡Hola! ¿Cómo estás?", false),
                Message("Muy bien, ¿y tú?", true),
                Message("Todo genial 🚀", false)
            )
        )
    }

    Scaffold(

        bottomBar = {
            Column {
                MessageInput { newMessage ->
                    messages = messages + Message(newMessage, true)
                }
                BottomNavBar(
                    selectedTab = "Mensajes",
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)){
            HeaderArtist(
                section = contactName,
                iconLeft = Icons.Default.ArrowBack,
                iconRight = Icons.Default.MoreVert,
                contentDescriptionLeft = "Atrás",
                contentDescriptionRight = "Opciones",
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp),
                onIconLeftClick = { navController.popBackStack() }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize() // ocupa toda la pantalla
                   , // respeta header y bottomBar
                contentPadding = PaddingValues(8.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(message)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    LooksoonTheme {
        ChatScreen(contactName = "Juan Pérez", navController = rememberNavController())
    }
}
