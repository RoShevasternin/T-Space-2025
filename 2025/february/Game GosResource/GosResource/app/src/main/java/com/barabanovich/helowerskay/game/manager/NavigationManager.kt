package com.barabanovich.helowerskay.game.manager

import com.barabanovich.helowerskay.game.screens.GameScreen
import com.badlogic.gdx.Gdx
import com.barabanovich.helowerskay.game.screens.LoaderScreen
import com.barabanovich.helowerskay.game.screens.CategoryScreen
import com.barabanovich.helowerskay.game.screens.ResultScreen
import com.barabanovich.helowerskay.game.screens.StartScreen
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedScreen
import com.barabanovich.helowerskay.game.utils.gdxGame
import com.barabanovich.helowerskay.game.utils.runGDX

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
        CategoryScreen::class.java.name -> CategoryScreen()
        GameScreen    ::class.java.name -> GameScreen()
        ResultScreen  ::class.java.name -> ResultScreen()

        else -> CategoryScreen()
    }

}