package com.example.looksoon.ui.screens.artist.mainscreenartist

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.looksoon.R
import com.example.looksoon.utils.getSmartToolsForRole
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MainScreenArtist(
    onTabSelected: (String) -> Unit,
    seeMoreClick: () -> Unit,
    viewModel: MainScreenArtistViewModel,
    role: String = "Artista",
    onSmartToolSelected: (String) -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedTab = "Inicio",
                onTabSelected = onTabSelected
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            HeaderArtist(
                section = "Descubre Eventos",
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
                    ),
                role = role,
                onSmartToolSelected = onSmartToolSelected
            )
            InteractiveMap(viewModel = viewModel)
            FiltersRow(
                buttons = listOf(
                    { GenreChip("Rock", onClick = {}) },
                    { GenreChip("Pop", onClick = {}) },
                    { GenreChip("Jazz", onClick = {}) },
                    { GenreChip("Alternativa", onClick = {}) },
                    { GenreChip("Balada", onClick = {}) }
                )
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    EventCard(
                        title = "Concierto de Jazz",
                        date = "2022-01-01",
                        location = "New York",
                        imagePainter = painterResource(id = R.drawable.jazz),
                        onSeeMoreClick = {}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun InteractiveMap(
    viewModel: MainScreenArtistViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    DisposableEffect(Unit) {
        viewModel.setupLocationClient(context)
        onDispose { viewModel.stopLocationUpdates() }
    }
    LaunchedEffect(locationPermission.status.isGranted) {
        if (locationPermission.status.isGranted) {
            viewModel.startLocationUpdates(true, context)
        } else {
            locationPermission.launchPermissionRequest()
        }
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.userLocation ?: LatLng(4.648, -74.247), 12f)
    }
    val userLocation = state.userLocation
    LaunchedEffect(userLocation, state.followUser) {
        if (state.followUser && userLocation != null) {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
        }
    }

    Box(
        modifier = modifier.fillMaxWidth().height(400.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = locationPermission.status.isGranted),
            uiSettings = MapUiSettings(zoomControlsEnabled = true),
            onMapLongClick = { latLng -> viewModel.calculateRoute(latLng) }
        ) {
            // Dibuja la ruta si existe
            if (state.routePoints.isNotEmpty()) {
                Polyline(
                    points = state.routePoints,
                    color = Color(0xFFB84DFF),
                    width = 15f
                )
            }
        }

        // Botones y Snackbar
        Button(
            onClick = { viewModel.toggleFollowUser(!state.followUser) },
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (state.followUser) Color(0xFFB84DFF) else Color.Gray)
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = "Seguir Ubicación")
        }

        if (state.routePoints.isNotEmpty()) {
            Button(
                onClick = { viewModel.clearRoute() },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Limpiar Ruta")
            }
        }

        val errorMessage = state.errorMessage
        if (errorMessage != null) {
            Snackbar(
                modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
                action = { TextButton(onClick = { viewModel.clearErrorMessage() }) { Text("OK") } }
            ) {
                Text(text = errorMessage)
            }
        }
    }
}

// --- PREVIEW CORREGIDA Y CLASE FALSA ---

@Preview
@Composable
fun MainScreenArtistPreview() {
    com.example.looksoon.ui.theme.LooksoonTheme {
        MainScreenArtist(
            onTabSelected = {},
            seeMoreClick = {},
            // Usamos la clase falsa para que la Preview no falle
            viewModel = FakeMainScreenArtistViewModel(),
            role = "Artista",
            onSmartToolSelected = {}
        )
    }
}

/**
 * Implementación FALSA del ViewModel que solo se usa para que las
 * @Previews de Jetpack Compose puedan funcionar sin crashear.
 * No contiene lógica real.
 */
class FakeMainScreenArtistViewModel : MainScreenArtistViewModel() {
    // No necesita contenido, solo heredar del ViewModel real.
}

// --- OTROS COMPOSABLES (SIN CAMBIOS) ---

@Composable
fun HeaderArtist(
    section: String,
    modifier: Modifier = Modifier,
    iconLeft: ImageVector,
    contentDescriptionLeft: String,
    contentDescriptionRight: String,
    iconRight: ImageVector,
    role: String = "Artista",
    onSmartToolSelected: (String) -> Unit = {},
    onIconRightClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val smartTools = getSmartToolsForRole(role)

    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = iconLeft,
                    contentDescription = contentDescriptionLeft,
                    tint = Color.White
                )
            }
            Text(
                text = section,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(onClick = onIconRightClick) {
                Icon(
                    imageVector = iconRight,
                    contentDescription = contentDescriptionRight,
                    tint = Color.White
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color(0xFF1C1C1C))
                .padding(vertical = 4.dp)
        ) {
            smartTools.forEach { tool ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = tool.name,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = tool.icon,
                            contentDescription = tool.name,
                            tint = Color(0xFFB84DFF)
                        )
                    },
                    onClick = {
                        expanded = false
                        onSmartToolSelected(tool.route)
                    }
                )
            }
        }
    }
}

@Composable
fun GenreChip(text: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                )
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FilterChip(text: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF1E1E1E))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text, tint = Color.White, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FiltersRow(
    modifier: Modifier = Modifier,
    buttons: List<@Composable () -> Unit>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip("Distancia", icon = Icons.Default.LocationOn)
        buttons.forEach { button ->
            button()
        }
    }
}

@Composable
fun EventCard(title: String, date: String, location: String, imagePainter: Painter, onSeeMoreClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF2C2C2C))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = title,
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f).padding(start = 12.dp)
                ) {
                    Text(date, color = Color.LightGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(location, color = Color.LightGray, fontSize = 14.sp)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Brush.horizontalGradient(listOf(Color(0xFFB84DFF), Color(0xFF5A0FC8))))
                        .clickable { onSeeMoreClick() }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ver más", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf("Inicio", "Convocatorias", "Publicar", "Feed","Mensajes", "Perfil")
    val icons = listOf(Icons.Default.Home, Icons.Default.DateRange, Icons.Default.Add, Icons.Default.List ,Icons.Default.Email, Icons.Default.AccountCircle)

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
                label = {
                    Text(item, color = if (isSelected) Color(0xFFB84DFF) else Color.LightGray)
                }
            )
        }
    }
}
