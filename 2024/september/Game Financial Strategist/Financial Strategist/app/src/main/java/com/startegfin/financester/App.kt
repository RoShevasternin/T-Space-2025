package com.startegfin.financester

import android.app.Application
import android.content.Context
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig

lateinit var appContext: Context private set

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        val environment = AdjustConfig.ENVIRONMENT_PRODUCTION
        val config      = AdjustConfig(this, "3rl1lvhsf680", environment)
        Adjust.initSdk(config)
    }

}