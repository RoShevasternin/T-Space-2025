package com.sukapital.saepital.game.manager

import com.badlogic.gdx.Gdx
import com.sukapital.saepital.game.LibGDXGame
import com.sukapital.saepital.game.screens.*
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null) = runGDX {
        this.key = key

        game.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back(key: Int? = null) = runGDX {
        this.key = key

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }

    fun clearBackStack() {
        backStack.clear()
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when (name) {
        LoaderScreen    ::class.java.name -> LoaderScreen(game)
        MainScreen      ::class.java.name -> MainScreen(game)
        BlogScreen      ::class.java.name -> BlogScreen(game)
        InfoScreen      ::class.java.name -> InfoScreen(game)
        HistoryScreen ::class.java.name -> HistoryScreen(game)

        else -> MainScreen(game)
    }

}