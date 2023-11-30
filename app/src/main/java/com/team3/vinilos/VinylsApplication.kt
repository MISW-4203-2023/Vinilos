package com.team3.vinilos

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.team3.proto.FavoritePreferences
import java.util.*
import com.team3.vinilos.model.network.AppContainer
import com.team3.vinilos.model.network.DefaultAppContainer
import com.team3.vinilos.model.repository.FavoritePreferencesRepository
import com.team3.vinilos.model.repository.UserPreferencesRepository
import com.team3.vinilos.model.serializer.FavoriteSeralizer

private const val DARK_MODE_PREFERENCE_NAME = "dark_mode_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DARK_MODE_PREFERENCE_NAME
)

private const val FAVORITE_PREFERENCES_NAME = "favorite_preferences"
private const val DATA_STORE_FILE_NAME = "favorites_store.pb"

private val Context.favoritePreferencesStore: DataStore<FavoritePreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = FavoriteSeralizer
)

class VinylsApplication : Application() {
    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository
    lateinit var favoritePreferencesRepository: FavoritePreferencesRepository
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        setLocale(this, Locale("es", "CO"))
        userPreferencesRepository = UserPreferencesRepository(dataStore)
        favoritePreferencesRepository = FavoritePreferencesRepository(favoritePreferencesStore)
    }

    private fun setLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
        } else {
            configuration.locale = locale
        }

        context.createConfigurationContext(configuration)
    }
}