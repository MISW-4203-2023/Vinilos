package com.team3.vinilos.model.models

import com.team3.vinilos.model.serializer.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Artist(
    val id:Long,
    val name:String,
    val image:String? = null,
    val description:String? = null,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date? = null,
    val albums:List<Album>? = null,
    val performerPrizes:List<PerformerPrize>? = null,
)