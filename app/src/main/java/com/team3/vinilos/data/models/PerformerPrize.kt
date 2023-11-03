package com.team3.vinilos.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PerformerPrize(
    val id:Long,
    val premiationDate:String? = null,
)