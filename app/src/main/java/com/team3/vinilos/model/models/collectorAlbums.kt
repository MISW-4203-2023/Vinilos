package com.team3.vinilos.model.models

import kotlinx.serialization.Serializable

@Serializable
data class collectorAlbums (
    val id:Long,
    val price:Long,
    val status:String? = null,
)