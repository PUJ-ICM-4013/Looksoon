package com.example.looksoon.ui.screens.mix

import android.app.AlertDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.looksoon.R
import com.example.looksoon.ui.screens.artist.mainscreenartist.BottomNavBar
import com.example.looksoon.ui.screens.artist.mainscreenartist.HeaderArtist
import com.example.looksoon.ui.screens.login_register.SignUp.ButtonRoles
import com.example.looksoon.ui.theme.TextPrimary
import com.example.looksoon.ui.theme.navigation.Screen
import com.example.looksoon.ui.viewmodels.PostViewModel
import com.example.looksoon.utils.CloudinaryUploader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    navController: NavHostController,
    postViewModel: PostViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) }
    var uploadStatus by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val genres = listOf("Rock", "Pop", "Jazz", "Electrónica", "Clásica", "Hip-Hop")
    val types = listOf("Publicación", "Historia", "Obra musical")

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val selectedImageUri = postViewModel.selectedImageUri

    val takePhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = postViewModel.saveBitmapToCache(context, it)
            postViewModel.setImage(uri)
        }
    }

    val pickMediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        postViewModel.setImage(uri)
    }

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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderArtist(
                section = "Creación de Contenido",
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
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo_looksoon),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(120.dp)
                )

                Text(
                    text = "¡Crear Publicación!",
                    color = TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                // File Uploader
                FileUploader(
                    progress = uploadProgress,
                    remainingTime = uploadStatus,
                    onSelectAudio = {},
                    onSelectVideo = {},
                    onSelectPhoto = {
                        val options = listOf("Tomar foto", "Elegir de galería")
                        AlertDialog.Builder(context)
                            .setTitle("Seleccionar imagen")
                            .setItems(options.toTypedArray()) { _, which ->
                                when (which) {
                                    0 -> takePhotoLauncher.launch(null)
                                    1 -> pickMediaLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                            }
                            .show()
                    }
                )

                // Vista previa de imagen
                selectedImageUri.value?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                    )
                }

                // Campos de entrada
                ReusableTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Título (opcional)"
                )

                ReusableTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Descripción",
                    singleLine = false
                )

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
                    label = "Enlace (Spotify, YouTube, etc.)"
                )

                // Mostrar error si existe
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón Publicar
                Button(
                    onClick = {
                        if (!isUploading && description.isNotEmpty()) {
                            coroutineScope.launch {
                                isUploading = true
                                errorMessage = null
                                uploadProgress = 0f

                                try {
                                    uploadStatus = "Preparando..."
                                    uploadProgress = 0.1f

                                    val result = createPostInFirestore(
                                        title = title,
                                        description = description,
                                        genre = genre,
                                        type = type,
                                        link = link,
                                        imageUri = selectedImageUri.value,
                                        context = context,
                                        onProgress = { progress, status ->
                                            uploadProgress = progress
                                            uploadStatus = status
                                        }
                                    )

                                    if (result.isSuccess) {
                                        uploadStatus = "¡Publicado!"
                                        uploadProgress = 1f

                                        // Limpiar campos
                                        postViewModel.clearImage()

                                        // Navegar al feed
                                        navController.navigate(Screen.Feed.route) {
                                            popUpTo(Screen.Feed.route) { inclusive = true }
                                        }
                                    } else {
                                        errorMessage = result.exceptionOrNull()?.message ?: "Error al crear el post"
                                        uploadStatus = ""
                                        uploadProgress = 0f
                                    }
                                } catch (e: Exception) {
                                    errorMessage = e.message ?: "Error desconocido"
                                    uploadStatus = ""
                                    uploadProgress = 0f
                                } finally {
                                    isUploading = false
                                }
                            }
                        } else if (description.isEmpty()) {
                            errorMessage = "La descripción es obligatoria"
                        }
                    },
                    enabled = !isUploading && description.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                ) {
                    if (isUploading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(if (isUploading) "Publicando..." else "Publicar")
                }

                TextButton(
                    onClick = { navController.popBackStack() },
                    enabled = !isUploading
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}

// Función para crear post en Firestore con Cloudinary
suspend fun createPostInFirestore(
    title: String,
    description: String,
    genre: String,
    type: String,
    link: String,
    imageUri: Uri?,
    context: android.content.Context,
    onProgress: (Float, String) -> Unit
): Result<String> {
    return try {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
            ?: return Result.failure(Exception("Usuario no autenticado"))

        onProgress(0.2f, "Cargando datos del usuario...")

        // Buscar usuario en todas las colecciones posibles
        val collections = listOf("Artista", "Bandas", "Fan", "Curador", "Establecimiento")
        var userName = currentUser.displayName ?: "Usuario"
        var userImage = ""
        var userRole = ""

        for (collection in collections) {
            try {
                val userDoc = db.collection(collection).document(currentUser.uid).get().await()
                if (userDoc.exists()) {
                    userName = userDoc.getString("nombreReal")
                        ?: userDoc.getString("nombreArtistico")
                                ?: userDoc.getString("name")
                                ?: userName
                    userImage = userDoc.getString("profileImageUrl") ?: ""
                    userRole = userDoc.getString("role") ?: collection.lowercase()
                    break
                }
            } catch (e: Exception) {
                continue
            }
        }

        onProgress(0.3f, "Datos cargados")

        // Subir imagen a Cloudinary si existe
        var imageUrl = ""
        if (imageUri != null) {
            onProgress(0.4f, "Subiendo imagen a Cloudinary...")

            try {
                imageUrl = CloudinaryUploader.uploadImage(imageUri, context)
                onProgress(0.8f, "Imagen subida correctamente")
            } catch (e: Exception) {
                return Result.failure(Exception("Error al subir imagen: ${e.message}"))
            }
        } else {
            onProgress(0.8f, "Sin imagen para subir")
        }

        onProgress(0.9f, "Creando publicación...")

        // Crear documento del post
        val postData = hashMapOf(
            "userId" to currentUser.uid,
            "userName" to userName,
            "userImage" to userImage,
            "userRole" to userRole,
            "title" to title,
            "description" to description,
            "genre" to genre,
            "type" to type,
            "link" to link,
            "imageUrl" to imageUrl,
            "timestamp" to System.currentTimeMillis(),
            "likesCount" to 0,
            "commentsCount" to 0,
            "likes" to emptyList<String>()
        )

        val postRef = db.collection("Posts").add(postData).await()

        onProgress(1f, "¡Publicación creada!")

        Result.success(postRef.id)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(Exception("Error al crear post: ${e.message}"))
    }
}

// Componentes reutilizables
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
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
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
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun FileUploader(
    progress: Float,
    remainingTime: String,
    onSelectAudio: () -> Unit,
    onSelectVideo: () -> Unit,
    onSelectPhoto: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.CloudUpload,
            contentDescription = "Upload",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Selecciona una imagen",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Fotos: JPG/PNG • Máx 10MB",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            FileTypeButton(text = "Fotos", onClick = onSelectPhoto)
        }

        if (progress > 0f) {
            Spacer(modifier = Modifier.height(20.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (remainingTime.isNotEmpty()) remainingTime else "Cargando: ${(progress * 100).toInt()}%",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
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