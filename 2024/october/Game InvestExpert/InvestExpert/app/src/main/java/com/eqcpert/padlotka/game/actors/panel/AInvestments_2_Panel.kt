package com.eqcpert.padlotka.game.actors.panel

import com.eqcpert.padlotka.game.screens.AbstractWorkScreen
import com.eqcpert.padlotka.game.utils.advanced.AdvancedScreen

class AInvestments_2_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractInvestmentsPanel() {

    override val listItemSum     = listOf(1000, 1500, 2000)
    override val listItemResult  = listOf(1100, 1800, 4000)
    override val listItemSeconds = listOf(25, 30, 35)

}