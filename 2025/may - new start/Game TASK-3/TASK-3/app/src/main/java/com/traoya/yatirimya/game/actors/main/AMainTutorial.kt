package com.traoya.yatirimya.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.traoya.yatirimya.game.actors.ABalance
import com.traoya.yatirimya.game.actors.button.AButton
import com.traoya.yatirimya.game.screens.MenuScreen
import com.traoya.yatirimya.game.screens.TutorialScreen
import com.traoya.yatirimya.game.utils.Block
import com.traoya.yatirimya.game.utils.GameColor
import com.traoya.yatirimya.game.utils.TIME_ANIM_SCREEN
import com.traoya.yatirimya.game.utils.actor.animDelay
import com.traoya.yatirimya.game.utils.actor.animHide
import com.traoya.yatirimya.game.utils.actor.animShow
import com.traoya.yatirimya.game.utils.advanced.AdvancedMainGroup
import com.traoya.yatirimya.game.utils.font.FontParameter
import com.traoya.yatirimya.game.utils.gdxGame

class AMainTutorial(override val screen: TutorialScreen): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters("1-LVL")
    private val font66        = screen.fontGenerator_SemiBold.generateFont(fontParameter.setSize(66))

    private val ls66 = Label.LabelStyle(font66, GameColor.red)

    private val imgDashboard   = Image(gdxGame.assetsAll.T3)
    private val btnNext        = AButton(screen, AButton.Type.Def)
    private val btnRD          = AButton(screen, AButton.Type.RD)

    private val aBalance = ABalance(screen)

    private val imgLvl = Image(gdxGame.assetsAll.wht)
    private val lblLvl = Label("1-LVL", ls66)

    override fun addActorsOnGroup() {
        color.a = 0f

        addABalance()
        addImgDashboard()
        addBtnNext()
        //addLvl()
        //addBtnRD()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addABalance() {
        addActor(aBalance)
        aBalance.setBounds(0f, 2030f, 1040f, 125f)
    }

    private fun addLvl() {
        addActor(imgLvl)
        imgLvl.setBounds(83f, 102f, 278f, 168f)

        addActor(lblLvl)
        lblLvl.setBounds(135f, 152f, 172f, 67f)
    }

    private fun addImgDashboard() {
        addActor(imgDashboard)
        imgDashboard.setBounds(83f, 323f, 875f, 1375f)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(110f, 378f, 820f, 154f)
        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }

    private fun addBtnRD() {
        addActor(btnRD)
        btnRD.setBounds(679f, 102f, 279f, 168f)
        btnRD.press()
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