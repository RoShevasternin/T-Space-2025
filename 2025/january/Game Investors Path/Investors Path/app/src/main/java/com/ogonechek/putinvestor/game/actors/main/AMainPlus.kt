package com.ogonechek.putinvestor.game.actors.main

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ogonechek.putinvestor.game.actors.AInputText
import com.ogonechek.putinvestor.game.actors.APanel
import com.ogonechek.putinvestor.game.actors.checkbox.ACheckBox
import com.ogonechek.putinvestor.game.actors.checkbox.ACheckBoxGroup
import com.ogonechek.putinvestor.game.data.InvestData
import com.ogonechek.putinvestor.game.utils.*
import com.ogonechek.putinvestor.game.utils.actor.animHide
import com.ogonechek.putinvestor.game.utils.actor.animShow
import com.ogonechek.putinvestor.game.utils.actor.setOnClickListener
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedGroup
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.util.log

class AMainPlus(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val aPanel    = APanel(screen)
    private val imgAddInv = Image(gdxGame.assetsAll.add_inv)
    private val imgMenu   = Image(gdxGame.assetsAll.add_panel)
    private val listDate  = List(12) { ACheckBox(screen, ACheckBox.Type.DATE) }

    private val inputName    = AInputText(screen, Input.OnscreenKeyboardType.Default)
    private val inputPercent = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)
    private val inputSumma   = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)

    private val falseInvestData = InvestData("", 1, 0, 0)
    private val investData      = InvestData("", 1, 0, 0)

    override fun addActorsOnGroup() {
        color.a = 0f

        addPanel()
        addAddInv()
        addMenu()
        addListDate()
        addInputText()

        animShowMain()

        handleKeyboard()
    }

    // Actors ------------------------------------------------------------------------

    private fun addPanel() {
        addActor(aPanel)
        aPanel.setBounds(28f, 2106f, 1306f, 725f)
    }

    private fun addAddInv() {
        addActor(imgAddInv)
        imgAddInv.setBounds(72f, 2135f, 908f, 74f)
    }

    private fun addMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(0f, 67f, 1362f, 2017f)

        val aCancel = Actor()
        val aNext   = Actor()
        addActors(aCancel, aNext)
        aCancel.setBounds(21f, 144f, 287f, 132f)
        aNext.setBounds(878f, 141f, 410f, 138f)

        aCancel.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
        aNext.setOnClickListener(gdxGame.soundUtil) {
            if (handlerNext()) screen.hideScreen { gdxGame.navigationManager.back() }
        }
    }

    private fun addListDate() {
        var nx = 58f
        val cbg = ACheckBoxGroup()
        listDate.onEachIndexed { index, box ->
            addActor(box)
            box.checkBoxGroup = cbg
            box.setBounds(nx, 1281f, 90f, 90f)
            nx += 15 + 90

            if (index == 0) box.check()

            box.setOnCheckListener { isCheck ->
                if (isCheck) investData.period = index.inc()
            }
        }
    }

    private fun addInputText() {
        addActors(inputName, inputPercent, inputSumma)
        inputName.apply {
            setBounds(110f, 1733f, 1142f, 127f)
            blockTextFieldListener = { text -> investData.dName = text }
        }
        inputPercent.apply {
            setBounds(110f, 867f, 1142f, 127f)
            blockTextFieldListener = { text -> investData.percent = text.isNumTake(3) }
        }
        inputSumma.apply {
            setBounds(110f, 433f, 1142f, 127f)
            blockTextFieldListener = { text -> investData.summa = text.isNumTake(7) }
        }
    }

    // Anim Main ------------------------------------------------

    private fun animShowMain() {
        animShow(TIME_ANIM_SCREEN)
    }

    fun animHideMain(block: Block = Block {}) {
        animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

    // Logic --------------------------------------------------------------------

    private fun handleKeyboard() {
        gdxGame.activity.blockKeyboardHeight.add { heightKeyboard ->
            val input = stage?.keyboardFocus?.parent

            if (input == null) {
                y = 0f
                return@add
            }

            val screenCoord = Vector2(0f, heightKeyboard.toFloat())
            stage.viewport.unproject(screenCoord)

            val newY = (HEIGHT_UI - screenCoord.y)

            if (input is AInputText) {
                when {
                    input == inputPercent && newY > 857f -> this.y = (newY - 857f)
                    input == inputSumma   && newY > 424f -> this.y = (newY - 424f)

                    else -> y = 0f
                }
            }
        }
    }

    private fun handlerNext(): Boolean {
        log("data = $investData")
        return if (investData != falseInvestData) {
            // Save Data

            gdxGame.ds_Invest.update { data ->
                val mData = data.toMutableList()
                mData.add(investData)
                mData
            }

            true
        } else false
    }

}