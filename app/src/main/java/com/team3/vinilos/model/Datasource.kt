package com.team3.vinilos.model

import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.Collector
import io.github.serpro69.kfaker.Faker

val faker = Faker()
class Datasource {
    fun loadAlbums() : List<Album> {
        return listOf(
            Album(id = 1, name = faker.name.name()),
            Album(id = 1, name = faker.name.name()),
            Album(id = 1, name = faker.name.name()),
            Album(id = 1, name = faker.name.name()),
            Album(id = 1, name = faker.name.name()),
            Album(id = 1, name = faker.name.name()),
            Album(id = 1, name = faker.name.name()),
            Album(id = 1, name = faker.name.name()),
        )
    }
    fun loadCollectors() : List<Collector> {
        return listOf(
            Collector(id = 1, name = faker.name.name()),
            Collector(id = 1, name = faker.name.name()),
            Collector(id = 1, name = faker.name.name()),
            Collector(id = 1, name = faker.name.name()),
            Collector(id = 1, name = faker.name.name()),
            Collector(id = 1, name = faker.name.name()),
            Collector(id = 1, name = faker.name.name()),
            Collector(id = 1, name = faker.name.name()),
        )
    }
}