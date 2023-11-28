package com.team3.vinilos.model.repository

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import com.team3.proto.FavoritePreferences

class FavoritePreferencesRepository(
    private val favoritePreferencesStore: DataStore<FavoritePreferences>
){
    private companion object {
        const val TAG = "FavoritePreferencesRepo"
    }

    val favoritePreferencesFlow: Flow<FavoritePreferences> = favoritePreferencesStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(FavoritePreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun agregarArtistaFavorito(artistId: Int) {
        var artistFound = false

        favoritePreferencesStore.data.firstOrNull()?.let { preferences ->
            val artistIndex = preferences.artistsList.indexOfFirst { it.id == artistId }

            if (artistIndex != -1) {
                artistFound = true
                favoritePreferencesStore.updateData { preferences ->
                    preferences.toBuilder().removeArtists(artistIndex).build()
                }
                Log.i("Eliminar ArtistaIndex", "Artista encontrado en el índice: $artistIndex, ID: $artistId")
            }
        }

        if (!artistFound) {
            val newArtist = FavoritePreferences.Artist.newBuilder()
                .setId(artistId)
                .build()

            favoritePreferencesStore.updateData { preferences ->
                preferences.toBuilder().addArtists(newArtist).build()
            }
            Log.i("Agregar Artista", "ID: $artistId")
        }
    }
}
