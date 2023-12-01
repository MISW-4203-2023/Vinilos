package com.team3.vinilos.test.fake

import com.team3.vinilos.model.faker
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.model.repository.AlbumAddRepository

class FakeNetworkAlbumAddRepository : AlbumAddRepository {
    override suspend fun addAlbum(album: CreateAlbum): Album {
        return Album(1, name = faker.name.name())
    }
}