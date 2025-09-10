package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.R
import com.example.looksoon.ui.theme.*

// ---------------- PANTALLA ----------------
@Composable
fun EventDetailsScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomBarEventDetails(total = "$—")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)

                .verticalScroll(rememberScrollState())
                .background(Background)
        ) {
            pageHeader(
                section = "Detalles del evento",
                onBackClick = {}
            )

            Text(
                "Completa la información para solicitar la reserva.",
                color = TextSecondary,
                modifier = Modifier.padding(16.dp),
                fontSize = 14.sp
            )

            ArtistSummaryCard()

            Spacer(Modifier.height(16.dp))

            InputFieldEDS(
                label = "Nombre del evento",
                placeholder = "Ej. Boda Ana & Carlos",
                icon = Icons.Default.ConfirmationNumber,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InputFieldEDS(
                    label = "Fecha",
                    placeholder = "DD/MM/AAAA",
                    icon = Icons.Default.CalendarToday,
                    modifier = Modifier
                        .fillMaxWidth(0.92f)

                )
                InputFieldEDS(
                    label = "Hora",
                    placeholder = "20:00",
                    icon = Icons.Default.AccessTime,
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                )
            }

            InputFieldEDS(
                label = "Ubicación",
                placeholder = "Dirección o lugar",
                icon = Icons.Default.Place,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .align(Alignment.CenterHorizontally)
            )

            InputFieldEDS(
                label = "Tipo de evento",
                placeholder = "Fiesta privada, corporativo, boda...",
                icon = Icons.Default.MusicNote,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .align(Alignment.CenterHorizontally)
            )

            InputFieldEDS(
                label = "Notas para el artista",
                placeholder = "Repertorio, duración, requerimientos técnicos...",
                icon = Icons.Default.Edit,
                singleLine = false,
                height = 100.dp,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

//---------------- HEADER ----------------
@Composable
fun pageHeader(section: String, onBackClick: () -> Unit) {
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

// ---------------- INPUT FIELD ----------------
@Composable
fun InputFieldEDS(
    label: String,
    placeholder: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    height: Dp = 56.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            leadingIcon = { Icon(icon, contentDescription = null, tint = PurplePrimary) },
            label = {
                Text(
                    label,
                    color = TextSecondary,
                    fontSize = 12.sp // 🔑 Label más pequeño
                )
            },
            placeholder = {
                Text(
                    placeholder,
                    color = TextDisabled,
                    fontSize = 12.sp // 🔑 Placeholder más pequeño
                )
            },
            singleLine = singleLine,
            modifier = modifier.height(height), // ⬅️ ya no forzamos el ancho aquí
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PurplePrimary,
                unfocusedBorderColor = Divider,
                focusedContainerColor = Background,
                unfocusedContainerColor = Background,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = PurplePrimary
            )
        )
    }
}

// ---------------- CARD DE ARTISTA ----------------
@Composable
fun ArtistSummaryCard() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.jazz),
                    contentDescription = "Artista seleccionado",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Artista seleccionado", color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("Nombre, género y tarifa base", color = TextSecondary, fontSize = 13.sp)
                }
                Text("$—", color = TextPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ---------------- BOTTOM BAR ----------------
@Composable
fun BottomBarEventDetails(total: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Total: $total", color = TextSecondary)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(
                    Brush.horizontalGradient(listOf(PurpleSecondary, PurplePrimary))
                )
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Enviar solicitud", color = TextPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

// ---------------- PREVIEW ----------------
@Preview(showBackground = true)
@Composable
fun EventDetailsScreenPreview() {
    LooksoonTheme {
        EventDetailsScreen(navController = rememberNavController())
    }
}
