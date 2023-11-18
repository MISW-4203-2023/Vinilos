package com.team3.vinilos.fake

import com.team3.vinilos.model.faker
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.models.Collector

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

    val listCollector = listOf (
        Collector (id = 1, name = faker.name.name()),
        Collector (id = 2, name = faker.name.name()),
        Collector (id = 3, name = faker.name.name()),
        Collector (id = 4, name = faker.name.name()),
        Collector (id = 5, name = faker.name.name())
    )
}