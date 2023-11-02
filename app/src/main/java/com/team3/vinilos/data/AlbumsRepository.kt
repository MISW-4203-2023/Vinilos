package com.team3.vinilos.data

import com.team3.vinilos.data.models.Album
import com.team3.vinilos.network.AlbumsApiService

interface AlbumsRepository {
    suspend fun getAlbums(): List<Album>
}

class NetworkAlbumsRepository(
    private val albumsApiService: AlbumsApiService
) : AlbumsRepository {
    override suspend fun getAlbums(): List<Album> = albumsApiService.getAlbums()
}

