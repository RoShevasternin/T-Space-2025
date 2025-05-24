package com.gpecspertisa.sollomka.game.actors.panel

import com.gpecspertisa.sollomka.game.screens.AbstractWorkScreen
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedScreen

class AImprovements_1_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(100, 200, 300, 400)

}