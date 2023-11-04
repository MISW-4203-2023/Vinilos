package com.team3.vinilos.test.fake

import com.team3.vinilos.data.models.Album
import com.team3.vinilos.data.models.Artist
import io.github.serpro69.kfaker.Faker
import kotlin.random.Random

class FakeUiDataSource {

    companion object {
        fun getAlbums(count: Int): List<Album> {
            val faker = Faker()
            val musicGenres = listOf(
                "Rock",
                "Pop",
                "Hip-hop",
                "R&B",
                "Jazz",
                "Reggae",
                "Metal",
            )

            val albums = mutableListOf<Album>()
            val random = Random.Default
            for (i in 1..count) {
                val randomGenre = musicGenres[random.nextInt(musicGenres.size)]
                val album = Album(id = i.toLong(), name = faker.name.name(), genre = randomGenre)
                albums.add(album)
            }
            return albums
        }


        fun getArtist(count: Int): List<Artist> {
            val faker = Faker()
            val artists = mutableListOf<Artist>()
            for (i in 1..count) {
                val artist = Artist(id = i.toLong(), name = faker.name.name())
                artists.add(artist)
            }
            return artists
        }
    }
}