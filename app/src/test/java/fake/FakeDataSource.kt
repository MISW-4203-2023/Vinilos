package fake

import com.team3.vinilos.data.faker
import com.team3.vinilos.data.models.Artist

object FakeDataSource {
    val listArtist = listOf (
        Artist (id = 1, name = faker.name.name()),
        Artist (id = 2, name = faker.name.name()),
        Artist (id = 3, name = faker.name.name()),
        Artist (id = 4, name = faker.name.name()),
        Artist (id = 5, name = faker.name.name())

    )
}