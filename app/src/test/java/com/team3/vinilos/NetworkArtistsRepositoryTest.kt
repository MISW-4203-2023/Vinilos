package com.team3.vinilos

import com.team3.vinilos.model.repository.NetworkArtistsRepository
import com.team3.vinilos.fake.FakeArtistApiService
import com.team3.vinilos.fake.FakeDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals


class NetworkArtistsRepositoryTest {
    @Test
    fun  networkArtistsRepository_getArtists_verifyArtistList() =
        runTest{
            val repository = NetworkArtistsRepository(
                artistsApiService = FakeArtistApiService()
            )
        assertEquals(FakeDataSource.listArtist, repository.getArtists())
    }
}