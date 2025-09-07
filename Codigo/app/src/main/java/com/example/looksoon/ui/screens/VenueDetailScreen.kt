package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Composable
fun VenueDetailScreenCreative(
    venueName: String = "Espacio Prisma",
    onBackClick: () -> Unit = {}
) {
    val isAvailable = remember { mutableStateOf(true) }
    val maxEvents = remember { mutableStateOf(3) }
    val rating = remember { mutableStateOf(4.5f) }
    val selectedTab = remember { mutableStateOf("Inicio") }

    // Definir colores para tema oscuro
    val DarkBackground = Color(0xFF121212)
    val DarkSurface = Color(0xFF1E1E1E)
    val DarkTextPrimary = Color(0xFFFFFFFF)
    val DarkTextSecondary = Color(0xFFB0B0B0)
    val DarkTextDisabled = Color(0xFF626262)

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            CuratorBottomNavBarP(
                selectedTab = selectedTab.value,
                onTabSelected = { selectedTab.value = it },
                darkMode = true
            )
        }
    ) { innerPadding ->
        // Usamos Box en lugar de Column para mejor control del layout
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Imagen de fondo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mapa),
                        contentDescription = "Mapa de ubicación",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.4f)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        DarkBackground.copy(alpha = 0.7f),
                                        DarkBackground
                                    )
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                                tint = DarkTextPrimary
                            )
                        }
                    }

                    Text(
                        text = "Detalles del Local",
                        color = DarkTextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.background(
                            Main.copy(alpha = 0.7f),
                            RoundedCornerShape(8.dp)
                        ).padding(horizontal = 12.dp, vertical = 6.dp)
                    )

                    Spacer(modifier = Modifier.size(44.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tarjeta principal
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = venueName,
                            color = DarkTextPrimary,
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
                                color = DarkTextSecondary,
                                fontSize = 14.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(5) { index ->
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Estrella",
                                    tint = if (index < rating.value.toInt()) PurplePrimary else DarkTextDisabled,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "%.1f".format(rating.value),
                                color = DarkTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Contenedores circulares
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CircularInfoBox(
                            "150",
                            "Capacidad",
                            PurplePrimary,
                            darkMode = true
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        CircularInfoBox(
                            maxEvents.value.toString(),
                            "Máx. Eventos",
                            PurplePrimary,
                            darkMode = true
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        CircularInfoBox(
                            if (isAvailable.value) "Sí" else "No",
                            "Disponible",
                            if (isAvailable.value) PurplePrimary else DarkTextDisabled,
                            isAvailable.value,
                            darkMode = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Descripción
                Surface(
                    color = DarkSurface,
                    shape = RoundedCornerShape(40.dp, 16.dp, 16.dp, 40.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .shadow(8.dp, RoundedCornerShape(40.dp, 16.dp, 16.dp, 40.dp))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Descripción",
                            color = PurplePrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Text(
                            text = "Espacio cultural multifuncional con excelente acústica, ideal para conciertos acústicos y presentaciones íntimas. Cuenta con equipo de sonido profesional y iluminación básica.",
                            color = DarkTextPrimary,
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

                        Column {
                            FeatureItem("Equipo de sonido profesional", darkMode = true)
                            FeatureItem("Iluminación básica incluida", darkMode = true)
                            FeatureItem("Ambiente climatizado", darkMode = true)
                            FeatureItem("Camerinos disponibles", darkMode = true)
                            FeatureItem("Acceso para personas con movilidad reducida", darkMode = true)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sección contacto
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(
                                RoundedCornerShape(16.dp, 60.dp, 16.dp, 60.dp)
                            )
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Main, Main.copy(alpha = 0.8f))
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Contacto",
                            color = DarkTextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Text(
                            text = "contacto@espacioprisma.cl",
                            color = DarkTextSecondary,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Text(
                            text = "+56 9 1234 5678",
                            color = DarkTextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }

                // Espacio final para asegurar que el contenido no queda oculto tras la barra
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}

@Composable
fun CircularInfoBox(value: String, label: String, color: Color, highlight: Boolean = true, darkMode: Boolean = false) {
    val surfaceColor = if (darkMode) {
        if (highlight) color.copy(alpha = 0.2f) else Color(0xFF1E1E1E)
    } else {
        if (highlight) color.copy(alpha = 0.2f) else Surface
    }

    val textColor = if (darkMode) {
        if (highlight) color else Color(0xFF626262)
    } else {
        if (highlight) color else TextDisabled
    }

    val labelColor = if (darkMode) Color(0xFFB0B0B0) else TextSecondary

    Surface(
        color = surfaceColor,
        shape = CircleShape,
        modifier = Modifier
            .aspectRatio(1f)
            .shadow(8.dp, CircleShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                color = labelColor,
                fontSize = 12.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun FeatureItem(text: String, darkMode: Boolean = false) {
    val textColor = if (darkMode) Color(0xFFFFFFFF) else TextPrimary

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
            color = textColor,
            fontSize = 14.sp
        )
    }
}

@Composable
fun CuratorBottomNavBarP(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    darkMode: Boolean = false
) {
    val items = listOf("Inicio", "Favoritos", "Perfil")
    val icons = listOf(Icons.Default.Home, Icons.Default.Favorite, Icons.Default.AccountCircle)

    val backgroundColor = if (darkMode) Color(0xFF000000) else Color(0xFF000000)
    val selectedTextColor = if (darkMode) PurplePrimary else PurplePrimary
    val unselectedTextColor = if (darkMode) Color(0xFFB0B0B0) else Color(0xFFB0B0B0)

    // Barra de navegación simplificada y mejorada
    NavigationBar(
        containerColor = backgroundColor,
        tonalElevation = 8.dp,
        modifier = Modifier.height(70.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = item == selectedTab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item) },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item,
                        tint = if (isSelected) PurplePrimary else unselectedTextColor,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item,
                        color = if (isSelected) selectedTextColor else unselectedTextColor,
                        fontSize = 12.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Eliminamos el indicador por defecto
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VenueDetailScreenCreativePreview() {
    VenueDetailScreenCreative()
}