package com.traoya.yatirimya.game.manager

import com.badlogic.gdx.Gdx
import com.traoya.yatirimya.game.screens.DashboardScreen
import com.traoya.yatirimya.game.screens.GelyScreen
import com.traoya.yatirimya.game.screens.LoaderScreen
import com.traoya.yatirimya.game.screens.MenuScreen
import com.traoya.yatirimya.game.screens.TutorialScreen
import com.traoya.yatirimya.game.utils.advanced.AdvancedScreen
import com.traoya.yatirimya.game.utils.gdxGame
import com.traoya.yatirimya.game.utils.runGDX

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
        TutorialScreen ::class.java.name -> TutorialScreen()
        MenuScreen     ::class.java.name -> MenuScreen()
        GelyScreen     ::class.java.name -> GelyScreen()

        else -> MenuScreen()
    }

}