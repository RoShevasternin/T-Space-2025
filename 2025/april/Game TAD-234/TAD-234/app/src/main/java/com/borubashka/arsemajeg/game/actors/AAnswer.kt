package com.borubashka.arsemajeg.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.borubashka.arsemajeg.game.screens.QuizScreen
import com.borubashka.arsemajeg.game.utils.actor.disable
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedGroup
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedScreen
import com.borubashka.arsemajeg.game.utils.gdxGame

class AAnswer(
    override val screen: AdvancedScreen,
    ls39: Label.LabelStyle,
    answer: String,
): AdvancedGroup() {

    var isWin = false

    private val imgResult = Image(gdxGame.assetsAll.blue)
    private val lblText   = Label(answer, ls39)

    override fun addActorsOnGroup() {
        addAndFillActor(imgResult)
        addActor(lblText)

        lblText.setBounds(24f, 0f, 777f, 123f)
        lblText.setAlignment(Align.center)
        lblText.wrap = true

        children.onEach { it.disable() }
    }

    fun select() {
        imgResult.drawable = TextureRegionDrawable(if (isWin) gdxGame.assetsAll.green else gdxGame.assetsAll.red)

        if (isWin) {
            QuizScreen.WIN_COUNT++
            gdxGame.soundUtil.apply { play(win) }
        } else {
            QuizScreen.WIN_COUNT--
            gdxGame.soundUtil.apply { play(fail) }
        }
    }

    fun unselect() {
        imgResult.drawable = TextureRegionDrawable(gdxGame.assetsAll.blue)
    }

    fun updateText(text: String) {
        lblText.setText(text)
    }


}