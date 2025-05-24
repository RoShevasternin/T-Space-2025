package com.pulser.purlembager.game.manager

import com.badlogic.gdx.Gdx
import com.pulser.purlembager.game.screens.AddScreen
import com.pulser.purlembager.game.screens.BlogScreen
import com.pulser.purlembager.game.screens.LoaderScreen
import com.pulser.purlembager.game.screens.MenuScreen
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame
import com.pulser.purlembager.game.utils.runGDX

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
        MenuScreen       ::class.java.name -> MenuScreen()
        AddScreen        ::class.java.name -> AddScreen()
        BlogScreen       ::class.java.name -> BlogScreen()

        else -> MenuScreen()
    }

}