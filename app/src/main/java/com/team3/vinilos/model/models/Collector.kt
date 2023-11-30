package com.team3.vinilos.model.models

import kotlinx.serialization.Serializable

@Serializable
data class Collector(
    val id: Long,
    val name: String,
    val telephone: String? = null,
    val email: String? = null,
    val comments: List<Comment>? = null,
    val favoritePerformers: List<Artist>? = null,
    val collectorAlbums: List<CollectorAlbums>? = null
)