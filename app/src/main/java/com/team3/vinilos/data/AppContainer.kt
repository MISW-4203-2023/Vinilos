package com.team3.vinilos.data

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.team3.vinilos.network.AlbumsApiService
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val albumsRepository: AlbumsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://192.168.0.7:3000/"

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
}
