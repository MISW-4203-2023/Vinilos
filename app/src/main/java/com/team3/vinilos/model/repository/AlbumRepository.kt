package com.team3.vinilos.model.repository

import android.util.Log
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.network.AlbumApiService

interface AlbumRepository {
    suspend fun getAlbum(id: Long): Album
}

class NetworkAlbumRepository(
    private  val albumApiService: AlbumApiService
) : AlbumRepository {
    override suspend fun getAlbum(id: Long): Album {
        Log.i("Repository", id.toString())
        return albumApiService.getAlbum(id)
    }

}