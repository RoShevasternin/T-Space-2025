package com.smarteca.foundsender.util

import com.smarteca.foundsender.game.utils.log
import com.smarteca.foundsender.util.permission.PermissionATT
import org.robovm.apple.apptrackingtransparency.ATTrackingManagerAuthorizationStatus
import org.robovm.apple.foundation.NSDictionary
import org.robovm.apple.foundation.NSError
import org.robovm.apple.foundation.NSString
import org.robovm.pods.appsflyer.AppsFlyerLib

object AppsFlyerManager {

    val appsFlyerLib by lazy { AppsFlyerLib.shared() }

    fun initialize(devKey: String, appId: String) {
        log("AppsFlyerManager: initialize | sdkVersion = ${appsFlyerLib.sdkVersion}")

        appsFlyerLib.appsFlyerDevKey = devKey
        appsFlyerLib.appleAppID      = appId
        appsFlyerLib.setIsDebug(true)
    }

    // startWithCompletionHandler() для прослуховування результату запуску AppsFlyer
    fun startWithCompletionHandler() {
        appsFlyerLib.start { result: NSDictionary<NSString, *>?, error: NSError? ->
            if (error == null) {
                log("✅ AppsFlyer: onSuccess - $result")
            } else {
                log("❌ AppsFlyer: onError ${error.code}, ${error.localizedDescription}")
            }
        }
    }

}
