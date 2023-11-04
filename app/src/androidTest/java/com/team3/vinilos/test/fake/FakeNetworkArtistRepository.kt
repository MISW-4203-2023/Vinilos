package com.team3.vinilos.test.fake

import com.team3.vinilos.data.ArtistsRepository
import com.team3.vinilos.data.models.Artist

class FakeNetworkArtistRepository: ArtistsRepository {
    override suspend fun getArtists(): List<Artist> {
        return FakeUiDataSource.getArtist(100)
    }
}