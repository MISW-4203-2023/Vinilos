package com.team3.vinilos.model.network

import com.team3.vinilos.model.models.Collector
import retrofit2.http.GET

interface CollectorsApiService {
    @GET("collectors")
    suspend fun getCollectors(): List<Collector>
}