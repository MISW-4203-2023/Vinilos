package com.team3.vinilos.data

import com.team3.vinilos.data.models.Artist
import com.team3.vinilos.network.ArtistsApiService

interface ArtistsRepository {
    suspend fun getArtists(): List<Artist>
}

class NetworkArtistsRepository(
    private  val artistsApiService: ArtistsApiService
) : ArtistsRepository {
    override suspend fun getArtists(): List<Artist>  = artistsApiService.getArtists()
}