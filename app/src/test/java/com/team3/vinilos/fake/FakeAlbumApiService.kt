package com.team3.vinilos.fake

import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.network.AlbumsApiService

class FakeAlbumApiService: AlbumsApiService {
    override suspend fun getAlbums(): List<Album> {
        return FakeDataSource.listAlbum
    }
}