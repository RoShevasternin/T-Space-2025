package com.sburbanaizer.verginiafalesska.game.manager

import com.badlogic.gdx.Gdx
import com.sburbanaizer.verginiafalesska.game.screens.ImproveScreen
import com.sburbanaizer.verginiafalesska.game.screens.InvestScreen
import com.sburbanaizer.verginiafalesska.game.screens.LoaderScreen
import com.sburbanaizer.verginiafalesska.game.screens.MenuScreen
import com.sburbanaizer.verginiafalesska.game.screens.ProdajaScreen
import com.sburbanaizer.verginiafalesska.game.screens.TutorialScreen
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedScreen
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame
import com.sburbanaizer.verginiafalesska.game.utils.runGDX

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
        TutorialScreen   ::class.java.name -> TutorialScreen()
        InvestScreen     ::class.java.name -> InvestScreen()
        ImproveScreen    ::class.java.name -> ImproveScreen()
        ProdajaScreen    ::class.java.name -> ProdajaScreen()

        else -> MenuScreen()
    }

}