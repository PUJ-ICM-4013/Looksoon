package com.example.looksoon.model

data class Track(
    val id: String,
    val title: String,
    val duration: Int?,
    val artwork: Artwork?,
    val user: User?
)

data class Artwork(
    val url: String?
)


data class TrendingTracksResponse(
    val data: List<Track>
)
