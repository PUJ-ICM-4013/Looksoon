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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.looksoon.R
import com.example.looksoon.ui.screens.artist.mainscreenartist.BottomNavBar
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist
import com.example.looksoon.ui.theme.navigation.Screen
import com.example.looksoon.ui.viewmodels.PostViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

// Modelo de datos para posts de Firestore
data class Post(
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userImage: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val timestamp: Long = 0L,
    val likesCount: Int = 0,
    val commentsCount: Int = 0
)

@Composable
fun FeedScreen(navController: NavHostController, postViewModel: PostViewModel) {
    val posts = remember { mutableStateListOf<Post>() }
    val isLoading = remember { mutableStateOf(true) }

    // Cargar posts de Firestore
    LaunchedEffect(Unit) {
        loadPostsFromFirestore(posts, isLoading)
    }

    Scaffold(
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
    ) { innerPadding ->
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

            if (isLoading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (posts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No hay publicaciones aún",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            } else {
                DynamicPostList(
                    posts = posts,
                    navController = navController
                )
            }
        }
    }
}

// Función para cargar posts desde Firestore
fun loadPostsFromFirestore(
    posts: MutableList<Post>,
    isLoading: MutableState<Boolean>
) {
    val db = FirebaseFirestore.getInstance()

    db.collection("Posts")
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .limit(50)
        .addSnapshotListener { snapshot, error ->
            if (error != null) {
                isLoading.value = false
                return@addSnapshotListener
            }

            posts.clear()
            snapshot?.documents?.forEach { doc ->
                val post = Post(
                    postId = doc.id,
                    userId = doc.getString("userId") ?: "",
                    userName = doc.getString("userName") ?: "Usuario",
                    userImage = doc.getString("userImage") ?: "",
                    description = doc.getString("description") ?: "",
                    imageUrl = doc.getString("imageUrl") ?: "",
                    timestamp = doc.getLong("timestamp") ?: 0L,
                    likesCount = doc.getLong("likesCount")?.toInt() ?: 0,
                    commentsCount = doc.getLong("commentsCount")?.toInt() ?: 0
                )
                posts.add(post)
            }
            isLoading.value = false
        }
}

@Composable
fun DynamicPostList(
    posts: List<Post>,
    navController: NavHostController
) {
    LazyColumn {
        items(posts) { post ->
            DynamicPostCard(
                post = post,
                navController = navController
            )
        }
    }
}

@Composable
fun DynamicPostCard(
    post: Post,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(width = 2.dp, color = colorScheme.primary)
    ) {
        Column(modifier = Modifier.background(colorScheme.surface)) {
            // Header con navegación al perfil
            DynamicPostHeader(
                post = post,
                onUserClick = {
                    navController.navigate("user_profile/${post.userId}")
                }
            )

            // Descripción del post
            if (post.description.isNotEmpty()) {
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    color = Color.White
                )
            }

            // Imagen del post
            if (post.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = post.imageUrl,
                    contentDescription = "Imagen del post",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Footer con reacciones
            DynamicPostFooter(
                post = post,
                onLikeClick = { /* TODO: Implementar like */ },
                onCommentClick = { /* TODO: Ir a comentarios */ },
                onShareClick = { /* TODO: Compartir */ }
            )
        }
    }
}

@Composable
fun DynamicPostHeader(
    post: Post,
    onUserClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onUserClick)
            .padding(12.dp)
    ) {
        // Foto de perfil
        if (post.userImage.isNotEmpty()) {
            AsyncImage(
                model = post.userImage,
                contentDescription = post.userName,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.logo_looksoon),
                contentDescription = post.userName,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = post.userName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.White
            )
            Text(
                text = formatTimestamp(post.timestamp),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DynamicPostFooter(
    post: Post,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Botón de Like con contador
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onLikeClick)
        ) {
            IconButton(onClick = onLikeClick) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Like",
                    tint = Color.White
                )
            }
            if (post.likesCount > 0) {
                Text(
                    text = post.likesCount.toString(),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        // Botón de Comentarios con contador
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onCommentClick)
        ) {
            IconButton(onClick = onCommentClick) {
                Icon(
                    Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Comment",
                    tint = Color.White
                )
            }
            if (post.commentsCount > 0) {
                Text(
                    text = post.commentsCount.toString(),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        // Botón de Compartir
        IconButton(onClick = onShareClick) {
            Icon(
                Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White
            )
        }
    }
}

// Función para formatear timestamp
fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60000 -> "Ahora"
        diff < 3600000 -> "Hace ${diff / 60000}m"
        diff < 86400000 -> "Hace ${diff / 3600000}h"
        diff < 172800000 -> "Ayer"
        else -> {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}

// Componentes existentes (Stories)
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