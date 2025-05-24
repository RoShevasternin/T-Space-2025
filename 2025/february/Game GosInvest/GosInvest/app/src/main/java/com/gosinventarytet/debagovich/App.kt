package com.gosinventarytet.debagovich

import android.app.Application
import android.content.Context

lateinit var appContext: Context private set

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}