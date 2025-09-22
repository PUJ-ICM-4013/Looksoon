package com.example.looksoon.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.looksoon.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavHostController,
    selectedTab: String = "Perfil",
    bottomBar: @Composable () -> Unit = {}

) {
    var isFollowing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            HeaderArtist(
                section = if (isMyProfile) "Mi Perfil" else "Perfil de Artista",
                iconLeft = if (isMyProfile) Icons.Default.Menu else Icons.Default.ArrowBack,
                iconRight = Icons.Default.Notifications,
                onIconLeftClick = { if (!isMyProfile) navController.popBackStack() },
                contentDescriptionLeft = if (isMyProfile) "Menú" else "Atrás",
                contentDescriptionRight = "Notificaciones",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.5.dp)
                    .height(56.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(MaterialTheme.colorScheme.primary, Color.Black)
                        )
                    )
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedTab = "Perfil",
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(route) { inclusive = true }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // ----------- Foto de perfil e info básica ------------
                Surface(
                    color = Surface,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.foto),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.size(80.dp).clip(CircleShape).border(2.dp, PurplePrimary, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Alex Rivera", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("@alexrv", color = TextSecondary, fontSize = 14.sp)
                                Text("Ciudad de México", color = TextSecondary, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row {
                                    Text("Indie", color = TextPrimary, fontSize = 12.sp, modifier = Modifier.background(PurpleSecondary, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Pop", color = TextPrimary, fontSize = 12.sp, modifier = Modifier.background(PurpleSecondary, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // --- Tabs de rol dinámicas ---
                        val roles = listOf("Artista", "Curador", "Local", "Fan")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            roles.forEach { tab ->
                                val isSelected = tab == userRole
                                Surface(
                                    color = if (isSelected) PurplePrimary else Surface,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                                ) {
                                    Text(
                                        text = tab,
                                        color = if (isSelected) TextPrimary else TextSecondary,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // -------- Estadísticas ----------
                        Surface(color = Main, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("24", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("Eventos", color = TextSecondary, fontSize = 12.sp)
                                }
                                Divider(color = Divider, modifier = Modifier.height(30.dp).width(1.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("3.2k", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("Seguidores", color = TextSecondary, fontSize = 12.sp)
                                }
                                Divider(color = Divider, modifier = Modifier.height(30.dp).width(1.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("128", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("Siguiendo", color = TextSecondary, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- Tarjeta de Gamificación (solo para Fans) ---
                if (isMyProfile && userRole == "Fan") {
                    GamificationInfoCard(
                        currentLevel = "Melómano",
                        currentNotas = 255,
                        notasForNextLevel = 500
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // --- Botones Condicionales ---
                if (isMyProfile) {
                    Button(
                        onClick = { navController.navigate(Screen.Editar.route) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Editar perfil", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { navController.navigate(Screen.ManagePosts.route) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, PurplePrimary)
                    ) {
                        Text("Gestionar mis publicaciones", color = TextPrimary)
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = { isFollowing = !isFollowing },
                            modifier = Modifier.weight(1f).height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowing) Surface else PurplePrimary,
                                contentColor = TextPrimary
                            ),
                            border = if (isFollowing) BorderStroke(1.dp, PurplePrimary) else null,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(if (isFollowing) "Siguiendo" else "Seguir")
                        }
                        OutlinedButton(
                            onClick = { /* Navegar al chat */ },
                            modifier = Modifier.weight(1f).height(50.dp),
                            border = BorderStroke(1.dp, TextSecondary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Mensaje", color = TextPrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- Info de Contacto ---
                Surface(
                    color = Surface,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ProfileInfoItem("Correo", "alex@musicwave.com", Icons.Default.Email)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Teléfono", "+52 55 1234 5678", Icons.Default.Call)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Ubicación", "Roma Norte, CDMX", Icons.Default.LocationOn)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Roles", "Artista, Curador, Fan", Icons.Default.Person)
                    }
                }
            }
        }
    }
}

@Composable
fun GamificationInfoCard(
    currentLevel: String,
    currentNotas: Int,
    notasForNextLevel: Int
) {
    val progress = currentNotas.toFloat() / notasForNextLevel.toFloat()

    Surface(
        color = Surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Tu Nivel de Fanático", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(currentLevel, color = PurplePrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("$currentNotas / $notasForNextLevel 🎵", color = TextSecondary, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = PurplePrimary,
                trackColor = Divider
            )
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, contentDescription = label, tint = PurplePrimary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, color = TextPrimary, fontSize = 14.sp)
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()

    ProfileScreen(
        navController = navController,
        selectedTab = "Perfil",
        bottomBar = {
            BottomNavBar(
                selectedTab = "Perfil",
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(route) { inclusive = true }
                    }
                }
            )
        }
    )

