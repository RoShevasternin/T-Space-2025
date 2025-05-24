package com.ingiodin.strinvestida.game.screens.main

import com.ingiodin.strinvestida.R
import com.ingiodin.strinvestida.appContext
import com.ingiodin.strinvestida.game.LibGDXGame
import com.ingiodin.strinvestida.game.screens.quests.CQuestScreen
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedStage

class CMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[2]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[2]
    override val screenName =  CQuestScreen::class.java.name

    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 708f, 834f, 583f)
        lblText.setBounds(116f, 755f, 781f, 489f)
    }

}