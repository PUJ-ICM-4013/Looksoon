package com.example.looksoon.ui.screens.curator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist
import com.example.looksoon.ui.screens.fan.HorizontalDivider
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

    Scaffold(
        bottomBar = {
            CuratorBottomNavBar(
                selectedTab = "Inicio",
                onTabSelected = { /* manejar navegación */ }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Sticky header usando HeaderArtist
            item {
                HeaderArtist(
                    section = "Curador",
                    iconLeft = Icons.Default.ArrowBack,
                    iconRight = Icons.Default.Search,
                    contentDescriptionLeft = "Volver",
                    contentDescriptionRight = "Buscar",
                    onIconLeftClick = onBackClick,
                    onIconRightClick = { /* acción buscar */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(MaterialTheme.colorScheme.primary, Color.Black)
                            )
                        )
                        .padding(16.dp)
                )
            }

            // Barra de búsqueda
            item {
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
            }

            // Filtros
            item {
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
            }

            // Lista de locales
            items(
                listOf(
                    Venue("Espacio Prisma", "Ciudad Centro, Chile", "Cap. 150", "3 eventos activos", 82),
                    Venue("Teatro Aurora", "Barrio Norte", "Cap. 300", "1 evento activo", 20),
                    Venue("Club Subsuelo", "Distrito Este", "Cap. 220", "2 eventos activos", 0)
                )
            ) { venue ->
                VenueCard(
                    name = venue.name,
                    location = venue.location,
                    capacity = venue.capacity,
                    activeEvents = venue.activeEvents,
                    progress = venue.progress,
                    onViewWorks = { onVenueClick(venue.name) }
                )
            }

            // Mis eventos
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Divider, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

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
            }
        }
    }
}

data class Venue(
    val name: String,
    val location: String,
    val capacity: String,
    val activeEvents: String,
    val progress: Int
)

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
                progress = progress / 100f,
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
fun CuratorBottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf("Inicio", "Favoritos", "Perfil")
    val icons = listOf(Icons.Default.Home, Icons.Default.Favorite, Icons.Default.AccountCircle)

    NavigationBar(containerColor = Color.Black) {
        items.forEachIndexed { index, item ->
            val isSelected = item == selectedTab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item) },
                icon = {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(Color(0xFFB84DFF), Color(0xFF5A0FC8))
                                    )
                                )
                                .padding(8.dp)
                        ) {
                            Icon(icons[index], contentDescription = item, tint = Color.White)
                        }
                    } else {
                        Icon(icons[index], contentDescription = item, tint = Color.LightGray)
                    }
                },
                label = { Text(item, color = if (isSelected) Color(0xFFB84DFF) else Color.LightGray) }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CuratorScreenPreview() {
    CuratorScreen()
}
