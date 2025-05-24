package com.baleru.gamanecpidec.game.manager

import com.badlogic.gdx.Gdx
import com.baleru.gamanecpidec.game.screens.LoaderScreen
import com.baleru.gamanecpidec.game.screens.MenuScreen
import com.baleru.gamanecpidec.game.screens.AddScreen
import com.baleru.gamanecpidec.game.screens.EditScreen
import com.baleru.gamanecpidec.game.screens.HistoryScreen
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedScreen
import com.baleru.gamanecpidec.game.utils.gdxGame
import com.baleru.gamanecpidec.game.utils.runGDX

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
        AddScreen       ::class.java.name -> AddScreen()
        HistoryScreen   ::class.java.name -> HistoryScreen()
        EditScreen      ::class.java.name -> EditScreen()

        else -> MenuScreen()
    }

}