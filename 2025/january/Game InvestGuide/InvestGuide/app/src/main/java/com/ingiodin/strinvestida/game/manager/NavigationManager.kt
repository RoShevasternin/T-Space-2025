package com.ingiodin.strinvestida.game.manager

import com.badlogic.gdx.Gdx
import com.ingiodin.strinvestida.game.LibGDXGame
import com.ingiodin.strinvestida.game.screens.*
import com.ingiodin.strinvestida.game.screens.main.*
import com.ingiodin.strinvestida.game.screens.quests.*
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedScreen
import com.ingiodin.strinvestida.game.utils.runGDX

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
        MenuScreen    ::class.java.name -> MenuScreen(game)

        AMainScreen   ::class.java.name -> AMainScreen(game)
        BMainScreen   ::class.java.name -> BMainScreen(game)
        CMainScreen   ::class.java.name -> CMainScreen(game)
        DMainScreen   ::class.java.name -> DMainScreen(game)
        EMainScreen   ::class.java.name -> EMainScreen(game)

        AQuestScreen   ::class.java.name -> AQuestScreen(game)
        BQuestScreen   ::class.java.name -> BQuestScreen(game)
        CQuestScreen   ::class.java.name -> CQuestScreen(game)
        DQuestScreen   ::class.java.name -> DQuestScreen(game)
        EQuestScreen   ::class.java.name -> EQuestScreen(game)

        Rating_10_Screen::class.java.name -> Rating_10_Screen(game)
        Rating_5_Screen ::class.java.name -> Rating_5_Screen(game)
        Rating_1_Screen ::class.java.name -> Rating_1_Screen(game)

        else -> MenuScreen(game)
    }

}