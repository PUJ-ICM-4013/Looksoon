package com.example.looksoon

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
            permissions.entries.forEach {
                if (!it.value) {
                    println("⚠️ Permiso no concedido: ${it.key}")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestAppPermissions()
        NotificationUtils.createNotificationChannel(this)

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
        val permissionsToRequest = mutableListOf<String>()

        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        val storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionsLauncher.launch(permissionsToRequest.toTypedArray())
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