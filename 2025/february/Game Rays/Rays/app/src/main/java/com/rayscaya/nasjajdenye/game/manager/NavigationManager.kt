package com.rayscaya.nasjajdenye.game.manager

import com.badlogic.gdx.Gdx
import com.rayscaya.nasjajdenye.game.screens.EditScreen
import com.rayscaya.nasjajdenye.game.screens.LoaderScreen
import com.rayscaya.nasjajdenye.game.screens.MenuScreen
import com.rayscaya.nasjajdenye.game.screens.TutorialScreen
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedScreen
import com.rayscaya.nasjajdenye.game.utils.gdxGame
import com.rayscaya.nasjajdenye.game.utils.runGDX

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
        MenuScreen    ::class.java.name -> MenuScreen()
        TutorialScreen::class.java.name -> TutorialScreen()
        EditScreen    ::class.java.name -> EditScreen()

        else -> MenuScreen()
    }

}