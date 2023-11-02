package com.team3.vinilos.data

import com.team3.vinilos.models.Album
import io.github.serpro69.kfaker.Faker

val faker = Faker()
class Datasource() {
    fun loadAlbums() : List<Album> {
        return listOf<Album> (
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
}