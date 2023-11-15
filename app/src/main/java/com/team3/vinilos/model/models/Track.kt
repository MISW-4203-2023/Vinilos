package com.team3.vinilos.model.models

import kotlinx.serialization.Serializable

@Serializable
data class Track (
    val id: Long,
    val name: String,
    val duration: String
)