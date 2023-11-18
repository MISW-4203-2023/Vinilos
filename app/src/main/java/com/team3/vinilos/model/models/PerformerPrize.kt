package com.team3.vinilos.model.models

import com.team3.vinilos.model.serializer.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class PerformerPrize(
    val id: Long,
    @Serializable(with = DateSerializer::class)
    val premiationDate: Date? = null,
)