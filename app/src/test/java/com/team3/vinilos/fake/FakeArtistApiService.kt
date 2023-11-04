package com.team3.vinilos.fake

import com.team3.vinilos.data.models.Artist
import com.team3.vinilos.network.ArtistsApiService

class FakeArtistApiService :ArtistsApiService {
    override suspend fun getArtists(): List<Artist> {
        return FakeDataSource.listArtist
    }
}