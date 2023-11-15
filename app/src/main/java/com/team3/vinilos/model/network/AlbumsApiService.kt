package com.team3.vinilos.model.network

import com.team3.vinilos.model.models.Album
import retrofit2.http.GET

interface AlbumsApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>
}