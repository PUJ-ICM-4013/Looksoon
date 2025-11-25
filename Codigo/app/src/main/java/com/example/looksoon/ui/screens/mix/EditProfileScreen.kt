package com.example.looksoon.ui.screens.mix

import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.looksoon.R
import com.example.looksoon.ui.theme.*
import com.example.looksoon.ui.viewmodels.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.looksoon.repository.UserRepository
import kotlinx.coroutines.launch

@Composable
fun EditTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
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
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun EditProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val userRepository = remember { UserRepository() }

    // Estados del usuario
    val user by profileViewModel.user.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState()

    // Estados locales para edición
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var nameState by remember { mutableStateOf("") }
    var usernameState by remember { mutableStateOf("") }
    var locationState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var phoneState by remember { mutableStateOf("") }
    var bioState by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    // Cargar datos del usuario al entrar
    LaunchedEffect(Unit) {
        profileViewModel.loadCurrentUserProfile()
    }

    // Actualizar estados locales cuando se carga el usuario
    LaunchedEffect(user) {
        user?.let {
            nameState = it.name
            usernameState = it.username
            locationState = it.location
            emailState = it.email
            phoneState = it.phone
            bioState = it.bio
        }
    }

    // Launcher para seleccionar imagen de galería
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
        } else {
            Toast.makeText(context, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher para tomar foto con cámara
    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            // Aquí deberías guardar el bitmap en un archivo temporal
            // Por ahora lo omitimos y usamos solo galería
            Toast.makeText(context, "Foto capturada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No se tomó ninguna foto", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold { innerPadding ->
        if (isLoading && user == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Background)
            ) {
                // Barra superior
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.navigateUp() },
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

                Spacer(modifier = Modifier.height(8.dp))

                // Contenedor principal
                Surface(
                    color = Surface,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Imagen de perfil
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            val imageUrl = selectedImageUri?.toString()
                                ?: user?.profileImageUrl?.takeIf { it.isNotEmpty() }

                            if (imageUrl != null) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Foto de perfil",
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
                                                    PickVisualMediaRequest(
                                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                                    )
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
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campos de texto
                        EditTextField("Nombre completo", nameState) { nameState = it }
                        Spacer(modifier = Modifier.height(12.dp))

                        EditTextField("Nombre de usuario", usernameState) { usernameState = it }
                        Spacer(modifier = Modifier.height(12.dp))

                        EditTextField("Ubicación", locationState) { locationState = it }
                        Spacer(modifier = Modifier.height(12.dp))

                        EditTextField("Correo electrónico", emailState) { emailState = it }
                        Spacer(modifier = Modifier.height(12.dp))

                        EditTextField("Teléfono", phoneState) { phoneState = it }
                        Spacer(modifier = Modifier.height(12.dp))

                        // Biografía
                        Text(
                            text = "Biografía",
                            color = TextSecondary,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                        )

                        OutlinedTextField(
                            value = bioState,
                            onValueChange = { bioState = it },
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

                // Botones
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancelar", fontSize = 16.sp)
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                isSaving = true
                                try {
                                    // Subir imagen si se seleccionó una nueva
                                    var imageUrl = user?.profileImageUrl ?: ""
                                    if (selectedImageUri != null) {
                                        imageUrl = userRepository.uploadProfileImage(selectedImageUri!!, context)
                                    }

                                    // Actualizar datos del usuario
                                    userRepository.updateUserProfile(
                                        name = nameState,
                                        username = usernameState,
                                        location = locationState,
                                        email = emailState,
                                        phone = phoneState,
                                        bio = bioState,
                                        profileImageUrl = imageUrl
                                    )

                                    Toast.makeText(
                                        context,
                                        "Perfil actualizado",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigateUp()
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } finally {
                                    isSaving = false
                                }
                            }
                        },
                        enabled = !isSaving,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurplePrimary,
                            contentColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = TextPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Guardar cambios", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}