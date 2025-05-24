package com.ingiodin.strinvestida.game.screens.main

import com.ingiodin.strinvestida.R
import com.ingiodin.strinvestida.appContext
import com.ingiodin.strinvestida.game.LibGDXGame
import com.ingiodin.strinvestida.game.screens.quests.AQuestScreen
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedStage

class AMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[0]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[0]
    override val screenName =  AQuestScreen::class.java.name


    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 577f, 834f, 715f)
        lblText.setBounds(129f, 600f, 754f, 670f)
    }

}