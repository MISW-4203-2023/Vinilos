package com.team3.vinilos.model.repository

import android.util.Log
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.network.ArtistApiService

interface ArtistRepository {
    suspend fun getArtist(id: Long): Artist
}

class NetworkArtistRepository(
    private  val artistApiService: ArtistApiService
) : ArtistRepository {
    override suspend fun getArtist(id: Long): Artist  {
        Log.i("Repository", id.toString())
        return artistApiService.getArtist(id)
    }

}