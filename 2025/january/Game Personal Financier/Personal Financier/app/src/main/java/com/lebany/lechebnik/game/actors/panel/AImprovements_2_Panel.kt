package com.lebany.lechebnik.game.actors.panel

import com.lebany.lechebnik.game.screens.AbstractWorkScreen
import com.lebany.lechebnik.game.utils.Block
import com.lebany.lechebnik.game.utils.advanced.AdvancedScreen

class AImprovements_2_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(400, 500, 600, 700)

}