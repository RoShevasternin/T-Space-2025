package com.sberigatelny.finexpertaizer.game.manager

import com.sberigatelny.finexpertaizer.game.screens.GameScreen
import com.badlogic.gdx.Gdx
import com.sberigatelny.finexpertaizer.game.screens.LoaderScreen
import com.sberigatelny.finexpertaizer.game.screens.StartScreen
import com.sberigatelny.finexpertaizer.game.screens.WorkScreen
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedScreen
import com.sberigatelny.finexpertaizer.game.utils.gdxGame
import com.sberigatelny.finexpertaizer.game.utils.runGDX

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

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen  ::class.java.name -> LoaderScreen()
        StartScreen   ::class.java.name -> StartScreen()
        GameScreen    ::class.java.name -> GameScreen()
        WorkScreen    ::class.java.name -> WorkScreen()

        else -> StartScreen()
    }

}