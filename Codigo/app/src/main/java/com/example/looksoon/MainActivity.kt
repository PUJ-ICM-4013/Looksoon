package com.example.looksoon

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.looksoon.repository.UserRepository
import com.example.looksoon.ui.theme.navigation.AppNavigation
import com.example.looksoon.ui.theme.LooksoonTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val userRepository = UserRepository()
    private val auth = FirebaseAuth.getInstance()

    // ✅ Variable para controlar si el usuario hizo login en esta sesión
    private var hasLoggedIn = false

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
            val storageGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

            if (!cameraGranted || !storageGranted) {
                println("⚠️ Permisos de cámara o almacenamiento no concedidos")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestAppPermissions()

        setContent {
            LooksoonTheme {
                AppNavigation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // ✅ SOLO actualizar estado si el usuario hizo login EN ESTA SESIÓN
        // NO hacerlo automáticamente al abrir la app
        if (auth.currentUser != null && hasLoggedIn) {
            lifecycleScope.launch {
                userRepository.updateOnlineStatus(true)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // ✅ Usuario sale de la app → Estado en línea = false
        if (auth.currentUser != null) {
            lifecycleScope.launch {
                userRepository.updateOnlineStatus(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // ✅ App se cierra → Estado en línea = false
        if (auth.currentUser != null) {
            lifecycleScope.launch {
                userRepository.updateOnlineStatus(false)
            }
        }
    }

    private fun requestAppPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val storagePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (cameraPermission != PackageManager.PERMISSION_GRANTED ||
            storagePermission != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }
    }

    // ✅ NUEVA FUNCIÓN: Llamar desde LoginViewModel cuando el usuario haga login
    fun setUserLoggedIn(loggedIn: Boolean) {
        hasLoggedIn = loggedIn
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LooksoonTheme {
        Greeting("Android")
    }
}