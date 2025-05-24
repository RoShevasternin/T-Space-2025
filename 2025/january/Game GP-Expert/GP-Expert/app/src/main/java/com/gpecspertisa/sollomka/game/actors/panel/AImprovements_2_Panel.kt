package com.gpecspertisa.sollomka.game.actors.panel

import com.gpecspertisa.sollomka.game.screens.AbstractWorkScreen
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedScreen

class AImprovements_2_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(400, 500, 600, 700)

}