package com.team3.vinilos.test.fake

import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.repository.CollectorsRepository


class FakeNetworkCollectorsRepository: CollectorsRepository {
    override suspend fun getCollectors(): List<Collector> {
        return FakeUiDataSource.getCollectors(100)
    }
}