package com.kaskazer.kazmuchero.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.kaskazer.kazmuchero.game.actors.AButton
import com.kaskazer.kazmuchero.game.actors.AClickGaz
import com.kaskazer.kazmuchero.game.actors.APanelBalance
import com.kaskazer.kazmuchero.game.actors.APanelLVL
import com.kaskazer.kazmuchero.game.utils.*
import com.kaskazer.kazmuchero.game.utils.actor.animHide
import com.kaskazer.kazmuchero.game.utils.actor.animShow
import com.kaskazer.kazmuchero.game.utils.actor.setOnClickListenerWithPos
import com.kaskazer.kazmuchero.game.utils.actor.setPosition
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedStage
import com.kaskazer.kazmuchero.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class AbstractLVLScreen : AdvancedScreen() {

    abstract val valueClick: Int
    abstract val valueLVL  : Int

    private val fontParameter = FontParameter()
    private val font66        = fontGenerator_AlbertSans.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "+").setSize(66))

    private val ls66 = LabelStyle(font66, Color.WHITE)

    private val listClickGaz by lazy { List(75) { AClickGaz(this, valueClick, ls66) } }

    // Actors
    private val panelBalance by lazy { APanelBalance(this) }
    private val btnDukan     by lazy { AButton(this, AButton.Type.Dukan) }
    private val panelLVL     by lazy { APanelLVL(this, valueLVL) }

    // Field

    private var currentClickGazIndex = 0

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.background_game.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)

        uiBackgroundImage.setOnClickListenerWithPos { _, pos ->
            game.soundUtil.apply { play(listBubble.random(), 0.05f) }

            panelBalance.updateGaz { it + valueClick }

            if (currentClickGazIndex == listClickGaz.lastIndex) currentClickGazIndex = 0

            listClickGaz[currentClickGazIndex++].apply {
                setPosition(pos.sub(95f, 56f))
                launchGaz()
            }
        }
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        addPanelBalance()
        addPanelLVL()
        addBtnDukan()

        addActorsOnStageUI_2()

        addClickGaz()
    }

    abstract fun AdvancedStage.addActorsOnStageUI_2()

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addPanelBalance() {
        addActor(panelBalance)
        panelBalance.setBounds(82f, 2035f, 879f, 126f)
        panelBalance.disable()

        panelBalance.apply {
            updateGaz { game.dataStore.gaz }
            updateCoin { game.dataStore.coin }
        }
    }

    private fun AdvancedStage.addPanelLVL() {
        addActor(panelLVL)
        panelLVL.setBounds(0f, 55f, 279f, 168f)
        panelLVL.disable()
    }

    private fun AdvancedStage.addBtnDukan() {
        addActor(btnDukan)
        btnDukan.setBounds(709f, 55f, 279f, 279f)

        btnDukan.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addClickGaz() {
        listClickGaz.onEach { gaz ->
            addActor(gaz)
            gaz.disable()
            gaz.setBounds(WIDTH_UI + 100, 0f, 190f, 112f)
        }
    }

    // Logic ----------------------------------------------------------------------

    private fun AClickGaz.launchGaz() {
        coroutine?.launch {
            delay(500)
            runGDX {
                animHide(TIME_ANIM) {
                    setPosition(WIDTH_UI+100f, 0f)
                    animShow()
                }
            }
        }
    }

}