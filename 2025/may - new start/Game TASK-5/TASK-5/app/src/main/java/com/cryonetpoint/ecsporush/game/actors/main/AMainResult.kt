package com.cryonetpoint.ecsporush.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cryonetpoint.ecsporush.game.actors.button.AButton
import com.cryonetpoint.ecsporush.game.actors.button.ATextButton
import com.cryonetpoint.ecsporush.game.screens.MenuScreen
import com.cryonetpoint.ecsporush.game.screens.QuizScreen
import com.cryonetpoint.ecsporush.game.screens.ResultScreen
import com.cryonetpoint.ecsporush.game.utils.Acts
import com.cryonetpoint.ecsporush.game.utils.Block
import com.cryonetpoint.ecsporush.game.utils.GameColor
import com.cryonetpoint.ecsporush.game.utils.TIME_ANIM_SCREEN
import com.cryonetpoint.ecsporush.game.utils.actor.animDelay
import com.cryonetpoint.ecsporush.game.utils.actor.animHide
import com.cryonetpoint.ecsporush.game.utils.actor.animShow
import com.cryonetpoint.ecsporush.game.utils.actor.setOrigin
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedMainGroup
import com.cryonetpoint.ecsporush.game.utils.font.FontParameter
import com.cryonetpoint.ecsporush.game.utils.gdxGame

class AMainResult(override val screen: ResultScreen): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(51))

    private val ls51 = Label.LabelStyle(font51, Color.WHITE)

    private val imgPanel   = Image(gdxGame.assetsAll.RESULT_TEXT)
    private val lblTitle   = Label((10..99).random().toString() + "%", ls51)
    private val imgResult  = Image(gdxGame.assetsAll.STAR)
    private val btnRestart = AButton(screen, AButton.Type.ToMain)

    override fun addActorsOnGroup() {
        color.a = 0f

        addBtns()
        addImgPanel()
        addLblResult()
        addImgResult()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtns() {
        addActors(btnRestart)
        btnRestart.setBounds(64f, 875f, 941f, 172f)

        btnRestart.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(64f, 1149f, 941f, 323f)
    }

    private fun addLblResult() {
        addActor(lblTitle)
        lblTitle.setBounds(844f, 1324f, 108f, 37f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addImgResult() {
        addActor(imgResult)
        imgResult.setBounds(0f, 1016f, 1068f, 1302f)
        imgResult.setOrigin(Align.center)

        imgResult.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(1.1f, 1.1f, 0.4f),
            Acts.scaleTo(1f, 1f, 0.4f),
        )))
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