package com.gosinventarytet.debagovich.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.gosinventarytet.debagovich.game.actors.button.AButton
import com.gosinventarytet.debagovich.game.screens.ResultScreen
import com.gosinventarytet.debagovich.game.utils.Block
import com.gosinventarytet.debagovich.game.utils.TIME_ANIM_SCREEN
import com.gosinventarytet.debagovich.game.utils.actor.animDelay
import com.gosinventarytet.debagovich.game.utils.actor.animHide
import com.gosinventarytet.debagovich.game.utils.actor.animShow
import com.gosinventarytet.debagovich.game.utils.advanced.AdvancedMainGroup
import com.gosinventarytet.debagovich.game.utils.font.FontParameter
import com.gosinventarytet.debagovich.game.utils.gdxGame

class AMainResult(
    override val screen: ResultScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font55 = screen.fontGenerator_Medium.generateFont(parameter.setSize(55))
    private val font38 = screen.fontGenerator_Medium.generateFont(parameter.setSize(38))

    private val ls50 = Label.LabelStyle(font55, Color.WHITE)
    private val ls38 = Label.LabelStyle(font38, Color.WHITE)

    private val resultText = "Вы ответили больше чем на ${(55..100).random()}% вопросов правильно, и получаете награду!"

    private val lblGreet  = Label("Поздравляем!", ls50)
    private val lblText   = Label(resultText, ls38)
    private val btnToMain = AButton(screen, AButton.Type.ToMain)

    override fun addActorsOnGroup() {
        color.a = 0f

        addLblGreet()
        addLblText()
        addBtnToMain()

        animShowMain {
            gdxGame.soundUtil.apply { play(win) }
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblGreet() {
        addActor(lblGreet)
        lblGreet.setBounds(203f, 1053f, 387f, 40f)
        lblGreet.setAlignment(Align.center)
    }

    private fun addLblText() {
        addActor(lblText)
        lblText.setBounds(26f, 861f, 740f, 150f)
        lblText.setAlignment(Align.center)
        lblText.wrap = true

    }

    private fun addBtnToMain() {
        addActor(btnToMain)
        btnToMain.setBounds(47f, 649f, 699f, 128f)
        btnToMain.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}