package com.example.looksoon.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.looksoon.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalActionsScreen(
    navController: NavController? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Acciones",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        },
        containerColor = Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Elige una acción para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            ActionButton(
                text = "Reservar artista",
                icon = Icons.Default.CalendarToday,
                onClick = { navController?.navigate("reserve_artist") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ActionButton(
                text = "Publicar evento",
                icon = Icons.Default.Campaign,
                onClick = { navController?.navigate("publish_event") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ActionButton(
                text = "Gestionar postulaciones",
                icon = Icons.Default.People,
                onClick = { navController?.navigate("manage_applications") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ActionButton(
                text = "Visualizar reserva",
                icon = Icons.Default.Visibility,
                onClick = { navController?.navigate("reservation_detail") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Acciones rápidas del local",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = PurplePrimary,
            contentColor = TextPrimary
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape),
            tint = TextPrimary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLocalActionsScreen() {
    LooksoonTheme {
        LocalActionsScreen()
    }
}
