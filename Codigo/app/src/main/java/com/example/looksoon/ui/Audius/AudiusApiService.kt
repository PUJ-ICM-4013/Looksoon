package com.example.looksoon.ui.Audius

import com.example.looksoon.model.TrendingTracksResponse
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface AudiusApiService {

    @GET("v1/tracks/trending")
    suspend fun getTrendingTracks(): TrendingTracksResponse

    companion object {
        private const val BASE_URL = "https://api.audius.co/"

        fun create(): AudiusApiService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AudiusApiService::class.java)
        }
    }
}
