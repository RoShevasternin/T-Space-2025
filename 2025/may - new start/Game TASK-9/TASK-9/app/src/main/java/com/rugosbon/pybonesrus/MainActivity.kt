package com.rugosbon.pybonesrus

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.rugosbon.pybonesrus.databinding.ActivityMainBinding
import com.rugosbon.pybonesrus.game.utils.gdxGame
import com.rugosbon.pybonesrus.util.OneTime
import com.rugosbon.pybonesrus.util.WebViewHelper
import com.rugosbon.pybonesrus.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        var statusBarHeight = 0
        var navBarHeight    = 0
    }

    private val onceExit  = OneTime()
    private val onceSystemBarHeight = OneTime()

    lateinit var binding      : ActivityMainBinding
    lateinit var webViewHelper: WebViewHelper

    val coroutine = CoroutineScope(Dispatchers.Main)

    val windowInsetsController by lazy { WindowCompat.getInsetsController(window, window.decorView) }

    /*val mapBlockKeyboardHeight = mutableMapOf<String, (Boolean, Int) -> Unit>()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initialize()

        //startKeyboardHeightListener()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            onceSystemBarHeight.use {
                statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                navBarHeight    = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

                // hide Status or Nav bar (після встановлення їх розмірів)
                windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
                windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            if (binding.webView.isVisible) {
                val imeBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                val navBottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
                val totalBottom = maxOf(imeBottom, navBottom)

                binding.root.setPadding(0, statusBarHeight, 0, totalBottom)
                log("ime = $imeBottom | navBar = $navBarHeight | total = $totalBottom")
            }

            WindowInsetsCompat.CONSUMED
        }
    }

    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch {
                finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webViewHelper = WebViewHelper(this)
    }

    // Logic -----------------------------------------------------------------------------------------

    fun hideWebView() {
        runOnUiThread {
            binding.webView.visibility = View.GONE
            binding.webView.loadUrl("about:blank")
            binding.root.setPadding(0, 0, 0, 0)

            binding.navHostFragment.requestFocus()
            gdxGame.resume()
        }
    }

    fun showWebView() {
        runOnUiThread {
            binding.root.setPadding(0, statusBarHeight, 0, 0)
            binding.webView.visibility = View.VISIBLE

            binding.webView.requestFocus()
            gdxGame.pause()
        }
    }

    // Keyboard ----------------------------------------------------------------------------------

    /*private fun startKeyboardHeightListener() = KeyboardHeightListener(binding.root) { isVisible, keyboardHeight ->
        log("Keyboard: isVisible = $isVisible | keyboardHeight = $keyboardHeight")

        if (binding.webView.isVisible.not()) {
            mapBlockKeyboardHeight.values.forEach { block -> block(isVisible, keyboardHeight) }
        }
    }.startListening()*/

}