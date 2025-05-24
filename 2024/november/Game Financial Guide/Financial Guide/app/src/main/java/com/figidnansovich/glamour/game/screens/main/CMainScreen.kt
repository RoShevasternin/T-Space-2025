package com.figidnansovich.glamour.game.screens.main

import com.figidnansovich.glamour.R
import com.figidnansovich.glamour.appContext
import com.figidnansovich.glamour.game.LibGDXGame
import com.figidnansovich.glamour.game.screens.quests.AQuestScreen
import com.figidnansovich.glamour.game.screens.quests.CQuestScreen
import com.figidnansovich.glamour.game.utils.advanced.AdvancedStage

class CMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[2]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[2]
    override val screenName =  CQuestScreen::class.java.name

    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 708f, 834f, 583f)
        lblText.setBounds(116f, 755f, 781f, 489f)
    }

}