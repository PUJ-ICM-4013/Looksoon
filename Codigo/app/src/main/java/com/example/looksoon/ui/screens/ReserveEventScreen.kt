package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.R
import com.example.looksoon.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReserveEventScreen(
    onBackClick: () -> Unit = {},
    onReserveClick: (Int) -> Unit = {}
) {
    var ticketCount by remember { mutableStateOf(1) }

    Scaffold(
        containerColor = Background,
        bottomBar = {
            FanBottomNavBar(
                selectedTab = "Inicio",
                onTabSelected = { /* Navegación inferior */ }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background)
        ) {
            // Header
            HeaderFan(
                section = "Reservar Evento",
                iconLeft = Icons.Default.ArrowBack,
                iconRight = Icons.Default.ArrowBack, // Placeholder (no acción derecha aquí)
                contentDescriptionLeft = "Volver",
                contentDescriptionRight = "",
                onIconLeftClick = onBackClick,
                onIconRightClick = {},
                modifier = Modifier.padding(12.dp)
            )

            // Imagen del evento
            Image(
                painter = painterResource(id = R.drawable.event1),
                contentDescription = "Festival de Música Urbana",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Info del evento
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Festival de Música Urbana",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "15 Sep 2025 • Bogotá, Colombia",
                    color = TextSecondary,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Cantidad de entradas",
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Selector de entradas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = { if (ticketCount > 1) ticketCount-- },
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleSecondary),
                            shape = RoundedCornerShape(50)
                        ) { Text("-", fontSize = 18.sp, color = Color.White) }

                        Text(
                            text = "$ticketCount",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = { ticketCount++ },
                            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                            shape = RoundedCornerShape(50)
                        ) { Text("+", fontSize = 18.sp, color = Color.White) }
                    }

                    Text(
                        text = "${ticketCount * 50} USD", // Ejemplo precio
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Botón reservar
                Button(
                    onClick = { onReserveClick(ticketCount) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(PurplePrimary, PurpleSecondary)
                                ),
                                RoundedCornerShape(12.dp)
                            )
                            .fillMaxWidth()
                            .height(52.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Reservar",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReserveEventScreen() {
    LooksoonTheme {
        ReserveEventScreen()
    }
}
