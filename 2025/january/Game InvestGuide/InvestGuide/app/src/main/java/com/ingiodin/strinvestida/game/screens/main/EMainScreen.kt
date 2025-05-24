package com.ingiodin.strinvestida.game.screens.main

import com.ingiodin.strinvestida.R
import com.ingiodin.strinvestida.appContext
import com.ingiodin.strinvestida.game.LibGDXGame
import com.ingiodin.strinvestida.game.screens.quests.EQuestScreen
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedStage

class EMainScreen(override val game: LibGDXGame) : AbstractMainScreen() {

    override val title: String = appContext.resources.getStringArray(R.array.main_titles)[4]
    override val text : String = appContext.resources.getStringArray(R.array.main_text)[4]
    override val screenName =  EQuestScreen::class.java.name

    override fun AdvancedStage.addToStage() {
        imgWhite.setBounds(90f, 891f, 834f, 400f)
        lblText.setBounds(116f, 936f, 781f, 310f)
    }

}