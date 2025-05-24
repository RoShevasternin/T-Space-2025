package com.liberator.wisoliter.game.actors.tutorial

import com.liberator.wisoliter.game.actors.ABalance
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen

class ATutorial_3(override val screen: AdvancedScreen): AdvancedGroup() {

    private val balance = ABalance(screen)

    override fun addActorsOnGroup() {
        addBalance()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBalance() {
        addActor(balance)
        balance.setBounds(27f, 1480f, 724f, 174f)
    }

}