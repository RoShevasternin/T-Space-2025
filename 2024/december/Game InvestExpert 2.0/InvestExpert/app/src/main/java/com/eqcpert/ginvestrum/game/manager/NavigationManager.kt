package com.eqcpert.ginvestrum.game.manager

import com.badlogic.gdx.Gdx
import com.eqcpert.ginvestrum.game.LibGDXGame
import com.eqcpert.ginvestrum.game.screens.*
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedScreen
import com.eqcpert.ginvestrum.game.utils.runGDX

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
        LoaderScreen  ::class.java.name -> LoaderScreen(game)
        Work_1_Screen ::class.java.name -> Work_1_Screen(game)
        Work_2_Screen ::class.java.name -> Work_2_Screen(game)
        Work_3_Screen ::class.java.name -> Work_3_Screen(game)
        Work_4_Screen ::class.java.name -> Work_4_Screen(game)

        else -> Work_1_Screen(game)
    }

}