package com.cburaktev.klavregasd.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.cburaktev.klavregasd.game.screens.TestScreen
import com.cburaktev.klavregasd.game.utils.actor.disable
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedGroup
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedScreen
import com.cburaktev.klavregasd.game.utils.gdxGame

class AAnswer(
    override val screen: AdvancedScreen,
    ls46: Label.LabelStyle,
    answer: String,
): AdvancedGroup() {

    var isWin = false

    private val imgResult = Image(gdxGame.assetsAll.white)
    private val lblText   = Label(answer, ls46)

    override fun addActorsOnGroup() {
        addAndFillActor(imgResult)
        addActor(lblText)

        lblText.setBounds(30f, 16f, 816f, 127f)
        lblText.setAlignment(Align.center)
        lblText.wrap = true

        children.onEach { it.disable() }
    }

    fun select() {
        imgResult.drawable = TextureRegionDrawable(if (isWin) gdxGame.assetsAll.green else gdxGame.assetsAll.red)

        if (isWin) {
            TestScreen.WIN_COUNT++
            gdxGame.soundUtil.apply { play(win) }
        } else {
            TestScreen.WIN_COUNT--
            gdxGame.soundUtil.apply { play(fail) }
        }
    }

    fun unselect() {
        imgResult.drawable = TextureRegionDrawable(gdxGame.assetsAll.white)
    }

    fun updateText(text: String) {
        lblText.setText(text)
    }


}