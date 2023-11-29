package com.team3.vinilos.test.fake

import com.team3.proto.FavoritePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface FavoritePreferencesDataStore {
    val favoritePreferencesFlow: Flow<FavoritePreferences>
    suspend fun updateData(update: suspend (FavoritePreferences.Builder) -> Unit)
}

class FakeFavoritePreferencesDataStore(private val initialData: FavoritePreferences) : FavoritePreferencesDataStore {
    private val mutableFlow = MutableStateFlow(initialData)

    override val favoritePreferencesFlow: Flow<FavoritePreferences> = mutableFlow

    override suspend fun updateData(update: suspend (FavoritePreferences.Builder) -> Unit) {
        val currentData = mutableFlow.value.toBuilder()
        update(currentData)
        mutableFlow.value = currentData.build()
    }
}