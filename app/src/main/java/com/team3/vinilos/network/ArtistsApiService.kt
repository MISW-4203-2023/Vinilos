package com.team3.vinilos.network

import com.team3.vinilos.data.models.Artist
import retrofit2.http.GET

interface ArtistsApiService  {
    @GET("musicians")
    suspend fun getArtists(): List<Artist>
}