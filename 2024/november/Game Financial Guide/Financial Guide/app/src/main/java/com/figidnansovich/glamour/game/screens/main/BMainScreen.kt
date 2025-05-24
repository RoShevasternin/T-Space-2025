package com.figidnansovich.glamour.game.screens.main

import com.figidnansovich.glamour.R
import com.figidnansovich.glamour.appContext
import com.figidnansovich.glamour.game.LibGDXGame
import com.figidnansovich.glamour.game.screens.quests.AQuestScreen
import com.figidnansovich.glamour.game.screens.quests.BQuestScreen
import com.figidnansovich.glamour.game.utils.advanced.AdvancedStage

class BMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[1]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[1]
    override val screenName =  BQuestScreen::class.java.name

    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 874f, 834f, 418f)
        lblText.setBounds(129f, 935f, 754f, 297f)
    }

}