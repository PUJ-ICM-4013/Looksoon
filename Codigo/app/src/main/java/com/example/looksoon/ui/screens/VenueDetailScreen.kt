package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.looksoon.R
import com.example.looksoon.ui.theme.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.ExpandMore

@Composable
fun VenueDetailScreenCreative(
    venueName: String = "Espacio Prisma",
    onBackClick: () -> Unit = {}
) {
    val isAvailable = remember { mutableStateOf(true) }
    val maxEvents = remember { mutableStateOf(3) }
    val rating = remember { mutableStateOf(4.5f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Mapa de fondo más visible
        Image(
            painter = painterResource(id = R.drawable.mapa), // Reemplaza con tu recurso
            contentDescription = "Mapa de ubicación",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.4f) // Mayor visibilidad del mapa
        )

        // Capa de degradado más suave para no opacar demasiado el mapa
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Background.copy(alpha = 0.7f),
                            Background,
                            Background
                        ),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Encabezado con transparencia
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de retroceso con diseño circular
                Surface(
                    color = Main.copy(alpha = 0.8f),
                    shape = CircleShape,
                    modifier = Modifier
                        .size(44.dp)
                        .shadow(8.dp, CircleShape)
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextPrimary
                        )
                    }
                }

                Text(
                    text = "Detalles del Local",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.background(Main.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )

                // Espacio para equilibrar
                Spacer(modifier = Modifier.size(44.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta principal con forma irregular
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Forma de fondo irregular
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 32.dp,
                                topEnd = 32.dp,
                                bottomEnd = 80.dp,
                                bottomStart = 16.dp
                            )
                        )
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Main, PurplePrimary.copy(alpha = 0.7f))
                            )
                        )
                )

                // Contenido de la tarjeta principal
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = venueName,
                        color = TextPrimary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = PurpleSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Ciudad Centro, Chile",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }

                    // Rating con estrellas
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Estrella",
                                tint = if (index < rating.value.toInt()) PurplePrimary else TextDisabled,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "%.1f".format(rating.value),
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Contenedores de información con el MISMO tamaño
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Contenedor circular para capacidad - MISMO TAMAÑO
                Surface(
                    color = Surface,
                    shape = CircleShape,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .shadow(8.dp, CircleShape)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "150",
                            color = PurplePrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Capacidad",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Contenedor circular para eventos - MISMO TAMAÑO
                Surface(
                    color = Surface,
                    shape = CircleShape,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .shadow(8.dp, CircleShape)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = maxEvents.value.toString(),
                            color = PurplePrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Máx. Eventos",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Contenedor circular para disponibilidad - MISMO TAMAÑO
                Surface(
                    color = if (isAvailable.value) PurplePrimary.copy(alpha = 0.2f) else Surface,
                    shape = CircleShape,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .shadow(8.dp, CircleShape)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = if (isAvailable.value) "Sí" else "No",
                            color = if (isAvailable.value) PurplePrimary else TextDisabled,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Disponible",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección de descripción con forma irregular
            Surface(
                color = Surface,
                shape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 40.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(8.dp, RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 40.dp
                    ))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Descripción",
                        color = PurplePrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = "Espacio cultural multifuncional con excelente acústica, ideal para conciertos acústicos y presentaciones íntimas. Cuenta con equipo de sonido profesional y iluminación básica.",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Características:",
                        color = PurplePrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Lista de características
                    Column {
                        FeatureItem("Equipo de sonido profesional")
                        FeatureItem("Iluminación básica incluida")
                        FeatureItem("Ambiente climatizado")
                        FeatureItem("Camerinos disponibles")
                        FeatureItem("Acceso para personas con movilidad reducida")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de contacto con diseño de burbujas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Fondo con forma de burbuja
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 60.dp,
                                bottomEnd = 16.dp,
                                bottomStart = 60.dp
                            )
                        )
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Main, Main.copy(alpha = 0.8f))
                            )
                        )
                )

                // Contenido de contacto
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Contacto",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Text(
                        text = "contacto@espacioprisma.cl",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "+56 9 1234 5678",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp)) // Espacio final
        }
    }
}

@Composable
fun FeatureItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(PurplePrimary, CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = TextPrimary,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VenueDetailScreenCreativePreview() {
    VenueDetailScreenCreative()
}


