package com.team3.vinilos.test.fake

import com.team3.vinilos.data.AlbumsRepository
import com.team3.vinilos.data.models.Album

class FakeNetworkAlbumsRepository: AlbumsRepository {
    override suspend fun getAlbums(): List<Album> {
        return FakeUiDataSource.getAlbums(100)
    }
}