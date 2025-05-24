package com.cryonetpoint.ecsporush.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.cryonetpoint.ecsporush.game.screens.QuizScreen
import com.cryonetpoint.ecsporush.game.utils.actor.disable
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedGroup
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedScreen
import com.cryonetpoint.ecsporush.game.utils.gdxGame

class AAnswer(
    override val screen: AdvancedScreen,
    ls51: Label.LabelStyle,
    answer: String,
): AdvancedGroup() {

    var isWin = false

    private val imgResult = Image(gdxGame.assetsAll.box_def)
    private val lblText   = Label(answer, ls51)

    override fun addActorsOnGroup() {
        addAndFillActor(imgResult)
        addActor(lblText)

        lblText.setBounds(205f, 4f, 687f, 219f)
        lblText.setAlignment(Align.center or Align.left)
        lblText.wrap = true

        children.onEach { it.disable() }
    }

    fun select() {
        imgResult.drawable = TextureRegionDrawable(/*if (isWin)*/ gdxGame.assetsAll.box_press)// else gdxGame.assetsAll.RESULT_TEXT)

        if (isWin) {
            //QuizScreen.WIN_COUNT++
            gdxGame.soundUtil.apply { play(click) }
        } else {
            //QuizScreen.WIN_COUNT--
            gdxGame.soundUtil.apply { play(click) }
        }
    }

    fun unselect() {
        imgResult.drawable = TextureRegionDrawable(gdxGame.assetsAll.box_def)
    }

    fun updateText(text: String) {
        lblText.setText(text)
    }


}