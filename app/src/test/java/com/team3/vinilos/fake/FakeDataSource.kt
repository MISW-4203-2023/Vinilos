package com.team3.vinilos.fake

import com.team3.vinilos.data.faker
import com.team3.vinilos.data.models.Album
import com.team3.vinilos.data.models.Artist

object FakeDataSource {
    val listArtist = listOf (
        Artist (id = 1, name = faker.name.name()),
        Artist (id = 2, name = faker.name.name()),
        Artist (id = 3, name = faker.name.name()),
        Artist (id = 4, name = faker.name.name()),
        Artist (id = 5, name = faker.name.name())
    )

    val listAlbum = listOf (
        Album (id = 1, name = faker.name.name()),
        Album (id = 2, name = faker.name.name()),
        Album (id = 3, name = faker.name.name()),
        Album (id = 4, name = faker.name.name()),
        Album (id = 5, name = faker.name.name())
    )
}