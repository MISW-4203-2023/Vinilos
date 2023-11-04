package com.team3.vinilos

import com.team3.vinilos.data.NetworkAlbumsRepository
import com.team3.vinilos.fake.FakeAlbumApiService
import com.team3.vinilos.fake.FakeDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class NetworkAlbumsRepositoryTest {
    @Test
    fun  networkAlbumsRepository_getAlbums_verifyAlbumList() =
        runTest{
            val repository = NetworkAlbumsRepository(
                albumsApiService = FakeAlbumApiService()
            )
            Assert.assertEquals(FakeDataSource.listAlbum, repository.getAlbums())
        }
}