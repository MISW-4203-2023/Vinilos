package com.team3.vinilos.test.fake

import com.team3.vinilos.model.repository.AlbumsRepository
import com.team3.vinilos.model.models.Album

class FakeNetworkAlbumsRepository: AlbumsRepository {
    override suspend fun getAlbums(): List<Album> {
        return FakeUiDataSource.getAlbums(100)
    }
}