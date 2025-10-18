package com.example.looksoon.ui.screens.mix

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.looksoon.R
import com.example.looksoon.ui.theme.*

// Para evitar conflicto entre Surface (componente) y tu color Surface
import androidx.compose.material3.Surface as M3Surface

@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    val nameState = remember { mutableStateOf(TextFieldValue("Alex Rivera")) }
    val usernameState = remember { mutableStateOf(TextFieldValue("@alexrv")) }
    val locationState = remember { mutableStateOf(TextFieldValue("Ciudad de México")) }
    val emailState = remember { mutableStateOf(TextFieldValue("alex@musicwave.com")) }
    val phoneState = remember { mutableStateOf(TextFieldValue("+52 55 1234 5678")) }
    val bioState = remember { mutableStateOf(TextFieldValue("Artista independiente apasionado por la música indie y pop.")) }

    Scaffold { innerPadding->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background)

        ) {
            // Barra superior
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

                Spacer(modifier = Modifier.size(24.dp)) // espacio para equilibrar
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Contenedor principal de edición
            M3Surface(
                color = Surface, // tu color Surface
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
                    // Sección de foto de perfil
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.foto),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, PurplePrimary, CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            IconButton(
                                onClick = { /* Acción para cambiar foto */ },
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
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campos de edición
                    EditTextField(
                        label = "Nombre completo",
                        value = nameState.value,
                        onValueChange = { nameState.value = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    EditTextField(
                        label = "Nombre de usuario",
                        value = usernameState.value,
                        onValueChange = { usernameState.value = it },
                        leadingText = "@"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    EditTextField(
                        label = "Ubicación",
                        value = locationState.value,
                        onValueChange = { locationState.value = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    EditTextField(
                        label = "Correo electrónico",
                        value = emailState.value,
                        onValueChange = { emailState.value = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    EditTextField(
                        label = "Teléfono",
                        value = phoneState.value,
                        onValueChange = { phoneState.value = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Biografía
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

                    Spacer(modifier = Modifier.height(24.dp))

                    // Géneros musicales
                    Text(
                        text = "Géneros musicales",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GenreChip("Indie", selected = true, onSelect = {})
                        GenreChip("Pop", selected = true, onSelect = {})
                        GenreChip("Rock", selected = false, onSelect = {})
                        GenreChip("Electrónica", selected = false, onSelect = {})
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "+ Agregar más",
                        color = PurplePrimary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón Cancelar
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextPrimary
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar", fontSize = 16.sp)
                }

                // Botón Guardar
                Button(
                    onClick = onSaveClick,
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

@Composable
fun EditTextField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    leadingText: String = ""
) {
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
            shape = RoundedCornerShape(12.dp),
            leadingIcon = if (leadingText.isNotEmpty()) {
                {
                    Text(
                        text = leadingText,
                        color = TextSecondary,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            } else null
        )
    }
}

@Composable
fun GenreChip(
    genre: String,
    selected: Boolean,
    onSelect: (String) -> Unit
) {
    M3Surface(
        color = if (selected) PurplePrimary else Surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (selected) PurplePrimary else Divider,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onSelect(genre) }
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = genre,
                color = if (selected) TextPrimary else TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen()
}
