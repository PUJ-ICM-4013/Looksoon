package com.example.looksoon.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.io.File

data class Post(
    val title: String,
    val description: String,
    val imageUri: Uri?
)

class PostViewModel : ViewModel() {

    // Lista observable de posts
    val posts = mutableStateListOf<Post>()

    // Uri seleccionada temporalmente
    val selectedImageUri = mutableStateOf<Uri?>(null)

    fun addPost(title: String, description: String, imageUri: Uri?) {
        posts.add(Post(title = title, description = description, imageUri = imageUri))
    }

    fun setImage(uri: Uri?) {
        selectedImageUri.value = uri
    }

    /**
     * Guarda un Bitmap en cache y devuelve su Uri a través de FileProvider.
     * Asegúrate de tener <provider> en AndroidManifest.xml y file_paths.xml apuntando a cache-path.
     */
    fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
        val file = File(context.cacheDir, "post_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }
}
