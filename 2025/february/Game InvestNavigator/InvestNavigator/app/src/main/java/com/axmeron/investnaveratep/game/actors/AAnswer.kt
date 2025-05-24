package com.axmeron.investnaveratep.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.axmeron.investnaveratep.game.utils.actor.disable
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.gdxGame
import com.axmeron.investnaveratep.game.utils.runGDX
import com.axmeron.investnaveratep.util.log

class AAnswer(
    override val screen: AdvancedScreen,
    val ls35: Label.LabelStyle,
): AdvancedGroup() {

    var isTrue = false

    private val imgFrame = Image(gdxGame.assetsAll.nine_frame)
    private val lblText  = Label("", ls35)

    override fun addActorsOnGroup() {
        addAndFillActor(imgFrame)
        addLblText()
    }

    override fun sizeChanged() {
        super.sizeChanged()
        imgFrame.setSize(width, height)
    }

    // Actors ---------------------------------------------------

    private fun addLblText() {
        addActor(lblText)

        lblText.apply {
            setAlignment(Align.center)
            wrap   = true
            width  = 520f
            height = prefHeight
            setPosition(34f, 21f)
        }

    }

    // Logic -------------------------------------------------------

    fun setText(text: String) {
        lblText.apply {
            setText(text)
            height = prefHeight
        }
    }

    fun setResult() {
        imgFrame.drawable = NinePatchDrawable(if (isTrue) gdxGame.assetsAll.nine_green else gdxGame.assetsAll.nine_red)
    }

    fun updateSize() {
        this.height = (lblText.height + 42f)
        log("lll = $height")
    }

}