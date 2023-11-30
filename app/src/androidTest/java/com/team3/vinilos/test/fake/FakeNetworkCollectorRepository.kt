package com.team3.vinilos.test.fake

import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.repository.CollectorRepository

class FakeNetworkCollectorRepository : CollectorRepository {
    override suspend fun getCollector(id: Long): Collector {
        return FakeUiDataSource.getCollector(100, id.toInt())
    }
}