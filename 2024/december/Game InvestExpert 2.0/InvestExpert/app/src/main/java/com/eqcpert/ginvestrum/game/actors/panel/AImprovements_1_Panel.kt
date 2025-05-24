package com.eqcpert.ginvestrum.game.actors.panel

import com.eqcpert.ginvestrum.game.screens.AbstractWorkScreen
import com.eqcpert.ginvestrum.game.utils.Block
import com.eqcpert.ginvestrum.game.utils.advanced.AdvancedScreen

class AImprovements_1_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(100, 200, 300, 400)

}