package com.cinvestor.crotcevni.game.manager

import com.badlogic.gdx.Gdx
import com.cinvestor.crotcevni.game.screens.ImproveScreen
import com.cinvestor.crotcevni.game.screens.InvestScreen
import com.cinvestor.crotcevni.game.screens.LoaderScreen
import com.cinvestor.crotcevni.game.screens.MenuScreen
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedScreen
import com.cinvestor.crotcevni.game.utils.gdxGame
import com.cinvestor.crotcevni.game.utils.runGDX

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
        ImproveScreen::class.java.name -> ImproveScreen()
        InvestScreen ::class.java.name -> InvestScreen()

        else -> MenuScreen()
    }

}