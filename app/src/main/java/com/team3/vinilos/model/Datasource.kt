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
    fun loadCollectors(count: Int) : List<Collector> {
        val collectors = mutableListOf<Collector>()
        for (i in 1 .. count) {
            val collector = Collector(id = i.toLong(), name = faker.name.name())
            collectors.add(collector)
        }
        return collectors
    }
}