package com.startegfin.financester.game.manager

import com.badlogic.gdx.Gdx
import com.startegfin.financester.game.LibGDXGame
import com.startegfin.financester.game.screens.*
import com.startegfin.financester.game.utils.advanced.AdvancedScreen
import com.startegfin.financester.game.utils.runGDX

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
        SplashScreen  ::class.java.name -> SplashScreen(game)
        MenuScreen    ::class.java.name -> MenuScreen(game)
        RoshodScreen  ::class.java.name -> RoshodScreen(game)
        DohodScreen   ::class.java.name -> DohodScreen(game)
        AllTransScreen::class.java.name -> AllTransScreen(game)
        AddRoshodScreen ::class.java.name -> AddRoshodScreen(game)
        AddDohodScreen  ::class.java.name -> AddDohodScreen(game)

        else -> MenuScreen(game)
    }

}