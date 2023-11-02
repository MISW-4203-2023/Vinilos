package com.team3.vinilos

import android.app.Application
import com.team3.vinilos.data.AppContainer
import com.team3.vinilos.data.DefaultAppContainer

class VinilsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}