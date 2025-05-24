package com.smarters.moneyes.game.manager

import com.badlogic.gdx.Gdx
import com.smarters.moneyes.game.screens.LoaderScreen
import com.smarters.moneyes.game.screens.MenuScreen
import com.smarters.moneyes.game.screens.QuestionScreen
import com.smarters.moneyes.game.screens.ResultScreen
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.gdxGame
import com.smarters.moneyes.game.utils.runGDX

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
        QuestionScreen   ::class.java.name -> QuestionScreen()
        ResultScreen     ::class.java.name -> ResultScreen()

        else -> MenuScreen()
    }

}