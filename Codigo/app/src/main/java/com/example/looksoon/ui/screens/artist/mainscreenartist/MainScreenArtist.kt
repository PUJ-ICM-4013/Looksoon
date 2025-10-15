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
import androidx.compose.runtime.Composable

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.R
import com.example.faunafinder.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState



//Composable para pantalla completa de MainScreenArtist
@Composable
fun MainScreenArtist(

    navController: NavHostController
) {

    //Scaffold para pantalla completa y que no pueda extenderse de los límites
    Scaffold(
        //Indicar que se tendrá abajo el Nav
        bottomBar = {
            BottomNavBar(
                selectedTab = "Inicio",

                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(Screen.Home.route)
                    }

                }
            )
        }
        //Usar padding necesario al contenido para que no se salga de la pantalla
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            //Llamar Composable de Header Artist
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
                    )
            );
            //Llamar Composable de Map

            InteractiveMap()
            //Llamar Composable de FiltersRow(Conjunto de botones)
            FiltersRow(
                buttons = listOf(
                    { GenreChip("Rock", onClick = {}) },
                    { GenreChip("Pop", onClick = {}) },
                    { GenreChip("Jazz", onClick = {}) },
                    { GenreChip("Alternativa", onClick = {}) },
                    { GenreChip("Balada", onClick = {}) }

                )
            )
            //LazyColumn para mostrar la lista de eventos(Falta hacerlo dinámico con datos)
            LazyColumn(
                    modifier = Modifier

                        .fillMaxSize()
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
                item {
                    EventCard(
                        title = "Concierto de Rock",
                        date = "2022-01-02",
                        location = "Los Angeles",
                        imagePainter = painterResource(id = R.drawable.jazz),

                        onSeeMoreClick = {}

                    )
                }
                item {
                    EventCard(
                        title = "Concierto de Rock",
                        date = "2022-01-02",
                        location = "Los Angeles",
                        imagePainter = painterResource(id = R.drawable.jazz),

                        onSeeMoreClick = {}

                    )
                }
            }

        }

    }
}
//Visualización de la pantalla
@Preview
@Composable
fun MainScreenArtistPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {

        MainScreenArtist(navController = rememberNavController())

    }
}

//Composable para el Header Artist
//Se envía info por parametro para que sea reutilizable
@Composable
fun HeaderArtist(
    section: String,
    modifier: Modifier = Modifier,
    iconLeft: ImageVector,
    contentDescriptionLeft: String,
    contentDescriptionRight: String,
    iconRight: ImageVector,
    onIconLeftClick: () -> Unit = {},
    onIconRightClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Icono izquierdo
            IconButton(onClick = onIconLeftClick) {
                Icon(iconLeft, contentDescription = contentDescriptionLeft, tint = Color.White)
            }
            //Sapcer para centrar(necesario tener dos, uno antes y otro despues del composable a centrar)
            Spacer(modifier = Modifier.weight(1f))
            //Texto del Header
            Text(
                text = section,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            //Spacer final de centrado de texto
            Spacer(modifier = Modifier.weight(1f))
            //Icono Gamification
            IconButton(onClick = {}){
                Icon(Icons.Default.Face, contentDescription = "Gamification", tint = Color.White)
            }
            //Icono derecho
            IconButton(onClick = onIconRightClick) {
                Icon(iconRight, contentDescription =contentDescriptionRight, tint = Color.White)
            }
        }
    }
}

//Composable para el Boton de Genero
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

//Boton para distancia
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
//Enviar lista de botones por parámetro
//Fila con todos los botones
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
        //Colocar el conjunto de funciones
        buttons.forEach { button ->
            button()
        }

    }
}

//Tarjetas de evento
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

//Mapa


// Borra tu Composable 'Map()' anterior y usa este
// Composable para el mapa interactivo con la lógica de 400.dp
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun InteractiveMap(
    viewModel: MainScreenArtistViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    // 1. Inicializar el cliente de ubicación
    DisposableEffect(Unit) {
        viewModel.setupLocationClient(context)
        onDispose {
            viewModel.stopLocationUpdates()
        }
    }

    // 2. Iniciar las actualizaciones de ubicación si el permiso está garantizado
    LaunchedEffect(locationPermission.status.isGranted) {
        if (locationPermission.status.isGranted) {
            viewModel.startLocationUpdates(true, context)
        } else {
            // Solicitar el permiso (si aún no se ha solicitado o fue negado anteriormente)
            locationPermission.launchPermissionRequest()
        }
    }

    // 3. Posición inicial de la cámara (por ejemplo, una ubicación central si no hay ubicación de usuario)
    val initialPos = LatLng(4.6486259, -74.2478962) // Ejemplo: Bogotá
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.userLocation ?: initialPos, 12f)
    }

    // 4. Mover la cámara cuando cambia la ubicación del usuario y 'followUser' es true
    LaunchedEffect(state.userLocation, state.followUser) {
        if (state.followUser && state.userLocation != null) {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(state.userLocation!!, 16f))
        }
    }

    // 5. El Composable del Mapa
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp) // <--- Altura de 400.dp solicitada
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = locationPermission.status.isGranted, // Muestra el punto azul nativo
                mapType = if (state.isDarkTheme) MapType.HYBRID else MapType.NORMAL // Lógica de modo oscuro si la implementas
            ),
            uiSettings = MapUiSettings(zoomControlsEnabled = true)
        ) {
            // Marcador de ubicación actual (Opcional, ya que 'isMyLocationEnabled' hace el punto azul)
            /*
            state.userLocation?.let {
                Marker(
                    state = rememberMarkerState(position = it),
                    title = "Tu ubicación actual"
                )
            }
            */

            // Dibujar marcadores de búsqueda
            state.markers.forEach { (pos, title) ->
                Marker(
                    state = rememberMarkerState(position = pos),
                    title = title
                )
            }
        }

        // Puedes añadir un botón para buscar, alternar 'followUser' y la luz, etc.,
        // como en el código de referencia, superpuesto sobre el mapa.

        // Botón para centrar en el usuario y activar/desactivar seguimiento
        Button(
            onClick = { viewModel.toggleFollowUser(!state.followUser) },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (state.followUser) Color(0xFFB84DFF) else Color.Gray)
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = "Seguir Ubicación")
        }

        // Muestra un mensaje si el permiso no está concedido
        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage!!,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(Color.White)
                    .padding(8.dp)
            )
        }
    }
}

// Puedes borrar el Composable Map() original que solo tenía el Image.


//Nav de navegacion (falta hacerlo reutilizable)
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


//Dialog genérico
@Composable
fun MinimalDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "This is a minimal dialog",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
    }
}
@Preview
@Composable
fun MinimalDialogPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        MinimalDialog(onDismissRequest = {})
    }
}
