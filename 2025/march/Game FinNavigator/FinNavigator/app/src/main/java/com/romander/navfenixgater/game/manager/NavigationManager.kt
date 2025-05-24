package com.romander.navfenixgater.game.manager

import com.badlogic.gdx.Gdx
import com.romander.navfenixgater.game.screens.CalculatorScreen
import com.romander.navfenixgater.game.screens.HistoryScreen
import com.romander.navfenixgater.game.screens.LoaderScreen
import com.romander.navfenixgater.game.screens.MenuScreen
import com.romander.navfenixgater.game.screens.ResultScreen
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.gdxGame
import com.romander.navfenixgater.game.utils.runGDX

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
        LoaderScreen    ::class.java.name -> LoaderScreen()
        MenuScreen      ::class.java.name -> MenuScreen()
        HistoryScreen   ::class.java.name -> HistoryScreen()
        CalculatorScreen::class.java.name -> CalculatorScreen()
        ResultScreen    ::class.java.name -> ResultScreen()

        else -> MenuScreen()
    }

}