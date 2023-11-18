package com.team3.vinilos.test.fake

import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.repository.CollectorsRepository


class FakeNetworkCollectorRepository: CollectorsRepository {
    override suspend fun getCollectors(): List<Collector> {
        return FakeUiDataSource.getCollector(100)
    }
}