package com.ingiodin.strinvestida.game.screens.main

import com.ingiodin.strinvestida.R
import com.ingiodin.strinvestida.appContext
import com.ingiodin.strinvestida.game.LibGDXGame
import com.ingiodin.strinvestida.game.screens.quests.BQuestScreen
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedStage

class BMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[1]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[1]
    override val screenName =  BQuestScreen::class.java.name

    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 874f, 834f, 418f)
        lblText.setBounds(129f, 935f, 754f, 297f)
    }

}