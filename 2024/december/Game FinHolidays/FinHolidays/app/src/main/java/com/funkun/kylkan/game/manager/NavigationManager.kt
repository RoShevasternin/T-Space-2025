package com.funkun.kylkan.game.manager

import com.badlogic.gdx.Gdx
import com.funkun.kylkan.game.screens.AddSpendScreen
import com.funkun.kylkan.game.screens.AddTripsScreen
import com.funkun.kylkan.game.screens.HistoryScreen
import com.funkun.kylkan.game.screens.LoaderScreen
import com.funkun.kylkan.game.screens.MainScreen
import com.funkun.kylkan.game.screens.TripsOpenedScreen
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.gdxGame
import com.funkun.kylkan.game.utils.runGDX

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
        MainScreen       ::class.java.name -> MainScreen()
        AddTripsScreen   ::class.java.name -> AddTripsScreen()
        TripsOpenedScreen::class.java.name -> TripsOpenedScreen()
        AddSpendScreen   ::class.java.name -> AddSpendScreen()
        HistoryScreen    ::class.java.name -> HistoryScreen()

        else -> MainScreen()
    }

}