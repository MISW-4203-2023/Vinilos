package com.team3.vinilos.test.fake

import com.team3.proto.FavoritePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class FakeFavoritePreferencesRepository(private val fakeDataStore: FavoritePreferencesDataStore) {
    val favoritePreferencesFlow: Flow<FavoritePreferences> = fakeDataStore.favoritePreferencesFlow

    suspend fun agregarArtistaFavorito(artistId: Int) {
        var artistFound = false

        fakeDataStore.favoritePreferencesFlow.firstOrNull()?.let { preferences ->
            val mutablePreferences = preferences.toBuilder()

            val artistIndex = preferences.artistsList.indexOfFirst { it.id == artistId }
            if (artistIndex != -1) {
                artistFound = true
                val artists = preferences.artistsList.toMutableList()
                artists.removeAt(artistIndex)
                mutablePreferences.clearArtists().addAllArtists(artists)
                fakeDataStore.updateData { preferences ->
                    preferences.mergeFrom(mutablePreferences.build())
                }
            }
        }

        if (!artistFound) {
            val newArtist = FavoritePreferences.Artist.newBuilder()
                .setId(artistId)
                .build()

            fakeDataStore.updateData { preferences ->
                val artists = preferences.artistsList.toMutableList()
                artists.add(newArtist)
                preferences.clearArtists().addAllArtists(artists)
            }
        }
    }

    suspend fun removerArtistaFavorito(artistId: Int){
        fakeDataStore.favoritePreferencesFlow.firstOrNull()?.let { preferences ->
            val artistIndex = preferences.artistsList.indexOfFirst { it.id == artistId }
            if (artistIndex != -1) {
                val artists = preferences.artistsList.toMutableList()
                artists.removeAt(artistIndex)
                val mutablePreferences =
                    preferences.toBuilder().clearArtists().addAllArtists(artists)
                fakeDataStore.updateData { preferences ->
                    preferences.mergeFrom(mutablePreferences.build())
                }
            }
        }
    }
}


