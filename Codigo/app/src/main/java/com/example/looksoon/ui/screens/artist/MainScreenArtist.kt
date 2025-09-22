package com.example.looksoon.ui.screens.artist

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.R
import com.example.faunafinder.navigation.Screen


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
            Map()
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

@Composable
fun Map() {
    Image(
        painter = painterResource(id = R.drawable.mapa),
        contentDescription = "Mapa",
        modifier = Modifier.fillMaxWidth().height(400.dp),
    )
    /*
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color(0xFF1C1C1C))
    )

     */
}

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
