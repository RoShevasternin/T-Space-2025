package com.figidnansovich.glamour.game.screens.main

import com.figidnansovich.glamour.R
import com.figidnansovich.glamour.appContext
import com.figidnansovich.glamour.game.LibGDXGame
import com.figidnansovich.glamour.game.screens.quests.AQuestScreen
import com.figidnansovich.glamour.game.screens.quests.EQuestScreen
import com.figidnansovich.glamour.game.utils.advanced.AdvancedStage

class EMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[4]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[4]
    override val screenName =  EQuestScreen::class.java.name

    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 891f, 834f, 400f)
        lblText.setBounds(116f, 936f, 781f, 310f)
    }

}