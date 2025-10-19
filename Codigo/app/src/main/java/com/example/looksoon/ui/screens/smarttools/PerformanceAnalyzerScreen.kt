package com.example.looksoon.ui.screens.smarttools

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist

import com.example.looksoon.ui.theme.*

@Composable
fun PerformanceAnalyzerScreen(
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
                section = "Performance Analyzer",
                iconLeft = Icons.Default.Analytics,
                iconRight = Icons.Default.Notifications,
                contentDescriptionLeft = "Menú",
                contentDescriptionRight = "Notificaciones",
                onSmartToolSelected = onSmartToolSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Analiza tu rendimiento en presentaciones anteriores y recibe sugerencias de mejora.",
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(12.dp),
                color = Surface
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("Gráficas de energía, asistencia y ritmo (próximamente)", color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Estadísticas recientes", color = TextPrimary, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            val stats = listOf(
                "Asistencia promedio" to "82%",
                "Duración promedio" to "1h 45min",
                "Nivel de energía" to "Alto"
            )

            stats.forEach { (label, value) ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Surface
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(label, color = TextPrimary)
                        Text(value, color = PurplePrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
