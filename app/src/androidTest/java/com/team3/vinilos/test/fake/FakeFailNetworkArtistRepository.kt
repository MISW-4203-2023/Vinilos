package com.team3.vinilos.test.fake

import com.team3.vinilos.data.AlbumsRepository
import com.team3.vinilos.data.ArtistsRepository
import com.team3.vinilos.data.models.Album
import com.team3.vinilos.data.models.Artist
import java.io.IOException

class FakeFailNetworkArtistRepository: ArtistsRepository {
    override suspend fun getArtists(): List<Artist> {
        throw IOException()
    }
}