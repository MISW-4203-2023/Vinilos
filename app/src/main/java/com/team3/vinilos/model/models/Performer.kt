package com.team3.vinilos.model.models

import com.team3.vinilos.model.serializer.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Performer (
    val id: Long,
    val name: String,
    val image: String,
    val description: String,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date? = null,
    val creationDate: String? = null
)