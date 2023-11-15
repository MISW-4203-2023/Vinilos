package com.team3.vinilos.model.models

import kotlinx.serialization.Serializable

@Serializable
data class PerformerPrize(
    val id:Long,
    val premiationDate:String? = null,
)