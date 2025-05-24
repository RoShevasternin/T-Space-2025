package com.borocbernay.kasshsemir.game.manager

import com.badlogic.gdx.Gdx
import com.borocbernay.kasshsemir.game.screens.AddSalesScreen
import com.borocbernay.kasshsemir.game.screens.LoaderScreen
import com.borocbernay.kasshsemir.game.screens.TovarsScreen
import com.borocbernay.kasshsemir.game.screens.AddTovarScreen
import com.borocbernay.kasshsemir.game.screens.HistoryScreen
import com.borocbernay.kasshsemir.game.screens.AnalScreen
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedScreen
import com.borocbernay.kasshsemir.game.utils.gdxGame
import com.borocbernay.kasshsemir.game.utils.runGDX

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
    fun clearBackStack() = backStack.clear()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen    ::class.java.name -> LoaderScreen()

        AddTovarScreen  ::class.java.name -> AddTovarScreen()
        AddSalesScreen::class.java.name -> AddSalesScreen()

        TovarsScreen::class.java.name -> TovarsScreen()
        HistoryScreen::class.java.name -> HistoryScreen()
        AnalScreen::class.java.name -> AnalScreen()

        else -> TovarsScreen()
    }

}