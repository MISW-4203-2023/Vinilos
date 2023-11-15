package com.team3.vinilos.model.models

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: Long,
    val name: String,
    val cover: String? = null,
    val releaseDate: String? = null,
    val description: String? = null,
    val genre: String? = null,
    val recordLabel: String? = null,
    val tracks: List<Track>? = null,
    val performers: List<Performer>? = null,
    val comments: List<Comment>? = null
)