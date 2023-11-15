package com.team3.vinilos.test.fake

import com.team3.vinilos.model.repository.AlbumsRepository
import com.team3.vinilos.model.models.Album
import java.io.IOException

class FakeFailNetworkAlbumsRepository: AlbumsRepository {
    override suspend fun getAlbums(): List<Album> {
        throw IOException()
    }
}