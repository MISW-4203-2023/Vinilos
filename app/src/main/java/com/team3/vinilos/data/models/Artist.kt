package com.team3.vinilos.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id:Long,
    val name:String,
    val image:String? = null,
    val description:String? = null,
    val birthDate:String? = null,
    val albums:List<Album>? = null,
    val performerPrizes:List<PerformerPrize>? = null,
)