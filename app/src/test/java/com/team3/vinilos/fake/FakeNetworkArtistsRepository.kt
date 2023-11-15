package com.team3.vinilos.fake

import com.team3.vinilos.model.repository.ArtistsRepository
import com.team3.vinilos.model.models.Artist

class FakeNetworkArtistsRepository : ArtistsRepository {
    override suspend fun getArtists(): List<Artist> {
        return  FakeDataSource.listArtist
    }
}