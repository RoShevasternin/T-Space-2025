package com.bobrevsc.tkarierraperbola.game.actors.panel

import com.bobrevsc.tkarierraperbola.game.screens.AbstractWorkScreen
import com.bobrevsc.tkarierraperbola.game.utils.Block
import com.bobrevsc.tkarierraperbola.game.utils.advanced.AdvancedScreen

class AImprovements_1_Panel(
    override val screen: AdvancedScreen,
    override val screenType: AbstractWorkScreen.ScreenType,
): AAbstractImprovementsPanel() {

    override val listItemSum = listOf(100, 200, 300, 400)

}