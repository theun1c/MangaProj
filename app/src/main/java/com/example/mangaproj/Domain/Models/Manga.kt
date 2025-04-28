package com.example.mangaproj.Domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Manga.kt
@Serializable
data class Manga(
    val id: String,
    val title: String,
    val author: String,
    val description: String?,
    val genre: String,
    @SerialName("cover_url") val coverUrl: String,
    val chapters: Int,
    val rating: Float,
    val status: String
)