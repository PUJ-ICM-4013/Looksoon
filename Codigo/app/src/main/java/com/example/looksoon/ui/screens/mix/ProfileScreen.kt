package com.example.looksoon.ui.screens.mix

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.theme.navigation.Screen
import com.example.looksoon.R
import com.example.looksoon.ui.screens.artist.mainscreenartist.BottomNavBar
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist
import com.example.looksoon.ui.theme.*
import coil.compose.rememberAsyncImagePainter
import com.example.looksoon.ui.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    selectedTab: String = "Perfil"
)
{
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
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
                .padding(innerPadding) // ✅ Igual que MainScreenArtist
                .fillMaxSize()
                .background(Background) // fondo principal negro o tu color del tema
                .verticalScroll(rememberScrollState())
        ) {
            HeaderArtist(
                section = "Perfil",
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
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // ----------- Foto de perfil e info básica ------------
                Surface(
                    color = Surface,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = if (profileViewModel.profileImageUri.value != null) {
                                    rememberAsyncImagePainter(profileViewModel.profileImageUri.value)
                                } else {
                                    painterResource(id = R.drawable.foto)
                                },
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, PurplePrimary, CircleShape),
                                contentScale = ContentScale.Crop
                            )



                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Alex Rivera", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("@alexrv", color = TextSecondary, fontSize = 14.sp)
                                Text("Ciudad de México", color = TextSecondary, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row {
                                    Text(
                                        "Indie", color = TextPrimary, fontSize = 12.sp,
                                        modifier = Modifier
                                            .background(PurpleSecondary, RoundedCornerShape(8.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "Pop", color = TextPrimary, fontSize = 12.sp,
                                        modifier = Modifier
                                            .background(PurpleSecondary, RoundedCornerShape(8.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // -------- Tabs de rol ----------
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf("Artista", "Curador", "Local", "Fan").forEachIndexed { i, tab ->
                                Surface(
                                    color = if (i == 0) PurplePrimary else Surface,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 4.dp)
                                ) {
                                    Text(
                                        text = tab,
                                        color = if (i == 0) TextPrimary else TextSecondary,
                                        fontWeight = if (i == 0) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // -------- Estadísticas ----------
                        Surface(
                            color = Main,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("24", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("Eventos", color = TextSecondary, fontSize = 12.sp)
                                }
                                Divider(
                                    color = Divider,
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(1.dp)
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("3.2k", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("Seguidores", color = TextSecondary, fontSize = 12.sp)
                                }
                                Divider(
                                    color = Divider,
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(1.dp)
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("128", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("Siguiendo", color = TextSecondary, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // -------- Info de contacto ----------
                Surface(
                    color = Surface,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        ProfileInfoItem("Correo", "alex@musicwave.com", Icons.Default.Email)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Teléfono", "+52 55 1234 5678", Icons.Default.Call)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Ubicación", "Roma Norte, CDMX", Icons.Default.LocationOn)
                        Divider(color = Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Roles", "Artista, Curador", Icons.Default.Person)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // -------- Botón editar ----------
                Button(
                    onClick = { navController.navigate(Screen.Editar.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PurplePrimary,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Editar perfil", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }


                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = PurplePrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, color = TextPrimary, fontSize = 14.sp)
        }
    }
}
// ✅ PREVIEW corregido
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    val profileViewModel = androidx.lifecycle.viewmodel.compose.viewModel<ProfileViewModel>()
    ProfileScreen(navController = navController, profileViewModel = profileViewModel)
}