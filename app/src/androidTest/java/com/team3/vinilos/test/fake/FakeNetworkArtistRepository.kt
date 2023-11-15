package com.team3.vinilos.test.fake

import com.team3.vinilos.model.repository.ArtistsRepository
import com.team3.vinilos.model.models.Artist

class FakeNetworkArtistRepository: ArtistsRepository {
    override suspend fun getArtists(): List<Artist> {
        return FakeUiDataSource.getArtist(100)
    }
}