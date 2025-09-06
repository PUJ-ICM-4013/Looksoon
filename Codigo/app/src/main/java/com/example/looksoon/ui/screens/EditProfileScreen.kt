package com.example.looksoon.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.looksoon.ui.theme.*

// Pantalla principal
@Composable
fun EditProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background) // Fondo negro
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Editar perfil",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Información básica
        SectionTitle("Información básica")
        CustomTextField("La Banda Andina", "Nombre artístico")
        CustomTextField("Bogotá, Colombia", "Ubicación")

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Chip("Indie Rock")
            Spacer(Modifier.width(6.dp))
            Chip("Folk")
            Spacer(Modifier.width(6.dp))
            Chip("Español")
        }

        // Medios y enlaces
        SectionTitle("Medios y enlaces")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = CircleShape,
                modifier = Modifier.size(64.dp),
                color = Divider
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_camera),
                    contentDescription = "Foto de perfil",
                    tint = TextSecondary,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
            ) {
                Text("Subir", color = TextPrimary)
            }
        }

        CustomTextField(
            "spotify.com/labandaandina, instagram.com/labandaandina",
            "Links"
        )

        // Press kit
        SectionTitle("Press kit")
        CustomTextField("10 canciones • 45 min", "Setlist")
        CustomTextField("PDF • actualizado ayer", "Bio y rider técnico")

        // Disponibilidad
        SectionTitle("Disponibilidad")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Surface, shape = RoundedCornerShape(12.dp))
        ) {
            Text("Mapa aquí", modifier = Modifier.align(Alignment.Center), color = TextSecondary)
        }

        CustomTextField("$400k - $1.2M COP", "Rango de caché")

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Text("Guardar cambios", color = TextPrimary)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = TextSecondary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun CustomTextField(value: String, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label, color = TextSecondary) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = PurplePrimary,
            unfocusedBorderColor = Divider,
            cursorColor = PurplePrimary
        )
    )
}

@Composable
fun Chip(text: String) {
    Surface(
        color = Surface,
        shape = RoundedCornerShape(50),
        modifier = Modifier.clickable { }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 14.sp,
            color = TextPrimary
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen()
}
