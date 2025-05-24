package com.vectorvesta.bronfinteh.game.manager

import com.badlogic.gdx.Gdx
import com.vectorvesta.bronfinteh.game.screens.HistoryScreen
import com.vectorvesta.bronfinteh.game.screens.LoaderScreen
import com.vectorvesta.bronfinteh.game.screens.MenuScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorDepositScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorInvestmentsScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorLeasingScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorLoanScreen
import com.vectorvesta.bronfinteh.game.screens.calculator.CalculatorMortgageScreen
import com.vectorvesta.bronfinteh.game.screens.delete.DeleteDepositScreen
import com.vectorvesta.bronfinteh.game.screens.delete.DeleteInvestmentsScreen
import com.vectorvesta.bronfinteh.game.screens.delete.DeleteLeasingScreen
import com.vectorvesta.bronfinteh.game.screens.delete.DeleteLoanScreen
import com.vectorvesta.bronfinteh.game.screens.delete.DeleteMortgageScreen
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedScreen
import com.vectorvesta.bronfinteh.game.utils.gdxGame
import com.vectorvesta.bronfinteh.game.utils.runGDX

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

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen ::class.java.name -> LoaderScreen()
        MenuScreen   ::class.java.name -> MenuScreen()
        HistoryScreen::class.java.name -> HistoryScreen()

        CalculatorLeasingScreen    ::class.java.name -> CalculatorLeasingScreen()
        CalculatorLoanScreen       ::class.java.name -> CalculatorLoanScreen()
        CalculatorDepositScreen    ::class.java.name -> CalculatorDepositScreen()
        CalculatorInvestmentsScreen::class.java.name -> CalculatorInvestmentsScreen()
        CalculatorMortgageScreen   ::class.java.name -> CalculatorMortgageScreen()

        DeleteLeasingScreen    ::class.java.name -> DeleteLeasingScreen()
        DeleteLoanScreen       ::class.java.name -> DeleteLoanScreen()
        DeleteDepositScreen    ::class.java.name -> DeleteDepositScreen()
        DeleteInvestmentsScreen::class.java.name -> DeleteInvestmentsScreen()
        DeleteMortgageScreen   ::class.java.name -> DeleteMortgageScreen()

        else -> MenuScreen()
    }

}