package com.example.looksoon.ui.screens.mix

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.looksoon.R
import com.example.looksoon.ui.theme.*
import com.example.looksoon.ui.viewmodels.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable


// Evita conflicto entre Surface color y componente
import androidx.compose.material3.Surface as M3Surface


@Composable
fun EditTextField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    Column {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = TextPrimary),
            colors = TextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = PurplePrimary,
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface,
                focusedIndicatorColor = PurplePrimary,
                unfocusedIndicatorColor = Divider
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}
@Composable
fun EditProfileScreen(
    navController: NavHostController? = null,
    profileViewModel: ProfileViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    val context = LocalContext.current

    // 🟣 Estado local para la imagen seleccionada
    val selectedMediaUri = remember { mutableStateOf(profileViewModel.profileImageUri.value) }

    // 🟣 Launcher para abrir galería (imagen o video)
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedMediaUri.value = uri
            profileViewModel.setProfileImage(uri)
        } else {
            Toast.makeText(context, "No se seleccionó ningún archivo", Toast.LENGTH_SHORT).show()
        }
    }

    // 🟣 Launcher para tomar una foto con la cámara
    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = profileViewModel.saveBitmapToCache(context, bitmap)
            selectedMediaUri.value = uri
            profileViewModel.setProfileImage(uri)
        } else {
            Toast.makeText(context, "No se tomó ninguna foto", Toast.LENGTH_SHORT).show()
        }
    }


    val nameState = remember { mutableStateOf(TextFieldValue("Alex Rivera")) }
    val usernameState = remember { mutableStateOf(TextFieldValue("@alexrv")) }
    val locationState = remember { mutableStateOf(TextFieldValue("Ciudad de México")) }
    val emailState = remember { mutableStateOf(TextFieldValue("alex@musicwave.com")) }
    val phoneState = remember { mutableStateOf(TextFieldValue("+52 55 1234 5678")) }
    val bioState =
        remember { mutableStateOf(TextFieldValue("Artista independiente apasionado por la música indie y pop.")) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background)
        ) {
            // 🔹 Barra superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = TextPrimary
                    )
                }

                Text(
                    text = "Editar Perfil",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Contenedor principal
            M3Surface(
                color = Surface,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // 🟣 Imagen de perfil
                    Box(contentAlignment = Alignment.BottomEnd) {
                        if (selectedMediaUri.value != null) {
                            AsyncImage(
                                model = selectedMediaUri.value,
                                contentDescription = "Foto de perfil seleccionada",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, PurplePrimary, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.foto),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, PurplePrimary, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        var showDialog by remember { mutableStateOf(false) }

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Seleccionar imagen") },
                                text = {
                                    Column {
                                        TextButton(onClick = {
                                            showDialog = false
                                            takePhotoLauncher.launch(null)
                                        }) {
                                            Text("Tomar foto")
                                        }
                                        TextButton(onClick = {
                                            showDialog = false
                                            pickMediaLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        }) {
                                            Text("Elegir de galería")
                                        }
                                    }
                                },
                                confirmButton = {},
                                dismissButton = {}
                            )
                        }

                        IconButton(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .size(32.dp)
                                .background(PurplePrimary, CircleShape)
                                .border(1.dp, Surface, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PhotoCamera,
                                contentDescription = "Cambiar foto",
                                tint = TextPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } // ✅ AQUÍ cerramos el Box

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔹 Campos de texto
                    EditTextField("Nombre completo", nameState.value) { nameState.value = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    EditTextField("Nombre de usuario", usernameState.value) { usernameState.value = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    EditTextField("Ubicación", locationState.value) { locationState.value = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    EditTextField("Correo electrónico", emailState.value) { emailState.value = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    EditTextField("Teléfono", phoneState.value) { phoneState.value = it }
                    Spacer(modifier = Modifier.height(12.dp))

                    // 🔹 Biografía
                    Text(
                        text = "Biografía",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    OutlinedTextField(
                        value = bioState.value,
                        onValueChange = { bioState.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        textStyle = LocalTextStyle.current.copy(color = TextPrimary),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            cursorColor = PurplePrimary,
                            focusedContainerColor = Surface,
                            unfocusedContainerColor = Surface,
                            focusedIndicatorColor = PurplePrimary,
                            unfocusedIndicatorColor = Divider
                        ),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 5
                    )
                }



            }
            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar", fontSize = 16.sp)
                }

                Button(
                    onClick = {
                        profileViewModel.setProfileImage(selectedMediaUri.value)
                        onSaveClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PurplePrimary,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Guardar cambios", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen()
}