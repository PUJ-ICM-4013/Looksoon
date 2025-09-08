package com.example.looksoon.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.theme.*

@Composable
fun PublishEventScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Background,
        bottomBar = {
            BottomBarPublicarEvento()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Título
            HePublish(
                section = "Publicar evento",
                onBackClick = {}
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Completa los detalles para que los artistas puedan postularse.",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Nombre del evento
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Ej. Noche de Jazz en Looksoon", color = TextSecondary) },
                leadingIcon = { Icon(Icons.Default.ConfirmationNumber, contentDescription = null, tint = PurplePrimary) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fecha y hora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = "", 
                    onValueChange = {},
                    label = { Text("DD/MM/AAAA", color = TextSecondary) },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = PurplePrimary) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors()
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("20:00", color = TextSecondary) },
                    leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null, tint = PurplePrimary) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Duración y presupuesto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("2 horas", color = TextSecondary) },
                    leadingIcon = { Icon(Icons.Default.HourglassBottom, contentDescription = null, tint = PurplePrimary) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors()
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Presupuesto", color = TextSecondary) },
                    leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null, tint = PurplePrimary) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tipo de evento
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Bar, restaurante, corporativo, boda...", color = TextSecondary) },
                leadingIcon = { Icon(Icons.Default.MusicNote, contentDescription = null, tint = PurplePrimary) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Cuéntanos sobre el ambiente, repertorio deseado...", color = TextSecondary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Ubicación con mapa
            Text("Ubicación", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Surface, RoundedCornerShape(12.dp))
                    .border(1.dp, Divider, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Mapa aquí", color = TextSecondary)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Material de promoción
            Text("Material de promoción", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Subir imagen o afiche", color = TextPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("URL o seleccionar", color = TextSecondary) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors()
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Ticketera o web", color = TextSecondary) },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Preferencias del artista
            Text("Preferencias del artista", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "Jazz",
                onValueChange = {},
                label = { Text("Género musical preferido", color = TextSecondary) },
                leadingIcon = { Icon(Icons.Default.MusicNote, contentDescription = null, tint = PurplePrimary) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "1 - 4",
                onValueChange = {},
                label = { Text("Número de artistas", color = TextSecondary) },
                leadingIcon = { Icon(Icons.Default.Group, contentDescription = null, tint = PurplePrimary) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "Moderado",
                onValueChange = {},
                label = { Text("Nivel de volumen permitido", color = TextSecondary) },
                leadingIcon = { Icon(Icons.Default.VolumeUp, contentDescription = null, tint = PurplePrimary) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun HePublish(section: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            tint = Color.White,
            contentDescription = "Atrás",
            modifier = Modifier
                .size(28.dp)
                .clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = section,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun BottomBarPublicarEvento() {
    Surface(
        tonalElevation = 4.dp,
        color = Surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Listo para publicar", color = TextPrimary, fontWeight = FontWeight.Bold)
                Text("Tu evento será visible para artistas cercanos", color = TextSecondary, fontSize = 12.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
            ) {
                Text("Publicar evento", color = Color.White)
            }
        }
    }
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Surface,
    unfocusedContainerColor = Surface,
    disabledContainerColor = Surface,
    focusedBorderColor = PurplePrimary,
    unfocusedBorderColor = Divider,
    focusedTextColor = TextPrimary,
    unfocusedTextColor = TextPrimary,
    cursorColor = PurplePrimary
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PublishEventPreview() {
    LooksoonTheme {
        PublishEventScreen(navController = rememberNavController())
    }
}

