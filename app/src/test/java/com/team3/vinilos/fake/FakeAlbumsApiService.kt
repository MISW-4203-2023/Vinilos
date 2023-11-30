package com.team3.vinilos.fake

import com.team3.vinilos.model.faker
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.model.network.AlbumApiService
import com.team3.vinilos.model.network.AlbumsApiService

class FakeAlbumsApiService: AlbumApiService {

    override suspend fun getAlbum(id: Long): Album {
        var albumSelect: Album = FakeDataSource.listAlbum.firstOrNull() ?: throw NoSuchElementException("No se encontró ningún álbum")

        for (album in FakeDataSource.listAlbum) {
            if (album.id == id) {
                albumSelect = album
                break
            }
        }

        return albumSelect
    }
    override suspend fun addAlbum(album: CreateAlbum): Album {
        return Album(10, name = album.name.toString(), album.cover)
    }

}
