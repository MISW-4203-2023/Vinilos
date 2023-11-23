package com.team3.vinilos.model.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.team3.proto.FavoritePreferences
import com.team3.vinilos.model.models.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class FavoritePreferencesRepository(
    private val favoritePreferencesStore: DataStore<FavoritePreferences>
){
    private companion object {
        val FAVORITE_MODE = booleanPreferencesKey("show_completed")
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

    suspend fun updateFavorite(completed: Boolean) {
        favoritePreferencesStore.updateData { preferences ->
            preferences.toBuilder().setShowFavorites(completed).build()
        }
    }

    suspend fun getArtist(artistId: Long){
        /*favoritePreferencesStore.updateData { preferences ->
            preferences.toBuilder().getArtists(artistId.toInt())
        }*/
    }
    suspend fun agregarArtistaFavorito(artist: Artist){
        val newArtist = FavoritePreferences.Artist.newBuilder()
            .setId(artist.id.toInt())
            .setName(artist.name)
            .setImage(artist.image)
            .setDescription(artist.description)
            .setFavorite(true)
            .build()

        favoritePreferencesStore.updateData { preferences ->
            preferences.toBuilder().addArtists(artist.id.toInt(), newArtist).build()
        }
        val favoritePrint = favoritePreferencesFlow
        Log.i("Favorite Data: ",favoritePrint.toString())
    }

}
