package com.example.looksoon.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.faunafinder.navigation.Screen
import com.example.looksoon.R

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*

@Composable
fun FeedScreen(navController: NavHostController) {

    Scaffold (
        bottomBar = {
            BottomNavBar(
                selectedTab = "Feed",
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

    ){innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            //Llamar Composable de Header Artist
            HeaderArtist(
                section = "Looksoon",
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
            //Llamar Composable de Category Selector
            StoriesRow()

            //Llamar Composable de Post List
            PostList()

        }

    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    LooksoonTheme {
        FeedScreen(navController = rememberNavController())
    }
}


//Circulo para icono de usuario----------------------

// ---------- Composable reutilizable para un "story" ----------
@Composable
fun StoryItem(
    imageRes: Int,
    label: String,
    gradient: List<Color> = listOf(Color(0xFF8A2387), Color(0xFFE94057), Color(0xFFF27121)) // gradiente por defecto
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .drawBehind {
                    drawRoundRect(
                        brush = Brush.linearGradient(gradient),
                        cornerRadius = CornerRadius(size.width / 2, size.height / 2),
                        topLeft = Offset(0f, 0f),
                        size = size
                    )
                }
                .padding(3.dp) // grosor del borde
                .clip(CircleShape)
                .background(Color.Black) // fondo negro detrás de la foto
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = label,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )
    }
}

// ---------- Lista de stories (con datos quemados) ----------
@Composable
fun StoriesRow() {
    val stories = listOf(
        Pair(R.drawable.logo_looksoon, "Tú"),
        Pair(R.drawable.logo_looksoon, "Luna"),
        Pair(R.drawable.logo_looksoon, "Rastro"),
        Pair(R.drawable.logo_looksoon, "Neón"),
        Pair(R.drawable.logo_looksoon, "Venus")
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(stories) { story ->
            StoryItem(imageRes = story.first, label = story.second)
        }
    }
}

@Preview
@Composable
fun StoriesRowPreview() {
    StoriesRow()
}


//Tarjetas de publicaciones
@Composable
fun PostCard() {
    Card(){
        Column{

        }
    }
}

//------ tarjetas de publicaciones------



// ----------------- DATA CLASS PARA REUTILIZAR -----------------
data class PostData(
    val userName: String,
    val userImage: Int,
    val date: String,
    val text: String? = null,
    val image: Int? = null,
    val buttonLabel: String? = null,
    val onButtonClick: (() -> Unit)? = null
)

// ----------------- HEADER DE LA PUBLICACIÓN -----------------
@Composable
fun PostHeader(userName: String, userImage: Int, date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = userImage),
            contentDescription = userName,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = userName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(text = date, style = MaterialTheme.typography.bodySmall, fontSize = 12.sp)
        }
    }
}

// ----------------- FOOTER CON ACCIONES -----------------
@Composable
fun PostFooter(
    modifier: Modifier = Modifier,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = onLikeClick) {
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Like")
        }
        IconButton(onClick = onCommentClick) {
            Icon(Icons.Default.Edit, contentDescription = "Comment")
        }
        IconButton(onClick = onShareClick) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
    }
}

// ----------------- CONTENIDOS POSIBLES -----------------
@Composable
fun PostText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    )
}

@Composable
fun PostImage(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Post Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun PostButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = label, fontWeight = FontWeight.Medium)
    }
}

// ----------------- CARD GENERAL REUTILIZABLE -----------------
@Composable
fun PostCard(post: PostData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(width = 2.dp, color = colorScheme.primary)
    ) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            // Header
            PostHeader(userName = post.userName, userImage = post.userImage, date = post.date)

            // Contenido dinámico
            post.text?.let { PostText(it) }
            post.image?.let { PostImage(it) }
            post.buttonLabel?.let {
                post.onButtonClick?.let { action -> PostButton(it, action) }
            }

            // Footer
            PostFooter(
                onLikeClick = {  },
                onCommentClick = {  },
                onShareClick = {  }
            )
        }
    }
}

// ----------------- EJEMPLOS DE USO -----------------
@Composable
fun PostList() {
    LazyColumn {
        item{
            // Solo texto
            PostCard(
                PostData(
                    userName = "Luna",
                    userImage = R.drawable.logo_looksoon,
                    date = "Hoy, 9:00 am",
                    text = "Hoy inicia nuestra nueva convocatoria para artistas digitales 🎨🚀"
                )
            )

        }
        item{
            // Solo imagen
            PostCard(
                PostData(
                    userName = "Rastro",
                    userImage = R.drawable.logo_looksoon,
                    date = "Ayer, 6:30 pm",
                    image = R.drawable.logocompleto_looksoon
                )
            )
        }
        item{
            // Imagen + texto
            PostCard(
                PostData(
                    userName = "Neón",
                    userImage = R.drawable.logo_looksoon,
                    date = "Hace 2 días",
                    text = "Les comparto esta captura de nuestro último evento!",
                    image = R.drawable.logocompleto_looksoon
                )
            )
        }
        item{
            // Imagen + texto + botón
            PostCard(
                PostData(
                    userName = "Venus",
                    userImage = R.drawable.logo_looksoon,
                    date = "Hace 3 días",
                    text = "Convocatoria abierta para participar en el festival 🌟",
                    image = R.drawable.logocompleto_looksoon,
                    buttonLabel = "Ir a la convocatoria",
                    onButtonClick = {  }
                )
            )
        }






    }
}
@Preview
@Composable
fun PostListPreview() {
    PostList()
}
