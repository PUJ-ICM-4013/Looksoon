package com.example.looksoon.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import com.example.looksoon.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreenArtist(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        HeaderArtist(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(/*Color(0xFF2A003F)*/ colorScheme.primary, Color.Black) // púrpura a negro
                    )
                )
        )
        Map()
        FiltersRow()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                EventCard(
                    title = "Concierto de Jazz",
                    date = "2022-01-01",
                    location = "New York",
                    imagePainter = painterResource(id = R.drawable.jazz),
                    onSeeMoreClick = {},
                )
            }
            item {
                EventCard(
                    title = "Concierto de Rock",
                    date = "2022-01-02",
                    location = "Los Angeles",
                    imagePainter = painterResource(id = R.drawable.jazz),
                    onSeeMoreClick = {},
                )
            }
            item {
                EventCard(
                    title = "Concierto de Pop",
                    date = "2022-01-03",
                    location = "Chicago",
                    imagePainter = painterResource(id = R.drawable.jazz),
                    onSeeMoreClick = {},
                )
            }
            item {
                EventCard(
                    title = "Concierto de Pop",
                    date = "2022-01-03",
                    location = "Chicago",
                    imagePainter = painterResource(id = R.drawable.jazz),
                    onSeeMoreClick = {},
                )
            }
            item {
                EventCard(
                    title = "Concierto de Jazz 2",
                    date = "2022-01-03",
                    location = "Chicago",
                    imagePainter = painterResource(id = R.drawable.jazz),
                    onSeeMoreClick = {},
                )}
            item {
                EventCard(
                    title = "Concierto de Jazz3",
                    date = "2022-01-03",
                    location = "Chicago",
                    imagePainter = painterResource(id = R.drawable.jazz),
                    onSeeMoreClick = {},
                )
            }
        }
    }
}
@Preview
@Composable
fun MainScreenArtistPreview() {
    LooksoonTheme {
        MainScreenArtist()
    }
}

@Composable
fun HeaderArtist(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
                     onNotificationsClick: () -> Unit = {},

) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono menú
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = Color.White
                )
            }

            // Título centrado
            Text(
                text = "Descubre Eventos",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            // Icono notificaciones
            IconButton(onClick = onNotificationsClick) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones",
                    tint = Color.White
                )
            }
        }
    }
}



@Preview
@Composable
fun HeaderArtistPreview() {
    LooksoonTheme {
        HeaderArtist(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(/*Color(0xFF2A003F)*/ colorScheme.primary, Color.Black) // púrpura a negro
                    )
                )
        )
    }
}


@Composable
fun GenreChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50)) // pill shape
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(colorScheme.primary /* PLAN B:0xFFB84DFF*/, /*PLAN B: Color(0xFF5A0FC8)*/ colorScheme.secondary) // degradado violeta
                )
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}



@Preview
@Composable
fun GenreChipPreview() {
    LooksoonTheme {
        GenreChip("Jazz")
    }
}


@Composable
fun FilterChip(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF1E1E1E)) // gris oscuro
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun FilterChipPreview() {
    LooksoonTheme {
        FilterChip("Distancia", Icons.Default.LocationOn)
    }
}


@Composable
fun FiltersRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip("Distancia", icon = Icons.Default.LocationOn)
        GenreChip("Rock")
        GenreChip("Pop")
        GenreChip("Jazz")
        GenreChip("Reggaeton")
        GenreChip("Alteranativo")
        GenreChip("Indie")
        GenreChip("Electrónica")
        GenreChip("Balada")


    }
}

@Preview
@Composable
fun FiltersRowPreview() {
    LooksoonTheme {
        FiltersRow()
    }
}



@Composable
fun EventCard(
    title: String,
    date: String,
    location: String,
    imagePainter: Painter,
    onSeeMoreClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF2C2C2C))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = title,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(text = date, color = Color.LightGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = location, color = Color.LightGray, fontSize = 14.sp)
                }

                // Botón degradado
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFFB84DFF), Color(0xFF5A0FC8))
                            )
                        )
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


@Preview
@Composable
fun EventCardPreview() {
    LooksoonTheme {
        EventCard(
            title = "Concierto de Jazz",
            date = "2022-01-01",
            location = "New York",
            imagePainter = painterResource(id = R.drawable.jazz),
            onSeeMoreClick = {},
            )
            }
}


@Composable
fun Map(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .background(Color(0xFF1C1C1C)))
}

@Preview
@Composable
fun MapPreview(){
    LooksoonTheme{
        Map()
    }
}


