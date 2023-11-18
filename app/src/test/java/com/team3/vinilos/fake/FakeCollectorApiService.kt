package com.team3.vinilos.fake

import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.network.CollectorsApiService

class FakeCollectorApiService : CollectorsApiService {
    override suspend fun getCollectors(): List<Collector> {
        return FakeDataSource.listCollector
    }
}