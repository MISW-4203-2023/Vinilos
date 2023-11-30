package com.team3.vinilos

import com.team3.vinilos.fake.FakeAlbumsApiService
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.model.repository.NetworkAlbumAddRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class NetworkAlbumAddRepositoryTest {
    @Test
    fun  networkAlbumsRepository_getAlbum_verifyAlbum() =
        runTest{

            val createAlbum = CreateAlbum("Nuevo Álbum", "Nuevo Artista")
            val fakeAlbumsApiService = FakeAlbumsApiService()
            val repository = NetworkAlbumAddRepository(albumApiService = fakeAlbumsApiService)
            val resultAlbum = repository.addAlbum(createAlbum)

            assertEquals(createAlbum.name, resultAlbum.name)
        }
}