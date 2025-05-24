package com.figidnansovich.glamour.game.screens.main

import com.figidnansovich.glamour.R
import com.figidnansovich.glamour.appContext
import com.figidnansovich.glamour.game.LibGDXGame
import com.figidnansovich.glamour.game.screens.quests.AQuestScreen
import com.figidnansovich.glamour.game.screens.quests.DQuestScreen
import com.figidnansovich.glamour.game.utils.advanced.AdvancedStage

class DMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[3]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[3]
    override val screenName =  DQuestScreen::class.java.name

    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 891f, 834f, 400f)
        lblText.setBounds(116f, 943f, 781f, 297f)
    }

}