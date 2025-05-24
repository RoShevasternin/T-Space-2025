@file:JvmName("IOSLauncher")

package com.smarteca.foundsender

import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import com.badlogic.gdx.graphics.glutils.HdpiMode
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication
import org.robovm.apple.uikit.UIApplicationLaunchOptions
import org.robovm.apple.uikit.UIScreen
import kotlin.math.roundToInt

/** Launches the iOS (RoboVM) application. */
class IOSLauncher : IOSApplication.Delegate() {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val pool = NSAutoreleasePool()
            val principalClass: Class<UIApplication>? = null
            val delegateClass = IOSLauncher::class.java
            UIApplication.main(args, principalClass, delegateClass)
            pool.close()
        }
    }


    override fun createApplication(): IOSApplication {
        return IOSApplication(GdxGame(this), IOSApplicationConfiguration().apply {
            // Configure your application here.
            useAccelerometer = false
            useCompass       = false
            hdpiMode         = HdpiMode.Pixels

            statusBarVisible = true
        })
    }

    override fun didFinishLaunching(application: UIApplication?, launchOptions: UIApplicationLaunchOptions?): Boolean {
        val result = super.didFinishLaunching(application, launchOptions)
        return result
    }

    // Logic ---------------------------------------------------------------

    fun getStatusBarHeightInPixels(): Int {
        val window = UIApplication.getSharedApplication().windows.firstOrNull() ?: return 0
        val insets = window.safeAreaInsets
        val statusBarHeight = if (insets.top > 0) insets.top else UIApplication.getSharedApplication().statusBarFrame.size.height
        return (statusBarHeight * UIScreen.getMainScreen().scale).roundToInt() // Конвертація у пікселі
    }

//    private var a = 0
//    fun artur() {
//        CoroutineScope(IOSDispatcher).launch {
//            while (isActive) {
//                log("test work: $a")
//                a++
//                delay(2000)
//            }
//        }
//    }

}
