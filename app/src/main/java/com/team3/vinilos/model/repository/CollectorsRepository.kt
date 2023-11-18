package com.team3.vinilos.model.repository

import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.network.CollectorsApiService

interface CollectorsRepository {
    suspend fun getCollectors(): List<Collector>
}

class NetworkCollectorsRepository(
    private val collectorsApiService: CollectorsApiService
) : CollectorsRepository {
    override suspend fun getCollectors(): List<Collector> = collectorsApiService.getCollectors()
}

