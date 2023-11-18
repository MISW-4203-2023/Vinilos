package com.team3.vinilos.model.network

import com.team3.vinilos.model.models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumApiService {
    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") id: Long): Album
}