package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.R
import com.example.looksoon.ui.theme.PurplePrimary
import com.example.looksoon.ui.theme.PurpleSecondary
import com.example.looksoon.ui.theme.TextPrimary
import com.example.looksoon.ui.theme.TextSecondary
import com.example.looksoon.ui.theme.Background

// Modelo simple para eventos
data class Event(
    val id: Int,
    val title: String,
    val date: String,
    val location: String,
    val image: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreEventsScreen(navController: NavHostController) {
    val events = listOf(
        Event(1, "Festival de Música Urbana", "15 Sep 2025", "Bogotá, Colombia", R.drawable.jazz),
        Event(2, "Concierto de Rock Indie", "20 Sep 2025", "Medellín, Colombia", R.drawable.jazz),
        Event(3, "Electro Party", "25 Sep 2025", "Cali, Colombia", R.drawable.jazz),
    )

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = { Text("Explorar", color = TextPrimary) },
                actions = {
                    IconButton(onClick = { /* TODO: Notificaciones */ }) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notificaciones",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        bottomBar = {
            // 👇 Usamos la misma FanBottomNavBar de InvitarContactos
            FanBottomNavBar(
                selectedTab = "Inicio", // 👈 pestaña activa
                onTabSelected = { route ->
                    // TODO: Manejar navegación entre pantallas
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                .fillMaxSize()
        ) {
            items(events) { event ->
                EventCard(event = event, onClick = {
                    // TODO: Navegar a detalle o reserva del evento
                    // navController.navigate("event_detail/${event.id}")
                })
            }
        }
    }
}

@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            // Imagen del evento
            Image(
                painter = painterResource(id = event.image),
                contentDescription = event.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Info del evento
            Column(modifier = Modifier.padding(12.dp)) {
                Text(event.title, color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(event.date, color = TextSecondary, fontSize = 14.sp)
                Text(event.location, color = TextSecondary, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(listOf(PurplePrimary, PurpleSecondary)),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text("Ver más", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreEventsPreview() {
    val navController = rememberNavController()
    ExploreEventsScreen(navController)
}
