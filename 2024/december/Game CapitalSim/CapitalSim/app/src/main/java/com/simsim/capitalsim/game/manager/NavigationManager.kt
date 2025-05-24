package com.simsim.capitalsim.game.manager

import com.badlogic.gdx.Gdx
import com.simsim.capitalsim.game.GDXGame
import com.simsim.capitalsim.game.screens.*
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.gdxGame
import com.simsim.capitalsim.game.utils.runGDX

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
        LoaderScreen ::class.java.name -> LoaderScreen()
        MenuScreen   ::class.java.name -> MenuScreen()
        SimScreen    ::class.java.name -> SimScreen()
        ResultScreen ::class.java.name -> ResultScreen()
        OnboardingScreen ::class.java.name -> OnboardingScreen()

        else -> MenuScreen()
    }

}