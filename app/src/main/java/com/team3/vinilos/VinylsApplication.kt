package com.team3.vinilos

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.util.*
import com.team3.vinilos.model.network.AppContainer
import com.team3.vinilos.model.network.DefaultAppContainer
import com.team3.vinilos.model.repository.UserPreferencesRepository

private const val DARK_MODE_PREFERENCE_NAME = "dark_mode_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DARK_MODE_PREFERENCE_NAME
)
class VinylsApplication : Application() {
    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        setLocale(this, Locale("es", "CO"))
        userPreferencesRepository = UserPreferencesRepository(dataStore)
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