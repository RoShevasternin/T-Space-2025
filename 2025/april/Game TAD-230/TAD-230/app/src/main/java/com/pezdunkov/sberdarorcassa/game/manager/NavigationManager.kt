package com.pezdunkov.sberdarorcassa.game.manager

import com.badlogic.gdx.Gdx
import com.pezdunkov.sberdarorcassa.game.screens.AddProdajaScreen
import com.pezdunkov.sberdarorcassa.game.screens.LoaderScreen
import com.pezdunkov.sberdarorcassa.game.screens.S1Screen
import com.pezdunkov.sberdarorcassa.game.screens.AddTovarScreen
import com.pezdunkov.sberdarorcassa.game.screens.S2Screen
import com.pezdunkov.sberdarorcassa.game.screens.S3Screen
import com.pezdunkov.sberdarorcassa.game.screens.S4Screen
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedScreen
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame
import com.pezdunkov.sberdarorcassa.game.utils.runGDX

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
        AddProdajaScreen::class.java.name -> AddProdajaScreen()

        S1Screen::class.java.name -> S1Screen()
        S2Screen::class.java.name -> S2Screen()
        S3Screen::class.java.name -> S3Screen()
        S4Screen::class.java.name -> S4Screen()

        else -> S1Screen()
    }

}