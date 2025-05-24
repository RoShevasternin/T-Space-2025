package com.progressmas.samsuster.game.actors.panel

import com.progressmas.samsuster.game.screens.AbstractWorkScreen
import com.progressmas.samsuster.game.utils.advanced.AdvancedScreen

class AImprovements_1_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(100, 200, 300, 400)

}