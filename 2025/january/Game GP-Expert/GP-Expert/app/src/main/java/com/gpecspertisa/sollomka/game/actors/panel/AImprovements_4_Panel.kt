package com.gpecspertisa.sollomka.game.actors.panel

import com.gpecspertisa.sollomka.game.screens.AbstractWorkScreen
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedScreen

class AImprovements_4_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(1000, 1500, 2000, 3000)

}