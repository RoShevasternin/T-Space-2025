package com.reinvestkapital.kapiterraw.game.manager

import com.badlogic.gdx.Gdx
import com.reinvestkapital.kapiterraw.game.LibGDXGame
import com.reinvestkapital.kapiterraw.game.screens.*
import com.reinvestkapital.kapiterraw.game.utils.advanced.AdvancedScreen
import com.reinvestkapital.kapiterraw.game.utils.runGDX

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
        SplashScreen    ::class.java.name -> SplashScreen(game)
        ListScreen      ::class.java.name -> ListScreen(game)
        MainScreen      ::class.java.name -> MainScreen(game)
        AllInvestScreen ::class.java.name -> AllInvestScreen(game)
        AddScreen       ::class.java.name -> AddScreen(game)

        else -> MainScreen(game)
    }

}