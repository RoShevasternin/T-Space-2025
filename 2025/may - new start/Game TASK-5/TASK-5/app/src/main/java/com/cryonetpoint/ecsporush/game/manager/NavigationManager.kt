package com.cryonetpoint.ecsporush.game.manager

import com.badlogic.gdx.Gdx
import com.cryonetpoint.ecsporush.game.screens.DashboardScreen
import com.cryonetpoint.ecsporush.game.screens.LoaderScreen
import com.cryonetpoint.ecsporush.game.screens.MenuScreen
import com.cryonetpoint.ecsporush.game.screens.QuizScreen
import com.cryonetpoint.ecsporush.game.screens.ResultScreen
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedScreen
import com.cryonetpoint.ecsporush.game.utils.gdxGame
import com.cryonetpoint.ecsporush.game.utils.runGDX

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
        LoaderScreen   ::class.java.name -> LoaderScreen()
        DashboardScreen::class.java.name -> DashboardScreen()
        MenuScreen     ::class.java.name -> MenuScreen()
        QuizScreen     ::class.java.name -> QuizScreen()
        ResultScreen   ::class.java.name -> ResultScreen()

        else -> DashboardScreen()
    }

}