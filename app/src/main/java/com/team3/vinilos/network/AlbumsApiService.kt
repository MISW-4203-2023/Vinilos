package com.team3.vinilos.network

import com.team3.vinilos.data.models.Album
import retrofit2.http.GET

interface AlbumsApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>
}