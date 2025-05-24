package com.cburaktev.klavregasd.game.manager

import com.badlogic.gdx.Gdx
import com.cburaktev.klavregasd.game.screens.LoaderScreen
import com.cburaktev.klavregasd.game.screens.MenuScreen
import com.cburaktev.klavregasd.game.screens.NewItemScreen
import com.cburaktev.klavregasd.game.screens.PreviewScreen
import com.cburaktev.klavregasd.game.screens.StatisticScreen
import com.cburaktev.klavregasd.game.screens.TestResultScreen
import com.cburaktev.klavregasd.game.screens.TestScreen
import com.cburaktev.klavregasd.game.screens.TestSelectScreen
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedScreen
import com.cburaktev.klavregasd.game.utils.gdxGame
import com.cburaktev.klavregasd.game.utils.runGDX

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
        MenuScreen      ::class.java.name -> MenuScreen()
        PreviewScreen   ::class.java.name -> PreviewScreen()
        NewItemScreen   ::class.java.name -> NewItemScreen()
        StatisticScreen ::class.java.name -> StatisticScreen()
        TestSelectScreen::class.java.name -> TestSelectScreen()
        TestScreen      ::class.java.name -> TestScreen()
        TestResultScreen::class.java.name -> TestResultScreen()

        else -> MenuScreen()
    }

}