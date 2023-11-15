package com.team3.vinilos.test.fake

import com.team3.vinilos.model.repository.ArtistsRepository
import com.team3.vinilos.model.models.Artist
import java.io.IOException

class FakeFailNetworkArtistRepository: ArtistsRepository {
    override suspend fun getArtists(): List<Artist> {
        throw IOException()
    }
}