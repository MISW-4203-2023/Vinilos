package com.team3.vinilos.fake

import com.team3.vinilos.data.ArtistsRepository
import com.team3.vinilos.data.models.Artist

class FakeNetworkArtistsRepository : ArtistsRepository {
    override suspend fun getArtists(): List<Artist> {
        return  FakeDataSource.listArtist
    }
}