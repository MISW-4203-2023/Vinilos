package com.team3.vinilos.model.models

import kotlinx.serialization.Serializable

@Serializable
data class Performer (
    val id: Long,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String? = null,
    val creationDate: String? = null
)