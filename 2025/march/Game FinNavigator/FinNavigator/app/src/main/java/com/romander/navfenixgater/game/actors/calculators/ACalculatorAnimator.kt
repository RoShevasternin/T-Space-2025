package com.romander.navfenixgater.game.actors.calculators

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.romander.navfenixgater.game.utils.Block
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.actor.animHide
import com.romander.navfenixgater.game.utils.actor.animMoveTo
import com.romander.navfenixgater.game.utils.actor.animShow
import com.romander.navfenixgater.game.utils.actor.disable
import com.romander.navfenixgater.game.utils.actor.enable
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.font.FontParameter
import com.romander.navfenixgater.game.utils.gdxGame
import com.romander.navfenixgater.util.log
import kotlin.collections.forEach

abstract class ACalculatorAnimator(override val screen: AdvancedScreen): AdvancedGroup() {

    protected val fontParameter = FontParameter()
    protected val font29        by lazy { screen.fontGenerator_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(29)) }
    protected val font29_Reg    by lazy { screen.fontGenerator_Reg.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(29)) }

    protected val ls29     by lazy { Label.LabelStyle(font29, GameColor.black_39) }
    protected val ls29_Reg by lazy { Label.LabelStyle(font29_Reg, GameColor.black_39) }

    private val lblInputTitle by lazy { Label("", ls29) }

    private var startInputY       = 0f
    private var selectedInputText = Actor()

    override fun addActorsOnGroup() {
        addLblInputTitle()

        gdxGame.activity.mapBlockKeyboardHeight.put("ACalculatorAnimator") { isVisible, _ ->
            if (isVisible.not()) {
                (screen.stageUI.keyboardFocus as? TextField)?.isDisabled = true
                screen.stageUI.unfocusAll()
                animInputHide()
            }
        }
    }

    override fun dispose() {
        gdxGame.activity.mapBlockKeyboardHeight.remove("ACalculatorAnimator")
        super.dispose()
    }

    private fun addLblInputTitle() {
        addActor(lblInputTitle)
        lblInputTitle.setBounds(0f, 802f, 609f, 38f)
        lblInputTitle.color.a = 0f
    }

    // Logic ----------------------------------------------------------

    private var myChildrens = listOf<Actor>()

    protected fun Actor.animInputToEnterText(title: String, block: () -> List<Actor>) {
        myChildrens = block()
        myChildrens.forEach {
            it.disable()
            it.color.a = 0f
        }

        lblInputTitle.setText(title)
        lblInputTitle.clearActions()
        lblInputTitle.animShow(0.3f)

        startInputY       = this.y
        selectedInputText = this
        selectedInputText.clearActions()
        selectedInputText.animMoveTo(0f, 701f, 0.3f, Interpolation.sineOut)
    }

    private fun animInputHide() {
        myChildrens.forEach {
            it.color.a = 1f
            it.enable()
        }

        lblInputTitle.setText("")
        lblInputTitle.clearActions()
        lblInputTitle.animHide(0.3f)

        selectedInputText.clearActions()
        selectedInputText.animMoveTo(0f, startInputY, 0.3f, Interpolation.sineOut)
    }

}