package com.figidnansovich.glamour.game.screens.main

import com.figidnansovich.glamour.R
import com.figidnansovich.glamour.appContext
import com.figidnansovich.glamour.game.LibGDXGame
import com.figidnansovich.glamour.game.screens.quests.AQuestScreen
import com.figidnansovich.glamour.game.utils.advanced.AdvancedStage

class AMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[0]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[0]
    override val screenName =  AQuestScreen::class.java.name


    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 577f, 834f, 715f)
        lblText.setBounds(129f, 600f, 754f, 670f)
    }

}