package com.eqcpert.ginvestrum.game.actors.panel

import com.eqcpert.ginvestrum.game.screens.AbstractWorkScreen
import com.eqcpert.ginvestrum.game.utils.Block
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedScreen

class AInvestments_3_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractInvestmentsPanel() {

    override val listItemSum     = listOf(1500, 2000, 4000)
    override val listItemResult  = listOf(1800, 2200, 8000)
    override val listItemSeconds = listOf(40, 45, 50)

}