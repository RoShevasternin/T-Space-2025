package com.inveanst.litka.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.inveanst.litka.game.actors.checkbox.ACheckBox
import com.inveanst.litka.game.utils.FONT_SCALE
import com.inveanst.litka.game.utils.GColor
import com.inveanst.litka.game.utils.actor.setOnClickListener
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.disable
import com.inveanst.litka.game.utils.font.FontParameter
import com.inveanst.litka.util.log

class AAnswer(override val screen: AdvancedScreen, var isWin: Boolean, val text: String): AdvancedGroup() {

    private val fontParameter  = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL)
    private val fontMedium16   = screen.fontGenerator_Raleway_Medium.generateFont(fontParameter.setSize(16 * 2))
    private val fontSemiBold16 = screen.fontGenerator_Raleway_SemiBold.generateFont(fontParameter.setSize(16 * 2))

    private val lsM16  = LabelStyle(fontMedium16, GColor.answerM)
    private val lsSB16 = LabelStyle(fontSemiBold16, GColor.text)

    private val imgState = Image(screen.game.all.frame_9_gray)
    private val box      = Image(screen.game.all.box_def)
    private val lblText  = Label(text, lsM16)

    var blockCheck = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgState)
        addLblText()
        imgState.height = lblText.prefHeight + 16f
        height          = lblText.prefHeight + 16f

        addBox()

        children.onEach { it.disable() }
        setOnClickListener {
            if (isWin) {
                screen.game.soundUtil.apply { play(bonus_game, 0.7f) }
                box.drawable = TextureRegionDrawable(screen.game.all.box_check)
                imgState.drawable = NinePatchDrawable(screen.game.all.frame_9_green)
            } else {
                screen.game.soundUtil.apply { play(fail_game, 0.5f) }
                box.drawable = TextureRegionDrawable(screen.game.all.box_red)
                imgState.drawable = NinePatchDrawable(screen.game.all.frame_9_red)
            }

            lblText.style = lsSB16

            blockCheck()
        }
    }

    private fun AdvancedGroup.addLblText() {
        lblText.setAlignment(Align.left)
        lblText.wrap = true
        lblText.width = 215f
        lblText.setFontScale(1f / 2)
        lblText.invalidate()
        lblText.height = lblText.prefHeight
        addActor(lblText)

        lblText.setPosition(50f, 8f)
    }

    private fun AdvancedGroup.addBox() {
        box.setSize(20f,20f)
        box.x = 10f
        box.y = (height / 2) - 10f
        addActor(box)
    }

    // Logic ----------------------------------------------------

    fun reset() {
        box.drawable = TextureRegionDrawable(screen.game.all.box_def)
        imgState.drawable = NinePatchDrawable(screen.game.all.frame_9_gray)
        lblText.style = lsM16
    }

    fun update(isWin: Boolean, text: String) {
        this.isWin = isWin
        lblText.setText(text)
        lblText.invalidate()
        lblText.height = lblText.prefHeight

        imgState.height = lblText.prefHeight + 16f
        height          = lblText.prefHeight + 16f
        box.y           = (height / 2) - 10f
    }

}