package com.ayrym.inperdader.game.manager

import com.ayrym.inperdader.game.screens.GameScreen
import com.badlogic.gdx.Gdx
import com.ayrym.inperdader.game.screens.LoaderScreen
import com.ayrym.inperdader.game.screens.MenuScreen
import com.ayrym.inperdader.game.screens.QuizScreen
import com.ayrym.inperdader.game.utils.advanced.AdvancedScreen
import com.ayrym.inperdader.game.utils.gdxGame
import com.ayrym.inperdader.game.utils.runGDX

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
        LoaderScreen ::class.java.name -> LoaderScreen()
        MenuScreen   ::class.java.name -> MenuScreen()
        QuizScreen   ::class.java.name -> QuizScreen()
        GameScreen   ::class.java.name -> GameScreen()

        else -> MenuScreen()
    }

}