package com.axmeron.investnaveratep.game.manager

import com.badlogic.gdx.Gdx
import com.axmeron.investnaveratep.game.screens.LoaderScreen
import com.axmeron.investnaveratep.game.screens.MenuScreen
import com.axmeron.investnaveratep.game.screens.OnboardScreen
import com.axmeron.investnaveratep.game.screens.ResultScreen
import com.axmeron.investnaveratep.game.screens.StartTestScreen
import com.axmeron.investnaveratep.game.screens.TestScreen
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.gdxGame
import com.axmeron.investnaveratep.game.utils.runGDX

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
        OnboardScreen    ::class.java.name -> OnboardScreen()
        MenuScreen       ::class.java.name -> MenuScreen()
        StartTestScreen  ::class.java.name -> StartTestScreen()
        TestScreen       ::class.java.name -> TestScreen()
        ResultScreen     ::class.java.name -> ResultScreen()

        else -> OnboardScreen()
    }

}