package com.team3.vinilos.test.fake

import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.repository.CollectorsRepository
import java.io.IOException

class FakeFailNetworkCollectorRepository: CollectorsRepository {
    override suspend fun getCollectors(): List<Collector> {
        throw IOException()
    }
}