package com.example.looksoon.ui.screens.mix

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.looksoon.ui.screens.login_register.ButtonRoles
import com.example.looksoon.ui.screens.artist.BottomNavBar
import com.example.looksoon.ui.screens.artist.GenreChip
import com.example.looksoon.ui.screens.artist.HeaderArtist
import com.example.looksoon.ui.screens.artist.SearchAndFilterBar


@Composable
fun MessagesScreen(
    navController: NavHostController
) {
    //Scaffold para pantalla completa y que no pueda extenderse de los límites
    Scaffold(


        //Inidicar que se tendrá abajo el Nav
        bottomBar = {
            Column{
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),

                    rol = "Crear nuevo chat",
                    OnClick = {}
                )

                BottomNavBar(
                    selectedTab = "Mensajes",
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            popUpTo(Screen.Home.route)
                        }
                    }
                )
            }

        }
        //Usar padding necesario al contenido para que no se salga de la pantalla
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            //LazyColumn para mostrar la lista de chats(Falta hacerlo dinámico con datos)
            HeaderArtist(
                section = "Mensajes",
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
            SearchAndFilterBar()

            Row(){
                Spacer(modifier = Modifier.weight(1f))
                GenreChip("Todos", onClick = {

                })
                Spacer(modifier = Modifier.weight(1f))
                GenreChip("No leídos", onClick = {})
                Spacer(modifier = Modifier.weight(1f))
                GenreChip("Favoritos", onClick = {})
                Spacer(modifier = Modifier.weight(1f))
            }
            LazyColumn{
                item {
                    ChatCard(navController = navController)

                }
                item {
                    ChatCard(navController = navController)
                }
                item {
                    ChatCard(navController = navController)
                }
                item {
                    ChatCard(navController = navController)
                }
                item {
                    ChatCard(navController = navController)
                }
                item {
                    ChatCard(navController = navController)
                }
                item {
                    ChatCard(navController = navController)
                }
            }





        }
    }
}

@Preview
@Composable
fun MessagesScreenPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        MessagesScreen(navController = rememberNavController())
    }
}


//Tarjeta de cada chat
@Composable
fun ChatCard(navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            navController.navigate("Chat")
        },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF2C2C2C))){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

        ){

            Image( painter = painterResource(id = R.drawable.logo_looksoon), contentDescription = "Logo",
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop)

            Column (modifier = Modifier.padding(16.dp)

            ){
                Text(
                    "Ingeniero C",
                    modifier = Modifier.padding(16.dp)

                        ,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                    )

                Text(
                    "Hola",
                    modifier = Modifier.padding(16.dp)

                        ,
                    color = Color.White,
                    fontSize = 16.sp
                    )
            }
            //Hora de la última interacción
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "12:00",
                color = Color.White,
                fontSize = 16.sp,
                )
            Spacer(modifier = Modifier.width(24.dp))
            //Cantidad de mensajes recibidos (Fondo morado claro)
            Button(onClick = {},
                modifier = Modifier.size(40.dp),
                ){
                Text(
                    "2",
                    color = Color(0xFF9B4DFF),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }



        }
    }
}

@Preview
@Composable
fun ChatCardPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        ChatCard(navController = rememberNavController())
    }
}