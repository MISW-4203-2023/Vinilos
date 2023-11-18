package com.team3.vinilos.test.fake

import com.team3.vinilos.model.repository.ArtistRepository
import com.team3.vinilos.model.models.Artist

class FakeNetworkArtistRepository: ArtistRepository {

    override suspend fun getArtist(id: Long): Artist {
        return FakeUiDataSource.getArtist(100, id.toInt())
    }
}