package com.team3.vinilos.model.network

import com.team3.vinilos.model.models.Artist
import retrofit2.http.GET

interface ArtistsApiService  {
    @GET("musicians")
    suspend fun getArtists(): List<Artist>
}