package com.example.looksoon.ui.screens.mix

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.faunafinder.navigation.Screen
import com.example.looksoon.R
import com.example.looksoon.ui.screens.artist.BottomNavBar
import com.example.looksoon.ui.screens.artist.HeaderArtist

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
                            colors = listOf(colorScheme.primary, Color.Black)
                        )
                    )
            )
            StoriesRow()
            PostList(navController = navController)
        }
    }
}

// ... (El resto de los composables como StoryItem, StoriesRow, PostData, PostHeader, etc., no necesitan cambios, pero se incluyen aquí para que el archivo esté completo)

@Composable
fun StoryItem(
    imageRes: Int,
    label: String,
    gradient: List<Color> = listOf(Color(0xFF8A2387), Color(0xFFE94057), Color(0xFFF27121))
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
                    )
                }
                .padding(3.dp)
                .clip(CircleShape)
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = label,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape).fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White, textAlign = TextAlign.Center))
    }
}

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
        modifier = Modifier.fillMaxWidth().background(Color.Black).padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(stories) { story ->
            StoryItem(imageRes = story.first, label = story.second)
        }
    }
}

data class PostData(
    val userName: String,
    val userImage: Int,
    val date: String,
    val text: String? = null,
    val image: Int? = null,
    val buttonLabel: String? = null,
    val onButtonClick: (() -> Unit)? = null
)

@Composable
fun PostHeader(userName: String, userImage: Int, date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = userImage),
            contentDescription = userName,
            modifier = Modifier.size(42.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = userName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(text = date, style = MaterialTheme.typography.bodySmall, fontSize = 12.sp)
        }
    }
}

@Composable
fun PostFooter(
    modifier: Modifier = Modifier,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = onLikeClick) {
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Like")
        }
        IconButton(onClick = onCommentClick) {
            Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Comment")
        }
        IconButton(onClick = onShareClick) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
    }
}

@Composable
fun PostText(text: String) {
    Text(text = text, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp), modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
}

@Composable
fun PostImage(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Post Image",
        modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 8.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun PostButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(12.dp).fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = label, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun PostCard(post: PostData, navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(width = 2.dp, color = colorScheme.primary)
    ) {
        Column(modifier = Modifier.background(colorScheme.surface)) {
            // --- LÓGICA DE NAVEGACIÓN AL PERFIL ---
            Box(modifier = Modifier.clickable {
                // Navega al perfil del usuario, pasando un ID de ejemplo (el nombre del usuario)
                //navController.navigate(Screen.UserProfile.route.replace("{userId}", post.userName))
            }) {
                PostHeader(userName = post.userName, userImage = post.userImage, date = post.date)
            }

            // Contenido dinámico
            post.text?.let { PostText(it) }
            post.image?.let { PostImage(it) }
            post.buttonLabel?.let {
                post.onButtonClick?.let { action -> PostButton(it, action) }
            }

            // --- LÓGICA DE NAVEGACIÓN A COMENTARIOS ---
            PostFooter(
                onLikeClick = { },
                onCommentClick = {
                    // Navega a la pantalla de comentarios, pasando un ID de ejemplo
                    //navController.navigate(Screen.PostComments.route.replace("{postId}", "123"))
                },
                onShareClick = { }
            )
        }
    }
}

@Composable
fun PostList(navController: NavHostController) {
    val posts = listOf(
        PostData("Luna", R.drawable.logo_looksoon, "Hoy, 9:00 am", text = "Hoy inicia nuestra nueva convocatoria para artistas digitales 🎨🚀"),
        PostData("Rastro", R.drawable.logo_looksoon, "Ayer, 6:30 pm", image = R.drawable.logocompleto_looksoon),
        PostData("Neón", R.drawable.logo_looksoon, "Hace 2 días", text = "Les comparto esta captura de nuestro último evento!", image = R.drawable.logocompleto_looksoon),
        PostData("Venus", R.drawable.logo_looksoon, "Hace 3 días", text = "Convocatoria abierta para participar en el festival 🌟", image = R.drawable.logocompleto_looksoon, buttonLabel = "Ir a la convocatoria", onButtonClick = { })
    )

    LazyColumn {
        items(posts) { post ->
            PostCard(post = post, navController = navController)
        }
    }
}

@Preview
@Composable
fun PostListPreview() {
    PostList(navController = rememberNavController())
}
