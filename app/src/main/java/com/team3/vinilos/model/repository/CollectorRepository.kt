package com.team3.vinilos.model.repository

import android.util.Log
import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.network.CollectorApiService

interface CollectorRepository {
    suspend fun getCollector(id: Long): Collector
}

class NetworkCollectorRepository(
    private  val collectorApiService: CollectorApiService
) : CollectorRepository {
    override suspend fun getCollector(id: Long): Collector {
        Log.i("Repository", id.toString())
        return collectorApiService.getCollector(id)
    }

}