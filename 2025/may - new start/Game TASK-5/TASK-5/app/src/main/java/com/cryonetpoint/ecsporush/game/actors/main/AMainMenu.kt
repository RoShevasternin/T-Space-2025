package com.cryonetpoint.ecsporush.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cryonetpoint.ecsporush.game.actors.button.AButton
import com.cryonetpoint.ecsporush.game.screens.MenuScreen
import com.cryonetpoint.ecsporush.game.screens.QuizScreen
import com.cryonetpoint.ecsporush.game.utils.*
import com.cryonetpoint.ecsporush.game.utils.actor.animDelay
import com.cryonetpoint.ecsporush.game.utils.actor.animHide
import com.cryonetpoint.ecsporush.game.utils.actor.animShow
import com.cryonetpoint.ecsporush.game.utils.actor.setBounds
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedMainGroup
import com.cryonetpoint.ecsporush.game.utils.font.FontParameter

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font102       = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(102))

    private val ls102 = Label.LabelStyle(font102, Color.WHITE)

    private val listType = listOf(
        AButton.Type.Random,
        AButton.Type.Q1,
        AButton.Type.Q2,
        AButton.Type.Q3,
    )

    private val lblText = Label("Выберите режим игры", ls102)
    private val listBtn = List(4) { AButton(screen, listType[it]) }
    private val btnAll  = AButton(screen, AButton.Type.All)

    override fun addActorsOnGroup() {
        color.a = 0f

        addBtns()
        addLbl()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtns() {
        var nx = 102f
        var ny = 1112f

        listBtn.forEachIndexed { index, button ->
            addActor(button)
            button.setBounds(nx, ny, 428f, 372f)

            nx += 5 + 428
            if (index.inc() % 2 == 0) {
                nx = 102f
                ny -= 4 + 372
            }

            button.setOnClickListener {
                MenuScreen.SELECTED_LVL_INDEX = index

                screen.hideScreen {
                    gdxGame.navigationManager.navigate(QuizScreen::class.java.name, screen::class.java.name)
                }
            }
        }

        addActor(btnAll)
        btnAll.setBounds(108f, 503f, 855f, 229f)
        btnAll.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(QuizScreen::class.java.name, screen::class.java.name)
            }
        }

    }

    private fun addLbl() {
        addActor(lblText)
        lblText.setBounds(102f, 1569f, 720f, 248f)
        lblText.wrap = true
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}