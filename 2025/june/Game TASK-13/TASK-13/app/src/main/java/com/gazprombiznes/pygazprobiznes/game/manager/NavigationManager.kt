package com.gazprombiznes.pygazprobiznes.game.manager

import com.badlogic.gdx.Gdx
import com.gazprombiznes.pygazprobiznes.game.screens.DashboardScreen1
import com.gazprombiznes.pygazprobiznes.game.screens.DashboardScreen2
import com.gazprombiznes.pygazprobiznes.game.screens.LoaderScreen
import com.gazprombiznes.pygazprobiznes.game.screens.GameScreen
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedScreen
import com.gazprombiznes.pygazprobiznes.game.utils.gdxGame
import com.gazprombiznes.pygazprobiznes.game.utils.runGDX

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
        DashboardScreen1::class.java.name -> DashboardScreen1()
        DashboardScreen2::class.java.name -> DashboardScreen2()
        GameScreen      ::class.java.name -> GameScreen()

        else -> GameScreen()
    }

}