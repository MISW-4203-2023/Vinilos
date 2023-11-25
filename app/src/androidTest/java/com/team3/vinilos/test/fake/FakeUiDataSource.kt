package com.team3.vinilos.test.fake

import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.models.CollectorAlbums
import com.team3.vinilos.model.models.Comment
import io.github.serpro69.kfaker.Faker
import java.time.ZoneId
import java.util.Date
import kotlin.random.Random

class FakeUiDataSource {

    companion object {
        private var allAlbums: List<Album> = mutableListOf()
        private var allArtists: List<Artist> = mutableListOf()
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

        fun getArtists(count: Int): List<Artist> {
            val faker = Faker()
            if (allArtists.isEmpty()) {
                val artists = mutableListOf<Artist>()
                for (i in 1..count) {
                    val artist = Artist(
                        id = i.toLong(),
                        name = faker.name.name(),
                        birthDate = Date.from(
                                (faker.person.birthDate(30)).atStartOfDay(ZoneId.systemDefault())
                                    .toInstant()
                            ),
                        description = faker.lorem.supplemental(),
                        image = "https://picsum.photos/500/500/?random",
                        )
                    artists.add(artist)
                }
                allArtists = artists
        }
        return allArtists
        }
        fun getArtist(c: Int, id: Int): Artist {
            return getArtists(c)[id - 1]
        }

        fun getCollectors(count: Int): List<Collector> {
            val faker = Faker()
            val collectors = mutableListOf<Collector>()
            val artists = mutableListOf<Artist>()
            val comments = mutableListOf<Comment>()
            val albums = mutableListOf<Album>()
            val collectorAlbums = mutableListOf<CollectorAlbums>()

            val musicGenres = listOf(
                "Rock",
                "Pop",
                "Hip-hop",
                "R&B",
                "Jazz",
                "Reggae",
                "Metal",
            )

            val artist = Artist(
                id = faker.idNumber.hashCode().toLong(),
                name = faker.name.name(),
                birthDate = Date.from(
                    (faker.person.birthDate(30)).atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                ),
                description = faker.lorem.supplemental(),
                image = "https://picsum.photos/500/500/?random",
            )
            artists.add(artist)

            val random = Random.Default
            val randomGenre = musicGenres[random.nextInt(musicGenres.size)]
            val album =Album(
                id = faker.idNumber.hashCode().toLong(),
                name = faker.name.name(),
                genre = randomGenre,
                releaseDate = Date.from(
                    (faker.person.birthDate(30)).atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                )
            )
            albums.add(album)


            val collectorAlbum = CollectorAlbums(
                id = faker.idNumber.hashCode().toLong(),
                price = faker.idNumber.hashCode().toLong(),
                status = faker.name.name(),
                album = album
            )
            collectorAlbums.add(collectorAlbum)


            for (i in 1..count) {
                val collector = Collector(
                    id = i.toLong(),
                    name = faker.name.name(),
                    telephone = faker.phoneNumber.phoneNumber().toString(),
                    email =  faker.toString(),
                    comments = comments,
                    favoritePerformers = artists,
                    collectorAlbums = collectorAlbums
                )
                collectors.add(collector)
            }
            return collectors
        }
        fun getCollector(c: Int, id: Int): Collector {
            return getCollectors(c)[id - 1]
        }
    }
}
