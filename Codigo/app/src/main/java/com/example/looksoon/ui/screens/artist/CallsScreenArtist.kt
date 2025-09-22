package com.example.looksoon.ui.screens.artist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.faunafinder.navigation.Screen
import com.example.looksoon.R

@Composable
fun CallsScreenArtist(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
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
            }
        ) { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Header
                HeaderArtist(
                    section = "Convocatorias",
                    iconLeft = Icons.Default.Menu,
                    iconRight = Icons.Default.Notifications,
                    contentDescriptionLeft = "Menú",
                    contentDescriptionRight = "Notificaciones",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(colorScheme.primary, Color.Black)
                            )
                        )
                )

                // Search + filtros
                SearchAndFilterBar()

                // Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GenreChip("Próximas")
                    GenreChip("Cerca de mí")
                    GenreChip("Cuerna")
                    GenreChip("Rock")
                    GenreChip("Pop")
                }

                Text(
                    "Para ti",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                // Lista de eventos
                LazyColumn {
                    items(6) {
                        EventCard(navController = navController)
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun CallsScreenArtistPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        CallsScreenArtist(navController = rememberNavController())
    }
}

/*Composable para crear un search*/
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            // Centrar texto

            Text(

                text = "Buscar convocatorias, bares, géneros...",
                color = Color.LightGray,
                fontSize = 16.sp,
                //Centrar texto
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                overflow = TextOverflow.Visible,
                lineHeight = 16.sp

            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color.White
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF2B0A40), // Fondo morado oscuro
            unfocusedContainerColor = Color(0xFF2B0A40),
            focusedBorderColor = colorScheme.tertiary,
            unfocusedBorderColor = colorScheme.surface,
            cursorColor = Color.White,
        ),
        textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(0.70f) // ocupa 75% del ancho
    )
}

//Composable para el botón de filtros
@Composable
fun FilterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1C1C1C), // negro/gris oscuro
            contentColor = Color.White
        ),
        modifier = modifier.height(56.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Filtros",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = "Filtros", fontSize = 12.sp)
    }
}



@Composable
fun SearchAndFilterBar() {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(query = query, onQueryChange = { query = it })
        Spacer(modifier = Modifier.width(8.dp))
        FilterButton(onClick = { /* Acción de filtros */ })
    }
}

@Preview
@Composable
fun SearchAndFilterBarPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        SearchAndFilterBar();
    }
}


// Tarjetas
@Composable
fun EventCard(navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B0D0F))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Imagen + título
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.jazz),
                    contentDescription = "Evento",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Bandas en Vivo - Teatro Central",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Distancia
            Row(
                modifier = Modifier
                    .background(Color(0xFF1C1C1C), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Distancia",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "3.4 km", color = Color.White, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Fecha límite
            Row(
                modifier = Modifier
                    .background(Color(0xFF1C1C1C), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Fecha",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Hasta 12 Jun", color = Color.White, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Pago
            Row(
                modifier = Modifier
                    .background(Color(0xFF1C1C1C), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Pago",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Pagado", color = Color.White, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botones
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB84DFF),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Postular")
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.EventDetailsArtist.route)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.Gray),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Detalles")
                }
            }
        }
    }
}
@Preview
@Composable
fun ECardPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        EventCard(navController = rememberNavController())
    }
}


