package com.gosinventarytet.debagovich.game.manager

import com.badlogic.gdx.Gdx
import com.gosinventarytet.debagovich.game.screens.LoaderScreen
import com.gosinventarytet.debagovich.game.screens.MenuScreen
import com.gosinventarytet.debagovich.game.screens.QuizScreen
import com.gosinventarytet.debagovich.game.screens.ResultScreen
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedScreen
import com.gosinventarytet.debagovich.game.utils.gdxGame
import com.gosinventarytet.debagovich.game.utils.runGDX

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
        ResultScreen ::class.java.name -> ResultScreen()

        else -> MenuScreen()
    }

}