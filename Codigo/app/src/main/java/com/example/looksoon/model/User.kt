package com.example.looksoon.model


data class User(
    val userId: String = "",
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val bio: String = "",
    val profileImageUrl: String = "",
    val roles: List<String> = emptyList(), // ["Artista", "Curador", "Local", "Fan"]
    val genres: List<String> = emptyList(), // ["Rock", "Pop", "Jazz"]
    val eventsCount: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0L,

    // Campos específicos por rol
    val artistInfo: ArtistInfo? = null,
    val localInfo: LocalInfo? = null,
    val curatorInfo: CuratorInfo? = null,
    val fanInfo: FanInfo? = null
)

data class ArtistInfo(
    val artisticName: String = "",
    val instruments: List<String> = emptyList(),
    val yearsOfExperience: Int = 0,
    val spotifyUrl: String = "",
    val youtubeUrl: String = "",
    val instagramUrl: String = ""
)

data class LocalInfo(
    val venueName: String = "",
    val capacity: Int = 0,
    val address: String = "",
    val venueType: String = ""
)

data class CuratorInfo(
    val specialty: String = "",
    val organization: String = "",
    val certifications: List<String> = emptyList()
)

data class FanInfo(
    val favoriteGenres: List<String> = emptyList(),
    val eventsAttended: Int = 0
)