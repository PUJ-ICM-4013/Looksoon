package com.example.looksoon.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

object CloudinaryUploader {
    // 🔑 Reemplaza estos valores con los tuyos
    private const val CLOUD_NAME = "dqzxphupx"
    private const val UPLOAD_PRESET = "looksoon_profiles"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun uploadImage(uri: Uri, context: Context): String {
        return withContext(Dispatchers.IO) {
            try {
                // Leer y comprimir la imagen
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                // Redimensionar para optimizar
                val resizedBitmap = resizeBitmap(bitmap, 1000, 1000)

                // Guardar en archivo temporal
                val file = File(context.cacheDir, "temp_upload_${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(file)
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
                outputStream.close()

                // Preparar request multipart
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.name,
                        file.asRequestBody("image/jpeg".toMediaTypeOrNull()))
                    .addFormDataPart("upload_preset", UPLOAD_PRESET)
                    .addFormDataPart("folder", "looksoon/profiles")
                    .build()

                // Hacer request a Cloudinary
                val request = Request.Builder()
                    .url("https://api.cloudinary.com/v1_1/$CLOUD_NAME/image/upload")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()

                // Limpiar archivo temporal
                file.delete()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val jsonObject = JSONObject(responseBody ?: "{}")
                    val secureUrl = jsonObject.getString("secure_url")
                    secureUrl
                } else {
                    throw Exception("Error al subir imagen: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception("Error al procesar imagen: ${e.message}")
            }
        }
    }

    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight

        if (ratioMax > ratioBitmap) {
            finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true)
    }
}