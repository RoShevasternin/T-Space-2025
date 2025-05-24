package com.testapp.testapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel

class Applicationtest : Application() {
    override fun onCreate() {
        super.onCreate()
        val environment = AdjustConfig.ENVIRONMENT_PRODUCTION
        val config = AdjustConfig(this, "de7sdjuvxcsg", environment)
        Adjust.initSdk(config)
    }
}