package com.ogonechek.putinvestor.game.manager

import com.badlogic.gdx.Gdx
import com.ogonechek.putinvestor.game.screens.AllScreen
import com.ogonechek.putinvestor.game.screens.LoaderScreen
import com.ogonechek.putinvestor.game.screens.MenuScreen
import com.ogonechek.putinvestor.game.screens.OnboardScreen
import com.ogonechek.putinvestor.game.screens.PlusScreen
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.gdxGame
import com.ogonechek.putinvestor.game.utils.runGDX

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
        OnboardScreen    ::class.java.name -> OnboardScreen()
        AllScreen        ::class.java.name -> AllScreen()
        PlusScreen       ::class.java.name -> PlusScreen()

        else -> MenuScreen()
    }

}