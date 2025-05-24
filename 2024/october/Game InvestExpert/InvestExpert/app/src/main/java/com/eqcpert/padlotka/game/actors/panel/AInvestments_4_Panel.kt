package com.eqcpert.padlotka.game.actors.panel

import com.eqcpert.padlotka.game.screens.AbstractWorkScreen
import com.eqcpert.padlotka.game.utils.advanced.AdvancedScreen

class AInvestments_4_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractInvestmentsPanel() {

    override val listItemSum     = listOf(2200, 4000, 8000)
    override val listItemResult  = listOf(2600, 4800, 16000)
    override val listItemSeconds = listOf(60, 90, 120)

}