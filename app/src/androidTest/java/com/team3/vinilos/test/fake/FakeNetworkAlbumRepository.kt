package com.team3.vinilos.test.fake

import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.repository.AlbumRepository

class FakeNetworkAlbumRepository : AlbumRepository {
    override suspend fun getAlbum(id: Long): Album {
        return FakeUiDataSource.getAlbum(100, id.toInt())
    }
}