package com.team3.vinilos.model.repository

import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.network.ArtistsApiService

interface ArtistsRepository {
    suspend fun getArtists(): List<Artist>
}

class NetworkArtistsRepository(
    private  val artistsApiService: ArtistsApiService
) : ArtistsRepository {
    override suspend fun getArtists(): List<Artist>  = artistsApiService.getArtists()
}