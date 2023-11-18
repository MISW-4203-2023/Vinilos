package com.team3.vinilos.model.network

import com.team3.vinilos.model.models.Artist
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistApiService  {
    @GET("musicians/{id}")
    suspend fun getArtist(@Path("id") id: Long): Artist
}