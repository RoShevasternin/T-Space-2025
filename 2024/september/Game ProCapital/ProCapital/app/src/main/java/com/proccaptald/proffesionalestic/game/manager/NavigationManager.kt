package com.proccaptald.proffesionalestic.game.manager

import com.badlogic.gdx.Gdx
import com.proccaptald.proffesionalestic.game.LibGDXGame
import com.proccaptald.proffesionalestic.game.screens.*
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.runGDX

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

    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when (name) {
        SplashScreen::class.java.name -> SplashScreen(game)
        MainScreen  ::class.java.name -> MainScreen(game)
        RulesScreen ::class.java.name -> RulesScreen(game)
        PPScreen    ::class.java.name -> PPScreen(game)
        GameScreen  ::class.java.name -> GameScreen(game)
        WinnerScreen::class.java.name -> WinnerScreen(game)

        SelectTargetAndPlayersScreen::class.java.name -> SelectTargetAndPlayersScreen(game)

        else -> MainScreen(game)
    }

}