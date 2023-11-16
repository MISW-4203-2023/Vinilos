package com.team3.vinilos.model.network

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.team3.vinilos.model.repository.AlbumRepository
import com.team3.vinilos.model.repository.AlbumsRepository
import com.team3.vinilos.model.repository.ArtistRepository
import com.team3.vinilos.model.repository.ArtistsRepository
import com.team3.vinilos.model.repository.NetworkAlbumRepository
import com.team3.vinilos.model.repository.NetworkAlbumsRepository
import com.team3.vinilos.model.repository.NetworkArtistRepository
import com.team3.vinilos.model.repository.NetworkArtistsRepository
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val albumsRepository: AlbumsRepository
    val artistsRepository: ArtistsRepository
    val artistRepository: ArtistRepository
    val albumRepository: AlbumRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://backend-vinyls-e1474335da3a.herokuapp.com/"

    private val jsonConverter = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(jsonConverter.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    //Album
    private val albumsRetrofitService: AlbumsApiService by lazy {
        retrofit.create(AlbumsApiService::class.java)
    }

    override val albumsRepository: AlbumsRepository by lazy {
        NetworkAlbumsRepository(albumsRetrofitService)
    }

    //Artist
    private val artistsRetrofitService: ArtistsApiService by lazy{
        retrofit.create(ArtistsApiService::class.java)
    }

    override val artistsRepository: ArtistsRepository by lazy {
        NetworkArtistsRepository(artistsRetrofitService)
    }

    //Artist
    private val artistRetrofitService: ArtistApiService by lazy{
        retrofit.create(ArtistApiService::class.java)
    }

    override val artistRepository: ArtistRepository by lazy {
        NetworkArtistRepository(artistRetrofitService)
    }

    private val albumRetrofitService: AlbumApiService by lazy{
        retrofit.create(AlbumApiService::class.java)
    }

    override val albumRepository: AlbumRepository by lazy {
        NetworkAlbumRepository(albumRetrofitService)
    }
}
