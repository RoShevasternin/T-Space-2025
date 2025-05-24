package com.bagazkz.klarebadew.game.manager

import com.badlogic.gdx.Gdx
import com.bagazkz.klarebadew.game.screens.GreatingScreen
import com.bagazkz.klarebadew.game.screens.LoaderScreen
import com.bagazkz.klarebadew.game.screens.MagazScreen
import com.bagazkz.klarebadew.game.screens.MenuScreen
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedScreen
import com.bagazkz.klarebadew.game.utils.gdxGame
import com.bagazkz.klarebadew.game.utils.runGDX

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
        GreatingScreen::class.java.name -> GreatingScreen()
        MenuScreen    ::class.java.name -> MenuScreen()
        MagazScreen   ::class.java.name -> MagazScreen()

        else -> MenuScreen()
    }

}