package com.team3.vinilos.test.fake

import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.models.Collector
import io.github.serpro69.kfaker.Faker
import java.time.ZoneId
import java.util.Date
import kotlin.random.Random

class FakeUiDataSource {

    companion object {
        private var allAlbums: List<Album> = mutableListOf()
        fun getAlbums(count: Int): List<Album> {
            if (allAlbums.isEmpty()) {
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
                    val album = Album(
                        id = i.toLong(),
                        name = faker.name.name(),
                        genre = randomGenre,
                        releaseDate = Date.from(
                            (faker.person.birthDate(30)).atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                        ),
                        description = faker.lorem.supplemental(),
                        cover = "https://picsum.photos/500/500/?random",
                    )
                    albums.add(album)
                }
                allAlbums = albums
            }
            return allAlbums
        }

        fun getAlbum(c: Int, id: Int): Album {
            return getAlbums(c)[id - 1]
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

        fun getCollector(count: Int): List<Collector> {
            val faker = Faker()
            val collectors = mutableListOf<Collector>()
            for (i in 1..count) {
                val collector = Collector(id = i.toLong(), name = faker.name.name())
                collectors.add(collector)
            }
            return collectors
        }
    }
}
