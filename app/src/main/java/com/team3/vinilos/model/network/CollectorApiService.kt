package com.team3.vinilos.model.network

import com.team3.vinilos.model.models.Collector
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorApiService {
    @GET("collectors/{id}")
    suspend fun getCollector(@Path("id") id: Long): Collector
}