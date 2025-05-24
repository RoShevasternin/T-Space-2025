package com.busiknesik.pomeshnek.game.manager

import com.badlogic.gdx.Gdx
import com.busiknesik.pomeshnek.game.screens.HistoryScreen
import com.busiknesik.pomeshnek.game.screens.LoaderScreen
import com.busiknesik.pomeshnek.game.screens.MainScreen
import com.busiknesik.pomeshnek.game.screens.AddScreen
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.gdxGame
import com.busiknesik.pomeshnek.game.utils.runGDX

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
        LoaderScreen  ::class.java.name -> LoaderScreen()
        MainScreen    ::class.java.name -> MainScreen()
        HistoryScreen ::class.java.name -> HistoryScreen()
        AddScreen     ::class.java.name -> AddScreen()

        else -> MainScreen()
    }

}