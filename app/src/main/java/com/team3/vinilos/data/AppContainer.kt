package com.team3.vinilos.data

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.team3.vinilos.network.AlbumsApiService
import com.team3.vinilos.network.ArtistsApiService
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val albumsRepository: AlbumsRepository
    val artistsRepository: ArtistsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://192.168.1.3:3000/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: AlbumsApiService by lazy {
        retrofit.create(AlbumsApiService::class.java)
    }


    /**
     * DI implementation for Mars photos repository
     */
    override val albumsRepository: AlbumsRepository by lazy {
        NetworkAlbumsRepository(retrofitService)
    }

    //Artist
    private val retrofitArtistService: ArtistsApiService by lazy{
        retrofit.create(ArtistsApiService::class.java)
    }

    override val artistsRepository: ArtistsRepository by lazy {
        NetworkArtistsRepository(retrofitArtistService)
    }
}
