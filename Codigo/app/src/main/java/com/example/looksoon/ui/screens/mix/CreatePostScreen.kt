package com.example.looksoon.ui.screens.mix

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.example.looksoon.ui.screens.artist.mainscreenartist.BottomNavBar
import com.example.looksoon.ui.screens.ButtonRoles
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist
import com.example.looksoon.ui.theme.LooksoonTheme
import com.example.looksoon.ui.theme.TextPrimary

@Composable
fun ReusableTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = singleLine,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReusableDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) }
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(navController: NavHostController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }

    val genres = listOf("Rock", "Pop", "Jazz", "Electrónica", "Clásica", "Hip-Hop")
    val types = listOf("Publicación", "Historia", "Obra musical")

    Scaffold(

        bottomBar = {
            BottomNavBar(
                selectedTab = "Publicar",
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        },

        topBar = {
            HeaderArtist(
                section = "Descubre Eventos",
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

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            // Logo and Welcome Text
            Image(
                painter = painterResource(id = R.drawable.logo_looksoon), // Replace with your logo
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = "¡Crear Publicacion/Obra!",
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            FileUploader(
                progress = 0.62f,
                remainingTime = "2:15",
                onSelectAudio = {},
                onSelectVideo = {},
                onSelectPhoto = {}
            )
            // Campos de entrada
            ReusableTextField(value = title, onValueChange = { title = it }, label = "Título")
            ReusableTextField(value = description, onValueChange = { description = it }, label = "Descripción", singleLine = false)

            ReusableDropdown(
                label = "Categoría",
                options = types,
                selectedOption = type,
                onOptionSelected = { type = it }
            )

            ReusableDropdown(
                label = "Género musical",
                options = genres,
                selectedOption = genre,
                onOptionSelected = { genre = it }
            )

            ReusableTextField(
                value = link,
                onValueChange = { link = it },
                label = "Enlace (Spotify, YouTube, etc.)",
                )


            // TODO: agregar un ImagePicker composable
            // ReusableImagePicker()

            Spacer(modifier = Modifier.height(24.dp))

            ButtonRoles(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                navController = navController,
                rol = "Publicar",
                OnClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )

            TextButton(onClick = { navController.popBackStack() }) {
                Text("Cancelar")
            }
        }
    }
}

@Preview
@Composable
fun CreatePostScreenPreview() {
    LooksoonTheme {
        CreatePostScreen(navController = rememberNavController())
    }
}




// -------------------- COMPOSABLES PARA SUBIR ARCHIVO


@Composable
fun FileUploader(
    progress: Float,
    remainingTime: String,
    onSelectAudio: () -> Unit,
    onSelectVideo: () -> Unit,
    onSelectPhoto: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icono principal
        Icon(
            imageVector = Icons.Default.CloudUpload,
            contentDescription = "Upload",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto de arrastrar archivos
        Text(
            text = "Arrastra y suelta archivos aquí",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Tipos de archivo soportados
        Text(
            text = "Audio: MP3/WAV/FLAC • Video: MP4/MOV • Fotos: JPG/PNG • Máx 250MB",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de tipo de archivo
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            FileTypeButton(text = "Audio", onClick = onSelectAudio)
            FileTypeButton(text = "Video", onClick = onSelectVideo)
            FileTypeButton(text = "Fotos", onClick = onSelectPhoto)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Barra de progreso
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            trackColor = Color.DarkGray,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto de progreso
        Text(
            text = "Cargando: ${(progress * 100).toInt()}% • $remainingTime restante",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
@Composable
fun FileTypeButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        modifier = Modifier.width(90.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}
@Preview(showBackground = true)
@Composable
fun FileUploaderPreview() {
    LooksoonTheme {
        FileUploader(
            progress = 0.62f,
            remainingTime = "2:15",
            onSelectAudio = {},
            onSelectVideo = {},
            onSelectPhoto = {}
        )
    }
}

