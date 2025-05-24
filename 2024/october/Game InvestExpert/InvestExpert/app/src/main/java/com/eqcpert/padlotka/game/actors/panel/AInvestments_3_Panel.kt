package com.eqcpert.padlotka.game.actors.panel

import com.eqcpert.padlotka.game.screens.AbstractWorkScreen
import com.eqcpert.padlotka.game.utils.advanced.AdvancedScreen

class AInvestments_3_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractInvestmentsPanel() {

    override val listItemSum     = listOf(1500, 2000, 4000)
    override val listItemResult  = listOf(1800, 2200, 8000)
    override val listItemSeconds = listOf(40, 45, 50)

}