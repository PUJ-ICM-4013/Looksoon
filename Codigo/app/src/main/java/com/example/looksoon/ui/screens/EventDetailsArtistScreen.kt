package com.example.looksoon.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.faunafinder.navigation.Screen
import com.example.looksoon.R
import com.example.looksoon.ui.theme.LooksoonTheme


@Composable
fun EventDetailsArtistScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedTab = Screen.Convocatorias.route,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        },




    ) {innerPadding->
        Column(modifier = Modifier.padding(innerPadding)){
            HeaderArtist(
                section = "Detalles",
                iconLeft = Icons.Default.Menu,
                iconRight = Icons.Default.Notifications,
                contentDescriptionLeft = "Menú",
                contentDescriptionRight = "Notificaciones",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(MaterialTheme.colorScheme.primary, Color.Black)
                        )
                    )
            );

            LazyColumn(modifier = Modifier.padding(16.dp)
            ){
                item{
                    EventInformationCard()
                }
                item{
                    RequirementsSection(listOf(
                        RequirementItem(Icons.Default.MusicNote, "Set de 45–60 min", "Se valora versatilidad", "Obligatorio"),
                        RequirementItem(Icons.Default.PhotoCamera, "Material promo", "Foto y bio corta", "Obligatorio"),
                        RequirementItem(Icons.Default.Link, "Enlaces", "Spotify / SoundCloud", "Opcional"),))
                    RequirementItem(Icons.Default.MusicNote, "Set de 45–60 min", "Se valora versatilidad", "Obligatorio")

                }
                item{
                    OrganizerSection(
                        name = "Terraza Nocturna",
                        category = "Bar",
                        location = "Chapinero",
                        imageRes = R.drawable.logo_looksoon // Usando drawable
                    )

                }



            }

        }


    }
}

@Preview
@Composable
fun EventDetailsArtistScreenPreview() {
    LooksoonTheme {
        EventDetailsArtistScreen(navController = rememberNavController())
    }
}

@Composable
fun EventInformationCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Black, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        // Imagen principal
        EventImage(
            imageRes = R.drawable.logo_looksoon, // Usando drawable
            tags = listOf("DJ Set", "Terraza Nocturna", "2.0 km")
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Título y ubicación
        EventHeader(
            title = "DJ Set - Terraza Nocturna",
            location = "Cra. 7 #45-12, Bogotá",
            untilDate = "Hasta 05 Jun"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Info del evento
        EventInfoRow(label = "Tipo", value = "Convocatoria pagada")
        EventInfoRow(label = "Género", value = "Electrónica / House")
        EventInfoRow(label = "Honorarios", value = "$600.000 COP")
        EventInfoRow(label = "Fecha tentativa", value = "Sáb 21:30")
        EventInfoRow(label = "Cierre de aplicaciones", value = "05 Jun, 23:59")
    }
}

@Composable
fun EventImage(imageRes: Int, tags: List<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tags.forEach { tag ->
                TagChip(text = tag)
            }
        }
    }
}

@Composable
fun TagChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFF4A0D68), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
fun EventHeader(title: String, location: String, untilDate: String) {
    Column {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$location • $untilDate",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun EventInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}

//Todos los preview
@Preview
@Composable
fun EventInformationCardPreview() {
    LooksoonTheme {
        EventInformationCard()
    }
}

@Preview
@Composable
fun EventImagePreview() {
    LooksoonTheme {
        EventImage(
            imageRes = R.drawable.logo_looksoon, // Usando drawable
            tags = listOf("DJ Set", "Terraza Nocturna", "2.0 km", "2.0 km"))
    }
}

// =====================
// Sección de Requisitos
// =====================
@Composable
fun RequirementsSection(requirements: List<RequirementItem>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text(
            text = "Requisitos",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        requirements.forEach { item ->
            RequirementCard(item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RequirementCard(item: RequirementItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono en círculo morado
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFF4A0D68), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Text(
                text = item.subtitle,
                color = Color.Gray,
                fontSize = 13.sp
            )
        }

        Text(
            text = item.requirementType,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

// Modelo de datos para los requisitos
data class RequirementItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val requirementType: String // Obligatorio / Opcional
)


// =====================
// Sección de Organizador
// =====================
@Composable
fun OrganizerSection(
    name: String,
    category: String,
    location: String,
    imageRes: Int // Cambió de imageUrl a imageRes
) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(
            text = "Organiza",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del organizador usando drawable
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = "$category • $location",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            // Botón de Ver mapa
            Box(
                modifier = Modifier
                    .background(Color(0xFF4A0D68), RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Ver mapa",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

// =====================
// PREVIEWS REQUISITOS
// =====================
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewRequirementCard() {
    RequirementCard(
        item = RequirementItem(
            icon = Icons.Default.MusicNote,
            title = "Set de 45–60 min",
            subtitle = "Se valora versatilidad",
            requirementType = "Obligatorio"
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewRequirementsSection() {
    val requirements = listOf(
        RequirementItem(Icons.Default.MusicNote, "Set de 45–60 min", "Se valora versatilidad", "Obligatorio"),
        RequirementItem(Icons.Default.PhotoCamera, "Material promo", "Foto y bio corta", "Obligatorio"),
        RequirementItem(Icons.Default.Link, "Enlaces", "Spotify / SoundCloud", "Opcional")
    )
    RequirementsSection(requirements = requirements)
}


// =====================
// PREVIEWS ORGANIZADOR
// =====================
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewOrganizerCard() {
    OrganizerSection(
        name = "Terraza Nocturna",
        category = "Bar",
        location = "Chapinero",
        imageRes = R.drawable.logo_looksoon // Usando drawable
    )
}


// =====================
// PREVIEW COMPLETO
// =====================
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewFullEventRequirementsAndOrganizer() {
    Column(modifier = Modifier.background(Color.Black)) {
        val requirements = listOf(
            RequirementItem(Icons.Default.MusicNote, "Set de 45–60 min", "Se valora versatilidad", "Obligatorio"),
            RequirementItem(Icons.Default.PhotoCamera, "Material promo", "Foto y bio corta", "Obligatorio"),
            RequirementItem(Icons.Default.Link, "Enlaces", "Spotify / SoundCloud", "Opcional")
        )

        RequirementsSection(requirements = requirements)

        OrganizerSection(
            name = "Terraza Nocturna",
            category = "Bar",
            location = "Chapinero",
            imageRes = R.drawable.logo_looksoon // Usando drawable
        )
    }
}