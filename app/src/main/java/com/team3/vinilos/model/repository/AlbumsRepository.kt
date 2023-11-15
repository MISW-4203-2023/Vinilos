package com.team3.vinilos.model.repository

import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.network.AlbumsApiService

interface AlbumsRepository {
    suspend fun getAlbums(): List<Album>
}

class NetworkAlbumsRepository(
    private val albumsApiService: AlbumsApiService
) : AlbumsRepository {
    override suspend fun getAlbums(): List<Album> = albumsApiService.getAlbums()
}

