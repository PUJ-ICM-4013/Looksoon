package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.faunafinder.navigation.Screen
import com.example.looksoon.R

@Composable
fun SignUpScreen(navController: NavHostController) {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        Scaffold() { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {

                Spacer(modifier = Modifier.weight(1f))
                // Logo and Welcome Text
                Image(
                    painter = painterResource(id = R.drawable.logo_looksoon), // Replace with your logo
                    contentDescription = "App Logo",
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    text = "¡Registrate!",
                    color = _root_ide_package_.com.example.looksoon.ui.theme.TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Digita tu rol en la comunidad",
                    color = _root_ide_package_.com.example.looksoon.ui.theme.TextSecondary,
                    fontSize = 14.sp

                )
                //Boton de roles
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    navController = navController,
                    rol = "Artista",
                    OnClick = {
                        navController.navigate(Screen.SignUpInformationArtist.route) {
                            popUpTo(Screen.SignUpInformationArtist.route) { inclusive = true }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    navController = navController,
                    rol = "Banda",
                    OnClick = {
                        navController.navigate(Screen.SignUpInformationBand.route) {
                            popUpTo(Screen.SignUpInformationBand.route) { inclusive = true }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    navController = navController,
                    rol = "Fan",
                    OnClick = {
                        navController.navigate(Screen.SignUpInformationFan.route) {
                            popUpTo(Screen.SignUpInformationFan.route) { inclusive = true }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    navController = navController,
                    rol = "Local/Establecimiento",
                    OnClick = {
                        navController.navigate(Screen.SignUpInformationEstablishment.route) {
                            popUpTo(Screen.SignUpInformationEstablishment.route) { inclusive = true }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    navController = navController,
                    rol = "Curador",
                    OnClick = {
                        navController.navigate(Screen.SignUpInformationCurator.route) {
                            popUpTo(Screen.SignUpInformationCurator.route) { inclusive = true }
                        }
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                //Sign in row
                AccountFlowRow(
                    onLinkClick = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    infoLeft = "¿Ya tienes una cuenta?",
                    infoRight = "Inicia sesión"
                )
            }
        }
    }
}
@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}

@Composable
fun ButtonRoles(modifier: Modifier = Modifier.fillMaxWidth(),
                navController: NavHostController,
                rol: String,
                OnClick: () -> Unit = {navController.navigate(Screen.Login.route)}) {
    Button(
        onClick = { OnClick() },
        modifier = modifier

            .height(48.dp)
        ,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = _root_ide_package_.com.example.looksoon.ui.theme.PurplePrimary,
            contentColor = Color.White
        )
    ) {
        Text(
            text = rol,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}