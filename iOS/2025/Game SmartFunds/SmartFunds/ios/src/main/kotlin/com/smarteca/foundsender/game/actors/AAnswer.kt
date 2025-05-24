package com.smarteca.foundsender.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.smarteca.foundsender.game.screens.test.SelectedTestScreen
import com.smarteca.foundsender.game.utils.TextureEmpty
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.actor.animShow
import com.smarteca.foundsender.game.utils.actor.disable
import com.smarteca.foundsender.game.utils.advanced.AdvancedGroup
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen
import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.game.utils.region

class AAnswer(
    override val screen: AdvancedScreen,
    ls48: Label.LabelStyle,
    answer: String,
): AdvancedGroup() {

    var isWin = false

    private val imgResult = Image()
    private val lblText   = Label(answer, ls48)
    private val imgCircle = Image(gdxGame.assetsAll.circle)

    override fun addActorsOnGroup() {
        addAndFillActor(imgResult)
        addActor(lblText)
        addActor(imgCircle)

        lblText.setBounds(194f, 61f, 838f, 57f)
        lblText.setAlignment(Align.center or Align.left)
        lblText.wrap = true

        imgCircle.setBounds(90f, 69f, 42f, 42f)
        imgCircle.color.a = 0f

        children.onEach { it.disable() }
    }

    fun select() {
        imgResult.drawable = TextureRegionDrawable(if (isWin) gdxGame.assetsAll.green else gdxGame.assetsAll.red)
        imgCircle.animShow(0.2f)

        if (isWin) {
            SelectedTestScreen.WIN_COUNT++
            gdxGame.soundUtil.apply { play(win) }
        } else {
            // SelectedTestScreen.WIN_COUNT--
            gdxGame.soundUtil.apply { play(fail) }
        }
    }

    fun unselect() {
        imgResult.drawable = TextureRegionDrawable(TextureEmpty.region)
        imgCircle.animHide(0.1f)
    }

    fun updateText(text: String) {
        lblText.setText(text)
    }


}
