package com.team3.vinilos

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.*
import com.team3.vinilos.model.network.AppContainer
import com.team3.vinilos.model.network.DefaultAppContainer

class VinylsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        setLocale(this, Locale("es", "CO"))
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