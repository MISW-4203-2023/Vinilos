package com.team3.vinilos.model.network

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.team3.vinilos.model.repository.AlbumsRepository
import com.team3.vinilos.model.repository.ArtistsRepository
import com.team3.vinilos.model.repository.CollectorsRepository
import com.team3.vinilos.model.repository.NetworkAlbumsRepository
import com.team3.vinilos.model.repository.NetworkArtistsRepository
import com.team3.vinilos.model.repository.NetworkCollectorsRepository
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val albumsRepository: AlbumsRepository
    val artistsRepository: ArtistsRepository
    val collectorsRepository: CollectorsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://backend-vinyls-e1474335da3a.herokuapp.com/"

    private val jsonConverter = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(jsonConverter.asConverterFactory("application/json".toMediaType()))
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

    //Collectors
    private val collectorsRetrofitService: CollectorsApiService by lazy{
        retrofit.create(CollectorsApiService::class.java)
    }

    override val collectorsRepository: CollectorsRepository by lazy {
        NetworkCollectorsRepository(collectorsRetrofitService)
    }
}
