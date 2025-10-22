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
import com.example.faunafinder.navigation.AppNavigation
import com.example.looksoon.ui.theme.LooksoonTheme

class MainActivity : ComponentActivity() {

    // ✅ Launcher para pedir permisos
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

        // ✅ Verificar y solicitar permisos antes de cargar la UI
        requestAppPermissions()

        setContent {
            LooksoonTheme {
                AppNavigation()
            }
        }
    }

    // ✅ Función que solicita los permisos si no están concedidos
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
