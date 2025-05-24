package com.jobzone.cobzone.game.manager

import com.badlogic.gdx.Gdx
import com.jobzone.cobzone.game.GDXGame
import com.jobzone.cobzone.game.screens.*
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.runGDX

class NavigationManager(val game: GDXGame) {

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

    fun clearBackStack() = backStack.clear()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen      ::class.java.name -> LoaderScreen(game)
        GreetingScreen    ::class.java.name -> GreetingScreen(game)
        VacanciesScreen   ::class.java.name -> VacanciesScreen(game)
        VacancyScreen     ::class.java.name -> VacancyScreen(game)
        RespondScreen     ::class.java.name -> RespondScreen(game)
        NotActiveScreen   ::class.java.name -> NotActiveScreen(game)
        AppreciationScreen::class.java.name -> AppreciationScreen(game)

        else -> GreetingScreen(game)
    }

}