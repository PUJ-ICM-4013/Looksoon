package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.R
import com.example.looksoon.ui.theme.*

// Data class para un comentario
data class Comment(
    val userAvatar: Int,
    val username: String,
    val text: String,
    val timestamp: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCommentsScreen(navController: NavController) {
    // Datos de ejemplo
    val comments = listOf(
        Comment(R.drawable.foto, "Rastro", "¡Qué gran foto!", "Hace 5 min"),
        Comment(R.drawable.foto, "Neón", "¡Increíble energía en ese show!", "Hace 10 min"),
        Comment(R.drawable.foto, "Venus", "Necesitamos más noches como esta.", "Hace 1 hora")
    )
    var newCommentText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = { Text("Comentarios", color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        },
        bottomBar = {
            // Envuelve la barra de entrada en una Columna para poder añadir padding
            Column(
                modifier = Modifier.padding(bottom = 8.dp) // <-- ¡ESTE ES EL ÚNICO CAMBIO!
            ) {
                CommentInputBar(
                    value = newCommentText,
                    onValueChange = { newCommentText = it },
                    onSendClick = {
                        // Lógica para enviar comentario
                        newCommentText = ""
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(comments) { comment ->
                CommentItem(comment = comment)
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Row(verticalAlignment = Alignment.Top) {
        Image(
            painter = painterResource(id = comment.userAvatar),
            contentDescription = "Avatar de ${comment.username}",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(comment.username, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(comment.timestamp, color = TextSecondary, fontSize = 12.sp)
            }
            Text(comment.text, color = TextPrimary, fontSize = 14.sp)
        }
    }
}

@Composable
fun CommentInputBar(value: String, onValueChange: (String) -> Unit, onSendClick: () -> Unit) {
    Surface(color = Surface, tonalElevation = 4.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Añade un comentario...", color = TextSecondary) },
                modifier = Modifier.weight(1f),
                colors = textFieldColors() // Usando tu Composable reutilizable de PublishEventScreen
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onSendClick, enabled = value.isNotBlank()) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Enviar Comentario",
                    tint = if (value.isNotBlank()) PurplePrimary else TextDisabled
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostCommentsScreenPreview() {
    LooksoonTheme {
        PostCommentsScreen(rememberNavController())
    }

}