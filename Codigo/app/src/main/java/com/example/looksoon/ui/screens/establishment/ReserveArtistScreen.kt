package com.example.looksoon.ui.screens.establishment

// Imports para la lógica de la notificación
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import kotlinx.coroutines.delay

// Imports básicos y de UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Imports específicos de tu proyecto
import com.example.looksoon.R
import com.example.looksoon.ui.theme.LooksoonTheme

// <<< 1. IMPORTAMOS EL VIEWMODEL Y LA TARJETA DE NOTIFICACIÓN REUTILIZABLE
import com.example.looksoon.ui.screens.SharedViewModel
import com.example.looksoon.ui.screens.artist.CustomNotificationCard

// ---------------- DATOS ----------------
data class Artist(
    val name: String,
    val genres: String,
    val tags: List<String>,
    val description: String,
    val price: String
)

val artists = listOf(
    Artist(
        "Luna Vega",
        "Pop • Indie • Español",
        listOf("Voz en vivo", "Acústico", "Eventos privados"),
        "Cantante y compositora con estilo íntimo y contemporáneo. Ideal para eventos sociales y cocteles.",
        "Desde $250"
    ),
    Artist(
        "DJ Orion",
        "Electrónica • House • Tech",
        listOf("Set 2h", "Luces básicas", "Eventos nocturnos"),
        "Mezclas modernas y dinámicas para fiestas y clubes. Adaptable a la energía del público.",
        "Desde $400"
    ),
    Artist(
        "Trio Andino",
        "Folclore • Instrumental",
        listOf("Instrumental", "Tradicional", "Cultural"),
        "Música tradicional con cuerdas y vientos, perfecto para celebraciones culturales y recepciones.",
        "Desde $300"
    )
)

// ---------------- PANTALLA PRINCIPAL ----------------
// <<< 2. MODIFICAMOS LA FIRMA DE LA FUNCIÓN
@Composable
fun ReserveArtistScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    // <<< 3. AÑADIMOS LA LÓGICA DE LA NOTIFICACIÓN
    val notificationAlreadyShown by sharedViewModel.notificationShown.collectAsState()
    var showNotification by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!notificationAlreadyShown) {
            showNotification = true
            sharedViewModel.markNotificationAsShown()
            delay(8000L) // Espera 8 segundos
            showNotification = false
        }
    }

    // <<< 4. ENVOLVEMOS EL SCAFFOLD EN UN BOX
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomBarReserve() }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                HeReserve(
                    section = "Reservar Artista",
                    onBackClick = { navController.popBackStack() }
                )

                SearchBarReserve()

                FiltersRowReserve(
                    filters = listOf("Pop", "Rock", "DJ", "Acústico", "Indie")
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(artists) { artist ->
                        ArtistCardReserve(artist = artist, navController = navController)
                    }
                }
            }
        }

        // <<< 5. AÑADIMOS LA NOTIFICACIÓN ANIMADA SUPERPUESTA
        AnimatedVisibility(
            visible = showNotification,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 70.dp, start = 16.dp, end = 16.dp),
            exit = fadeOut()
        ) {
            // Reutilizamos la tarjeta, pero con el mensaje para el local
            CustomNotificationCard(
                message = "¡La ponti fiesta se acerca! No te quedes sin música, encuentra y reserva a los mejores artistas para tu evento.",
                onDismiss = { showNotification = false }
            )
        }
    }
}


// ---------------- HEADER ----------------
@Composable
fun HeReserve(section: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            tint = Color.White,
            contentDescription = "Atrás",
            modifier = Modifier
                .size(28.dp)
                .clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = section,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

// ---------------- BARRA DE BÚSQUEDA ----------------
@Composable
fun SearchBarReserve() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Buscar por nombre o género", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

// ---------------- FILTROS ----------------
@Composable
fun FiltersRowReserve(filters: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { label ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = label, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ---------------- TARJETA DE ARTISTA ----------------
@Composable
fun ArtistCardReserve(artist: Artist, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                navController.navigate("event_details")
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.jazz),
                    contentDescription = artist.name,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(artist.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                    Text(artist.genres, fontSize = 14.sp, color = Color.LightGray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                artist.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(tag, fontSize = 11.sp, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(artist.description, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(artist.price, fontSize = 15.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFB84DFF), Color(0xFF5A0FC8))
                            )
                        )
                        .clickable { navController.navigate("event_details") }
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Reservar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ---------------- BOTTOM BAR ----------------
@Composable
fun BottomBarReserve() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Black
    ) {
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Elige un artista para continuar", color = Color.Gray)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        Brush.horizontalGradient(listOf(Color(0xFFB84DFF), Color(0xFF5A0FC8)))
                    )
                    .clickable { }
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Continuar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ---------------- PREVIEW ----------------
@Preview(showBackground = true)
@Composable
fun ReserveArtistScreenPreview() {
    LooksoonTheme {
        // La Preview no necesita los parámetros, por lo que la dejamos como estaba.
        // La funcionalidad real se prueba en el emulador.
        ReserveArtistScreen(navController = rememberNavController(), sharedViewModel = viewModel())
    }
}
