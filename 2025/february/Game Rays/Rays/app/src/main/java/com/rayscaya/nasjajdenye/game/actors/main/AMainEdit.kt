package com.rayscaya.nasjajdenye.game.actors.main

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.rayscaya.nasjajdenye.game.actors.AInputText
import com.rayscaya.nasjajdenye.game.data.DataInput
import com.rayscaya.nasjajdenye.game.screens.EditScreen
import com.rayscaya.nasjajdenye.game.utils.Block
import com.rayscaya.nasjajdenye.game.utils.GameColor
import com.rayscaya.nasjajdenye.game.utils.TIME_ANIM_SCREEN
import com.rayscaya.nasjajdenye.game.utils.actor.animDelay
import com.rayscaya.nasjajdenye.game.utils.actor.animHide
import com.rayscaya.nasjajdenye.game.utils.actor.animShow
import com.rayscaya.nasjajdenye.game.utils.actor.setOnClickListener
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedMainGroup
import com.rayscaya.nasjajdenye.game.utils.font.FontParameter
import com.rayscaya.nasjajdenye.game.utils.gdxGame
import com.rayscaya.nasjajdenye.game.utils.isNumTake

class AMainEdit(
    override val screen: EditScreen,
): AdvancedMainGroup() {

    companion object {
        var GLOBAL_editDataInput: DataInput? = null
    }

    //private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    //private val font54 = screen.fontGenerator_Regular.generateFont(parameter.setSize(54))
    //private val ls54 = Label.LabelStyle(font54, GameColor.black_2E)

    private val imgInput     = Image(gdxGame.assetsAll.INPUT)
    private val inputTitle   = AInputText(screen, Input.OnscreenKeyboardType.Default)
    private val inputPeriod  = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)
    private val inputPercent = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)
    private val inputSumma   = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)

    // Field
    private val falseInputData = DataInput("", 0, 0, 0)
    private val inputData      = DataInput("", 0, 0, 0)

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        GLOBAL_editDataInput?.let {
            inputData.title   = it.title
            inputData.period  = it.period
            inputData.percent = it.percent
            inputData.summa   = it.summa

            inputTitle.textField.text = inputData.title
            inputPeriod.textField.text = inputData.period.toString()
            inputPercent.textField.text = inputData.percent.toString()
            inputSumma.textField.text = inputData.summa.toString()
        }

        addImgInput()
        addInputs()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgInput() {
        addActor(imgInput)
        imgInput.setBounds(0f, 410f, 928f, 1194f)

        val aCancel = Actor()
        val aOk     = Actor()
        addActors(aCancel, aOk)
        aCancel.apply {
            setBounds(0f, 453f, 281f, 114f)
            setOnClickListener(gdxGame.soundUtil) { screen.hideScreen { gdxGame.navigationManager.back() } }
        }
        aOk.apply {
            setBounds(767f, 453f, 161f, 114f)
            setOnClickListener(gdxGame.soundUtil) {
                saveDataInput()
                screen.hideScreen { gdxGame.navigationManager.back() }
            }
        }
    }

    private fun addInputs() {
        addActors(inputTitle, inputPeriod, inputPercent, inputSumma)
        inputTitle.apply {
            setBounds(49f, 1441f, 829f, 51f)
            blockTextFieldListener = { text -> inputData.title = text.take(20) }
        }
        inputPeriod.apply {
            setBounds(49f, 1192f, 829f, 51f)
            blockTextFieldListener = { text -> inputData.period = text.isNumTake(2) }
        }
        inputPercent.apply {
            setBounds(49f, 943f, 829f, 51f)
            blockTextFieldListener = { text -> inputData.percent = text.isNumTake(3) }
        }
        inputSumma.apply {
            setBounds(49f, 694f, 829f, 51f)
            blockTextFieldListener = { text -> inputData.summa = text.isNumTake(6) }
        }

    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        GLOBAL_editDataInput = null

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ------------------------------------------------

    private fun saveDataInput() {
        if (falseInputData.title   != inputData.title   &&
            falseInputData.period  != inputData.period  &&
            falseInputData.percent != inputData.percent &&
            falseInputData.summa   != inputData.summa
        ) {
            gdxGame.ds_DataInput.update { list ->
                val mList = list.toMutableList()
                mList.add(inputData)
                mList
            }
        } else {
            // Fail
        }
    }

}