package com.team3.vinilos.model.models

import com.team3.vinilos.model.serializer.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class CreateAlbum(
    val name: String,
    val cover: String? = null,
    @Serializable(with = DateSerializer::class)
    val releaseDate: Date? = null,
    val description: String? = null,
    val genre: String? = null,
    val recordLabel: String? = null,
)