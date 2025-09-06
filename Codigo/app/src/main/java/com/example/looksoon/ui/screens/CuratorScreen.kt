package com.example.looksoon.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.looksoon.ui.theme.*

@Composable
fun CuratorScreen(
    onBackClick: () -> Unit = {},
    onVenueClick: (String) -> Unit = {},
    onMyEventsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onVenuesClick: () -> Unit = {},
    onRateClick: () -> Unit = {}
) {
    val searchState = remember { mutableStateOf(TextFieldValue("")) }
    val capacityFilter = remember { mutableStateOf(true) }
    val activeEventsFilter = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        // Barra superior
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = TextPrimary
                )
            }

            Text(
                text = "Curador · Selecciona un local",
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        OutlinedTextField(
            value = searchState.value,
            onValueChange = { searchState.value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar locales, ciudades...", color = TextSecondary) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = TextSecondary
                )
            },
            textStyle = LocalTextStyle.current.copy(color = TextPrimary),
            colors = TextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = PurplePrimary,
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface,
                focusedIndicatorColor = PurplePrimary,
                unfocusedIndicatorColor = Divider
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterChip(
                selected = capacityFilter.value,
                onClick = { capacityFilter.value = !capacityFilter.value },
                label = { Text("Capacidad 100-300", fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
            )

            FilterChip(
                selected = activeEventsFilter.value,
                onClick = { activeEventsFilter.value = !activeEventsFilter.value },
                label = { Text("Con eventos activos", fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(color = Divider, thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de locales disponibles
        Text(
            text = "Locales disponibles",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                VenueCard(
                    name = "Espacio Prisma",
                    location = "Ciudad Centro, Chile",
                    capacity = "Cap. 150",
                    activeEvents = "3 eventos activos",
                    progress = 82,
                    onViewWorks = { onVenueClick("Espacio Prisma") }
                )
            }

            item {
                VenueCard(
                    name = "Teatro Aurora",
                    location = "Barrio Norte",
                    capacity = "Cap. 300",
                    activeEvents = "1 evento activo",
                    progress = 20,
                    onViewWorks = { onVenueClick("Teatro Aurora") }
                )
            }

            item {
                VenueCard(
                    name = "Club Subsuelo",
                    location = "Distrito Este",
                    capacity = "Cap. 220",
                    activeEvents = "2 eventos activos",
                    progress = 0,
                    onViewWorks = { onVenueClick("Club Subsuelo") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(color = Divider, thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // Mis eventos
        Text(
            text = "Mis eventos",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            color = Surface,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onMyEventsClick)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Continuar calificando",
                    color = TextPrimary,
                    fontSize = 14.sp
                )

                Surface(
                    color = PurplePrimary,
                    shape = CircleShape
                ) {
                    Text(
                        text = "2",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navegación inferior
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem("Perfil", selected = false, onClick = onProfileClick)
            BottomNavItem("Editar", selected = false, onClick = onEditClick)
            BottomNavItem("Locales", selected = true, onClick = onVenuesClick)
            BottomNavItem("Calificar", selected = false, onClick = onRateClick)
        }
    }
}

@Composable
fun VenueCard(
    name: String,
    location: String,
    capacity: String,
    activeEvents: String,
    progress: Int,
    onViewWorks: () -> Unit
) {
    Surface(
        color = Surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = location,
                color = TextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = capacity, color = TextSecondary, fontSize = 12.sp)
                Text(text = activeEvents, color = TextSecondary, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PurplePrimary,
                trackColor = Divider
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Progreso $progress%", color = TextSecondary, fontSize = 12.sp)
                Text(
                    text = "Ver obras",
                    color = PurplePrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(onClick = onViewWorks)
                )
            }
        }
    }
}

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (selected) PurplePrimary else Surface,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (selected) PurplePrimary else Divider,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            label()
        }
    }
}

@Composable
fun BottomNavItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            color = if (selected) PurplePrimary else TextSecondary,
            fontSize = 12.sp
        )

        if (selected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(PurplePrimary, CircleShape)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CuratorScreenPreview() {
    CuratorScreen()
}
