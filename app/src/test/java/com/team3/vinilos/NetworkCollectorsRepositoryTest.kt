package com.team3.vinilos

import com.team3.vinilos.fake.FakeCollectorApiService
import com.team3.vinilos.fake.FakeDataSource
import com.team3.vinilos.model.repository.NetworkCollectorsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals


class NetworkCollectorsRepositoryTest {
    @Test
    fun  networkCollectorsRepository_getCollectors_verifyCollectorList() =
        runTest{
            val repository = NetworkCollectorsRepository(
                collectorsApiService = FakeCollectorApiService()
            )
        assertEquals(FakeDataSource.listCollector, repository.getCollectors())
    }
}