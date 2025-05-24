package com.barabanovich.helowerskay.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.barabanovich.helowerskay.game.screens.GameScreen
import com.barabanovich.helowerskay.game.utils.TextureEmpty
import com.barabanovich.helowerskay.game.utils.actor.disable
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedGroup
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedScreen
import com.barabanovich.helowerskay.game.utils.gdxGame
import com.barabanovich.helowerskay.game.utils.region

class AAnswer(
    override val screen: AdvancedScreen,
    ls43: Label.LabelStyle,
    answer: String,
): AdvancedGroup() {

    var isWin = false

    private val imgResult = Image()
    private val lblText   = Label(answer, ls43)

    override fun addActorsOnGroup() {
        addAndFillActor(imgResult)
        addActor(lblText)

        lblText.setBounds(19f, 10f, 813f, 110f)
        lblText.setAlignment(Align.center)
        lblText.wrap = true

        children.onEach { it.disable() }
    }

    fun select() {
        imgResult.drawable = TextureRegionDrawable(if (isWin) gdxGame.assetsAll.green else gdxGame.assetsAll.red)

        if (isWin) {
            GameScreen.WIN_COUNT++
            gdxGame.soundUtil.apply { play(win) }
        } else {
            GameScreen.WIN_COUNT--
            gdxGame.soundUtil.apply { play(fail) }
        }
    }

    fun unselect() {
        imgResult.drawable = TextureRegionDrawable(TextureEmpty.region)
    }

    fun updateText(text: String) {
        lblText.setText(text)
    }


}