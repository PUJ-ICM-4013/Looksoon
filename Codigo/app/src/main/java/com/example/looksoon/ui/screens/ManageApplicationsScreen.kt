package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.looksoon.R
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import com.example.looksoon.ui.theme.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class Application(
    val name: String,
    val genre: String,
    val price: String,
    val distance: String,
    val tags: List<String>,
    val message: String,
    val status: String? = null,
    val rating: String? = null,
    val imageRes: Int
)

@Composable
fun ManageApplicationsScreen(navController: NavController) {
    val applications = listOf(
        Application(
            "Luna Trío", "Jazz contemporáneo", "$120k", "2 km",
            listOf("Equipo propio", "Repertorio flexible"),
            "¡Hola! Disponibles esa fecha. Podemos adaptar un set de 2 horas.",
            rating = "4.8",
            imageRes = R.drawable.foto
        ),
        Application(
            "Saxo & Beat", "Nu Jazz", "$90k", "5 km",
            listOf("Amplificación", "Repertorio solicitado"),
            "Podemos llevar set acústico si prefieren volumen moderado.",
            status = "Nuevo",
            imageRes = R.drawable.foto
        ),
        Application(
            "Dúo Estelar", "Bossa & Jazz", "$110k", "3 km",
            listOf("Set 2x45", "Equipo propio"),
            "¡Nos encanta el lugar! Podemos incluir clásicos de Jobim.",
            status = "Preseleccionado",
            imageRes = R.drawable.foto
        )
    )

    Scaffold(
        containerColor = Background,
        bottomBar = { BottomBarConfirmations(selectedCount = 3) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Background)
        ) {
            HeaderReserve("Postulaciones") { /* Acción atrás */ }

            Text(
                "Gestiona las solicitudes de artistas para tu evento",
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 12.dp),
                color = TextSecondary,
                fontSize = 14.sp
            )

            // Evento resumido
            EventSummary()

            // Tabs
            TabRow(
                selectedTabIndex = 0,
                containerColor = Background,
                contentColor = PurplePrimary
            ) {
                Tab(selected = true, onClick = { }) { Text("Todas", modifier = Modifier.padding(12.dp)) }
                Tab(selected = false, onClick = { }) { Text("Favoritas", modifier = Modifier.padding(12.dp)) }
                Tab(selected = false, onClick = { }) { Text("Seleccionadas", modifier = Modifier.padding(12.dp)) }
            }

            // Search bar
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Surface)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Buscar artista o género", color = TextSecondary)
                Text("Filtros", color = PurplePrimary)
            }

            // Lista de postulaciones
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(applications) { app ->
                    ApplicationCard(app)
                }
            }
        }
    }
}

@Composable
fun EventSummary() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Noche de Jazz en Looksoon", color = TextPrimary, fontWeight = FontWeight.Bold)
                Text("12/10 • 20:00 • Palermo", color = TextSecondary, fontSize = 13.sp)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Surface)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text("8 postulaciones", color = PurplePrimary, fontSize = 13.sp)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ApplicationCard(app: Application) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(app.imageRes),
                    contentDescription = app.name,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(app.name, color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("${app.genre} • ${app.price} • ${app.distance}", color = TextSecondary, fontSize = 13.sp)
                }
                app.rating?.let {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Surface)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(it, color = PurplePrimary, fontSize = 13.sp)
                    }
                }
                app.status?.let {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Surface)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(it, color = if (it == "Nuevo") TextPrimary else Color(0xFF4CAF50), fontSize = 13.sp)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                app.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Surface)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(tag, color = PurplePrimary, fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(app.message, color = TextPrimary, fontSize = 13.sp)

            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TextButton(onClick = { }) { Text("Favorito", color = PurplePrimary) }
                TextButton(onClick = { }) { Text("Descartar", color = TextSecondary) }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                    shape = RoundedCornerShape(50)
                ) { Text("Seleccionar") }
            }
        }
    }
}

@Composable
fun BottomBarConfirmations(selectedCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$selectedCount seleccionadas", color = TextSecondary, fontSize = 13.sp)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(
                    Brush.horizontalGradient(listOf(PurplePrimary, PurpleSecondary))
                )
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Enviar confirmaciones", color = TextPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HeaderReserve(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "< Atrás",
            color = PurplePrimary,
            modifier = Modifier.clickable { onBack() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicationsScreenPreview() {
    LooksoonTheme {
        ManageApplicationsScreen(navController = rememberNavController())
    }
}
