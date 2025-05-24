package com.ayrym.inperdader.game.actors

import com.ayrym.inperdader.game.utils.GameColor
import com.ayrym.inperdader.game.utils.actor.disable
import com.ayrym.inperdader.game.utils.advanced.AdvancedGroup
import com.ayrym.inperdader.game.utils.advanced.AdvancedScreen
import com.ayrym.inperdader.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align

class AAnswer(
    override val screen: AdvancedScreen,
    text: String,
    ls38: Label.LabelStyle,
    val ls30: Label.LabelStyle,
    answer: String,
): AdvancedGroup() {

    var isWin = false

    private val circle  = Image(gdxGame.assetsAll.circle_def)
    private val lbl     = Label(text, ls38)
    private val lblText = Label(answer, ls30)

    override fun addActorsOnGroup() {
        addActors(circle, lbl, lblText)

        circle.setBounds(0f, 0f, 85f, 85f)
        lbl.setBounds(0f, 0f, 85f, 85f)
        lblText.setBounds(103f, 0f, 615f, 85f)

        lbl.setAlignment(Align.center)
        lblText.setAlignment(Align.left or Align.center)
        lblText.wrap = true
        children.onEach { it.disable() }
    }

    fun select() {
        circle.drawable = TextureRegionDrawable(if (isWin) gdxGame.assetsAll.circle_check else gdxGame.assetsAll.red)
        lblText.style = Label.LabelStyle(ls30).also { it.fontColor = if (isWin) GameColor.purpl_B5 else GameColor.red_E3 }

        if (isWin) {
            gdxGame.soundUtil.apply { play(win) }
        } else {
            gdxGame.soundUtil.apply { play(fail) }
        }
    }

    fun unselect() {
        circle.drawable = TextureRegionDrawable(gdxGame.assetsAll.circle_def)
        lblText.style = Label.LabelStyle(ls30).also { it.fontColor = GameColor.black_36 }
    }

    fun updateText(text: String) {
        lblText.setText(text)
    }


}