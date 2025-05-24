package com.eqcpert.padlotka.game.actors.panel

import com.eqcpert.padlotka.game.screens.AbstractWorkScreen
import com.eqcpert.padlotka.game.utils.advanced.AdvancedScreen

class AImprovements_3_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(900, 1000, 1100, 1200)

}