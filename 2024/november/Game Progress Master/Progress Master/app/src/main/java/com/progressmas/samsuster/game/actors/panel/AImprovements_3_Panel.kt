package com.progressmas.samsuster.game.actors.panel

import com.progressmas.samsuster.game.screens.AbstractWorkScreen
import com.progressmas.samsuster.game.utils.advanced.AdvancedScreen

class AImprovements_3_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(900, 1000, 1100, 1200)

}