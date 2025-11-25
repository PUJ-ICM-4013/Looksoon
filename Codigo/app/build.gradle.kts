

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.looksoon"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.looksoon"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

// BLOQUE DE DEPENDENCIAS CORRECTO Y ÚNICO
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // --- Firebase BOM y servicios ---
    implementation(platform("com.google.firebase:firebase-bom:34.4.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore") // ✅ FIRESTORE AGREGADO

    // --- OkHttp para Cloudinary (subida de imágenes) ---
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // ✅ OKHTTP PARA CLOUDINARY
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // ✅ OPCIONAL: para logs


    // --- Compose UI, Material3 y Navegación (versiones actualizadas) ---
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-graphics:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

    // --- Permisos ---
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    // --- Ktor, Coil, Coroutines ---
    implementation("io.ktor:ktor-client-android:2.3.12")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // --- DEPENDENCIAS PARA GOOGLE MAPS Y RUTAS ---
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.maps.android:maps-compose:6.4.1")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")

    // --- DEPENDENCIAS DE TEST (DENTRO DEL BLOQUE CORRECTO) ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")


    // ----- RETRO FIT ---------
    dependencies {
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

        // ViewModel + Kotlin Coroutines
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")

        // Jetpack Compose
        implementation("androidx.compose.ui:ui:1.7.0")
        implementation("androidx.compose.material3:material3:1.2.0")
        implementation("androidx.activity:activity-compose:1.9.0")
    }

}
