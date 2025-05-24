package com.lebany.lechebnik.game.actors.panel

import com.lebany.lechebnik.game.screens.AbstractWorkScreen
import com.lebany.lechebnik.game.utils.Block
import com.lebany.lechebnik.game.utils.advanced.AdvancedScreen

class AImprovements_1_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(100, 200, 300, 400)

}