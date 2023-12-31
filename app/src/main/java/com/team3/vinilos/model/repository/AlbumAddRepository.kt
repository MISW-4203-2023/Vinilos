package com.team3.vinilos.model.repository

import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.model.network.AlbumApiService

interface AlbumAddRepository {
    suspend fun addAlbum(album: CreateAlbum): Album
}

class NetworkAlbumAddRepository(
    private val albumApiService: AlbumApiService
) : AlbumAddRepository {

    override suspend fun addAlbum(album: CreateAlbum): Album {
        return albumApiService.addAlbum(album)
    }
}