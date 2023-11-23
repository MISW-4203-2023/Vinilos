package com.team3.vinilos.model.network

import com.team3.vinilos.model.models.Album
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumApiService {
    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") id: Long): Album

    @POST("albums")
    suspend fun  addAlbum(@Body album: Album): Album
}