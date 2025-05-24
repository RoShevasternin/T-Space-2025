package com.finansoviy.gurochek.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.finansoviy.gurochek.game.screens.GameScreen
import com.finansoviy.gurochek.game.utils.TextureEmpty
import com.finansoviy.gurochek.game.utils.actor.disable
import com.finansoviy.gurochek.game.utils.advanced.AdvancedGroup
import com.finansoviy.gurochek.game.utils.advanced.AdvancedScreen
import com.finansoviy.gurochek.game.utils.gdxGame
import com.finansoviy.gurochek.game.utils.region

class AAnswer(
    override val screen: AdvancedScreen,
    ls37: Label.LabelStyle,
    answer: String,
): AdvancedGroup() {

    var isWin = false

    private val imgResult = Image()
    private val lblText   = Label(answer, ls37)

    override fun addActorsOnGroup() {
        addAndFillActor(imgResult)
        addActor(lblText)

        lblText.setBounds(6f, 6f, 880f, 135f)
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