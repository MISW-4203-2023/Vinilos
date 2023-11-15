package com.team3.vinilos

import android.app.Application
import com.team3.vinilos.model.network.AppContainer
import com.team3.vinilos.model.network.DefaultAppContainer

class VinylsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}