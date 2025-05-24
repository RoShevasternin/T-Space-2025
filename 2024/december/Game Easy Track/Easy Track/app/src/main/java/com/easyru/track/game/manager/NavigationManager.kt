package com.easyru.track.game.manager

import com.badlogic.gdx.Gdx
import com.easyru.track.game.screens.LoaderScreen
import com.easyru.track.game.screens.MenuScreen
import com.easyru.track.game.screens.OnboardScreen
import com.easyru.track.game.screens.ResultScreen
import com.easyru.track.game.screens.StartTestScreen
import com.easyru.track.game.screens.TestScreen
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.gdxGame
import com.easyru.track.game.utils.runGDX

class NavigationManager {

    private val backStack = mutableListOf<String>()

    fun navigate(toScreenName: String, fromScreenName: String? = null) = runGDX {

        gdxGame.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back() = runGDX {
        if (isBackStackEmpty()) exit() else gdxGame.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }


    fun exit() = runGDX { Gdx.app.exit() }

    fun isBackStackEmpty() = backStack.isEmpty()

    fun clearBackStack() = backStack.clear()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen     ::class.java.name -> LoaderScreen()
        OnboardScreen    ::class.java.name -> OnboardScreen()
        MenuScreen       ::class.java.name -> MenuScreen()
        StartTestScreen  ::class.java.name -> StartTestScreen()
        TestScreen       ::class.java.name -> TestScreen()
        ResultScreen     ::class.java.name -> ResultScreen()

        else -> OnboardScreen()
    }

}