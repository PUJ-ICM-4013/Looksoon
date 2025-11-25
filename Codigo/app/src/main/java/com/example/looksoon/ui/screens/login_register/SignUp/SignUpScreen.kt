package com.example.looksoon.ui.screens.login_register.SignUp

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
import com.example.looksoon.R
import com.example.looksoon.ui.screens.login_register.login.AccountFlowRow
import com.example.looksoon.ui.theme.PurplePrimary
import com.example.looksoon.ui.theme.TextPrimary
import com.example.looksoon.ui.theme.TextSecondary

@Composable
fun SignUpScreen(
                 onArtistClick: () -> Unit,
                 onBandClick: () -> Unit,
                 onFanClick: () -> Unit,
                 onEstablishmentClick: () -> Unit,
                 onCuratorClick: () -> Unit,
                 onLoginClick: () -> Unit) {
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
                    color = TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Digita tu rol en la comunidad",
                    color = TextSecondary,
                    fontSize = 14.sp

                )
                //Boton de roles
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    rol = "Artista",
                    OnClick = {
                        onArtistClick()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),

                    rol = "Banda",
                    OnClick = {
                        onBandClick()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),

                    rol = "Fan",
                    OnClick = {
                        onFanClick()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),

                    rol = "Local/Establecimiento",
                    OnClick = {
                        onEstablishmentClick()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonRoles(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),

                    rol = "Curador",
                    OnClick = {
                        onCuratorClick()
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                //Sign in row
                AccountFlowRow(
                    onLinkClick = {
                        onLoginClick()
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
    SignUpScreen(onArtistClick = {}, onBandClick = {}, onFanClick = {}, onEstablishmentClick = {}, onCuratorClick = {}, onLoginClick = {})
}

@Composable
fun ButtonRoles(modifier: Modifier = Modifier.fillMaxWidth(),

                rol: String,
                OnClick: () -> Unit) {
    Button(
        onClick = { OnClick() },
        modifier = modifier

            .height(48.dp)
        ,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PurplePrimary,
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