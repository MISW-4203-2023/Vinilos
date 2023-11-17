package com.team3.vinilos.model

import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.Artist
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
    fun getArtist(): Artist {
        return Artist(id = 1, name = faker.name.name(),image = "https://cdn.shopify.com/s/files/1/0275/3095/products/image_4931268b-7acf-4702-9c55-b2b3a03ed999_1024x1024.jpg",
            description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York."
        )
    }
}