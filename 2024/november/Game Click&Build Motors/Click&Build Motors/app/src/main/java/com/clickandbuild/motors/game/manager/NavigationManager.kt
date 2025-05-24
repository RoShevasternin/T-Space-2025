package com.clickandbuild.motors.game.manager

import com.badlogic.gdx.Gdx
import com.clickandbuild.motors.game.LibGDXGame
import com.clickandbuild.motors.game.screens.*
import com.clickandbuild.motors.game.utils.advanced.AdvancedScreen
import com.clickandbuild.motors.game.utils.runGDX

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
        LoaderScreen      ::class.java.name -> LoaderScreen(game)

        Tutorial_1_Screen ::class.java.name -> Tutorial_1_Screen(game)
        Tutorial_2_Screen ::class.java.name -> Tutorial_2_Screen(game)
        Tutorial_3_Screen ::class.java.name -> Tutorial_3_Screen(game)

        Factory_0_Screen ::class.java.name -> Factory_0_Screen(game)
        Factory_1_Screen ::class.java.name -> Factory_1_Screen(game)
        Factory_2_Screen ::class.java.name -> Factory_2_Screen(game)
        Factory_3_Screen ::class.java.name -> Factory_3_Screen(game)
        Factory_4_Screen ::class.java.name -> Factory_4_Screen(game)
        Factory_5_Screen ::class.java.name -> Factory_5_Screen(game)
        Factory_6_Screen ::class.java.name -> Factory_6_Screen(game)
        Factory_7_Screen ::class.java.name -> Factory_7_Screen(game)
        Factory_8_Screen ::class.java.name -> Factory_8_Screen(game)

        SellScreen      ::class.java.name -> SellScreen(game)
        AttentionScreen ::class.java.name -> AttentionScreen(game)

        else -> Tutorial_1_Screen(game)
    }

}