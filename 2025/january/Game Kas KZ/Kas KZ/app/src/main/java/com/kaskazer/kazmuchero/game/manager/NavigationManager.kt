package com.kaskazer.kazmuchero.game.manager

import com.badlogic.gdx.Gdx
import com.kaskazer.kazmuchero.game.LibGDXGame
import com.kaskazer.kazmuchero.game.screens.*
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen
import com.kaskazer.kazmuchero.game.utils.runGDX

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
        HelloScreen   ::class.java.name -> HelloScreen(game)
        TutorialScreen::class.java.name -> TutorialScreen(game)
        MarketScreen  ::class.java.name -> MarketScreen(game)

        LVL_1_Screen::class.java.name -> LVL_1_Screen(game)
        LVL_2_Screen::class.java.name -> LVL_2_Screen(game)
        LVL_3_Screen::class.java.name -> LVL_3_Screen(game)
        LVL_4_Screen::class.java.name -> LVL_4_Screen(game)
        LVL_5_Screen::class.java.name -> LVL_5_Screen(game)
        LVL_6_Screen::class.java.name -> LVL_6_Screen(game)
        LVL_7_Screen::class.java.name -> LVL_7_Screen(game)
        LVL_8_Screen::class.java.name -> LVL_8_Screen(game)
        LVL_9_Screen::class.java.name -> LVL_9_Screen(game)
        LVL_10_Screen::class.java.name -> LVL_10_Screen(game)

        else -> HelloScreen(game)
    }

}