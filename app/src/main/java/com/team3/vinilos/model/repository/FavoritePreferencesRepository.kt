package com.team3.vinilos.model.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.team3.proto.FavoritePreferences
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
}
