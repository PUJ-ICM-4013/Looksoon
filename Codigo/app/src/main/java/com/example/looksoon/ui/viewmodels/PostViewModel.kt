package com.example.looksoon.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

data class Post(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val userImage: String = "",
    val userRole: String = "",
    val title: String = "",
    val description: String = "",
    val genre: String = "",
    val type: String = "",
    val link: String = "",
    val imageUrl: String = "",
    val timestamp: Long = 0L,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val likes: List<String> = emptyList()
)

class PostViewModel : ViewModel() {

    // Lista observable de posts
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    // Uri seleccionada temporalmente
    val selectedImageUri = mutableStateOf<Uri?>(null)

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Estado de error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addPost(post: Post) {
        val currentPosts = _posts.value.toMutableList()
        currentPosts.add(0, post) // Agregar al inicio
        _posts.value = currentPosts
    }

    fun setPosts(posts: List<Post>) {
        _posts.value = posts
    }

    fun setImage(uri: Uri?) {
        selectedImageUri.value = uri
    }

    fun clearImage() {
        selectedImageUri.value = null
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setError(error: String?) {
        _error.value = error
    }

    fun clearError() {
        _error.value = null
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
