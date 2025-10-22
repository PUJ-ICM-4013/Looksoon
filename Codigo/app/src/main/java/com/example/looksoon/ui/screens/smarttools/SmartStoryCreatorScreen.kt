package com.example.looksoon.ui.screens.smarttools

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist

import com.example.looksoon.ui.theme.*

@Composable
fun SmartStoryCreatorScreen(
    onBack: () -> Unit,
    onSmartToolSelected: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            HeaderArtist(
                section = "Smart Story Creator",
                iconLeft = Icons.Default.Create,
                iconRight = Icons.Default.Notifications,
                contentDescriptionLeft = "Menú",
                contentDescriptionRight = "Notificaciones",
                onSmartToolSelected = onSmartToolSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Crea historias visuales atractivas para tus redes sociales y eventos.",
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                shape = RoundedCornerShape(12.dp),
                color = Surface
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("Sube fotos, agrega texto o música (versión futura)", color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* Crear historia */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
            ) {
                Text("Crear nueva historia", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Tus historias anteriores", color = TextPrimary, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            repeat(2) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Surface
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Historia ${it + 1}", color = TextPrimary, fontWeight = FontWeight.Bold)
                        Text("Publicado hace 2 días", color = TextSecondary)
                    }
                }
            }
        }
    }
}
