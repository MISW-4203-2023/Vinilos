package com.team3.vinilos.model.models

import com.team3.vinilos.model.serializer.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Album(
    val id: Long,
    val name: String,
    val cover: String? = null,
    @Serializable(with = DateSerializer::class)
    val releaseDate: Date? = null,
    val description: String? = null,
    val genre: String? = null,
    val recordLabel: String? = null,
    val tracks: List<Track>? = null,
    val performers: List<Artist>? = null,
    val comments: List<Comment>? = null
)