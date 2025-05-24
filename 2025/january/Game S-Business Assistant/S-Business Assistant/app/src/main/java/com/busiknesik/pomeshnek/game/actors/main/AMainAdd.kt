package com.busiknesik.pomeshnek.game.actors.main

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.busiknesik.pomeshnek.game.actors.AInputText
import com.busiknesik.pomeshnek.game.data.TovarData
import com.busiknesik.pomeshnek.game.utils.*
import com.busiknesik.pomeshnek.game.utils.actor.animHide
import com.busiknesik.pomeshnek.game.utils.actor.animShow
import com.busiknesik.pomeshnek.game.utils.actor.setOnClickListener
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedGroup
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.util.log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AMainAdd(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val imgBrand  = Image(gdxGame.assetsAll.brand)
    private val imgInput  = Image(gdxGame.assetsAll.edit_tovar)
    private val btnSave   = Actor()

    private val inputName    = AInputText(screen, Input.OnscreenKeyboardType.Default)
    private val inputCount   = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)
    private val inputCost    = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)
    private val inputProdaja = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)
    private val inputDop     = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)

    private val falseInputData = InputData("", 0, 0, 0, 0) //TovarData("", getCurrentDate(), 0,0,0,0)
    private val inputData      = InputData("", 0, 0, 0, 0) //TovarData("", getCurrentDate(), 0,0,0,0)

    private data class InputData(
        var nName  : String,
        var count  : Int,
        var cost   : Int,
        var prodaja: Int,
        var dop    : Int,
    )

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgBrand()
        addImgInput()
        addBtnSave()
        addInputText()

        animShowMain()

        handleKeyboard()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgBrand() {
        addActor(imgBrand)
        imgBrand.setBounds(55f, 2149f, 1081f, 383f)
    }

    private fun addImgInput() {
        addActor(imgInput)
        imgInput.setBounds(0f, 0f, 1191f, 2023f)
    }

    private fun addBtnSave() {
        addActor(btnSave)
        btnSave.setBounds(119f, 63f, 952f, 127f)
        btnSave.setOnClickListener(gdxGame.soundUtil) {
            if (handlerSave()) screen.hideScreen { gdxGame.navigationManager.back() }
        }
    }

    private fun addInputText() {
        addActors(inputName, inputCount, inputCost, inputProdaja, inputDop)
        inputName.apply {
            setBounds(165f, 1703f, 861f, 158f)
            blockTextFieldListener = { text -> inputData.nName = text.take(20) }
        }
        inputCount.apply {
            setBounds(165f, 1356f, 861f, 158f)
            blockTextFieldListener = { text -> inputData.count = text.isNumTake(4) }
        }
        inputCost.apply {
            setBounds(165f, 1011f, 861f, 158f)
            blockTextFieldListener = { text -> inputData.cost = text.isNumTake(8) }
        }
        inputProdaja.apply {
            setBounds(165f, 664f, 861f, 158f)
            blockTextFieldListener = { text -> inputData.prodaja = text.isNumTake(8) }
        }
        inputDop.apply {
            setBounds(165f, 317f, 861f, 158f)
            blockTextFieldListener = { text -> inputData.dop = text.isNumTake(6) }
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
                    input == inputProdaja && newY > 664f -> this.y = (newY - 664f)
                    input == inputDop     && newY > 317f -> this.y = (newY - 317f)

                    else -> y = 0f
                }
            }
        }
    }

    private fun handlerSave(): Boolean {
        log("data = $inputData")
        return if (inputData != falseInputData) {

            val margaPercent = ((inputData.prodaja - ((inputData.cost * inputData.count) + inputData.dop)) / (inputData.prodaja / 100f)).toInt()

            val tovarData = TovarData(
                inputData.nName,
                getCurrentDate(),
                inputData.prodaja,
                inputData.count,
                margaPercent,
                (inputData.prodaja * (margaPercent / 100f)).toInt(),
            )

            gdxGame.ds_Tovar.update { data ->
                val mData = data.toMutableList()
                mData.add(tovarData)
                mData
            }

            true
        } else false
    }

    private fun getCurrentDate(): String {
        val date = Date() // Поточна дата
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) // Формат дати
        return formatter.format(date) // Форматування
    }

}