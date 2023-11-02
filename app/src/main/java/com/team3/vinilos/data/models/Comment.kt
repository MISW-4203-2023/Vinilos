package com.team3.vinilos.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Comment (
    val id: Long,
    val description: String,
    val rating: Long
)
