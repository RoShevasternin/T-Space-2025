package com.padrestoranom.easypaydonalds.game.actors.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.padrestoranom.easypaydonalds.game.actors.AGelir
import com.padrestoranom.easypaydonalds.game.actors.AHarcamalar
import com.padrestoranom.easypaydonalds.game.actors.AInputText
import com.padrestoranom.easypaydonalds.game.actors.AMenu
import com.padrestoranom.easypaydonalds.game.actors.button.AButton
import com.padrestoranom.easypaydonalds.game.actors.button.ATextButton
import com.padrestoranom.easypaydonalds.game.actors.checkbox.ACheckBox
import com.padrestoranom.easypaydonalds.game.actors.checkbox.ACheckBoxGroup
import com.padrestoranom.easypaydonalds.game.data.DataItem
import com.padrestoranom.easypaydonalds.game.screens.AddScreen
import com.padrestoranom.easypaydonalds.game.screens.HistoryScreen
import com.padrestoranom.easypaydonalds.game.screens.MenuScreen
import com.padrestoranom.easypaydonalds.game.utils.*
import com.padrestoranom.easypaydonalds.game.utils.actor.animDelay
import com.padrestoranom.easypaydonalds.game.utils.actor.animHide
import com.padrestoranom.easypaydonalds.game.utils.actor.animShow
import com.padrestoranom.easypaydonalds.game.utils.actor.disable
import com.padrestoranom.easypaydonalds.game.utils.actor.enable
import com.padrestoranom.easypaydonalds.game.utils.actor.setOnClickListener
import com.padrestoranom.easypaydonalds.game.utils.actor.setOnTouchListener
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedGroup
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedMainGroup
import com.padrestoranom.easypaydonalds.game.utils.font.FontParameter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AMainAdd(
    override val screen: AddScreen,
    val aMenu: AMenu,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font53        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(53))
    private val font53r       = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(53))
    private val font44        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(44))

    private val ls53   = Label.LabelStyle(font53, GameColor.black_42)
    private val ls53rw = Label.LabelStyle(font53r, Color.WHITE)

    private val lblTitle = Label("Bir kategori seçin", ls53)

    private val boxHarcamalar = ACheckBox(screen, ACheckBox.Type.Harcamalar)
    private val boxGelir      = ACheckBox(screen, ACheckBox.Type.Gelir)

    private val imgInput = Image(gdxGame.assetsAll.input)

    private val inputComment = AInputText(screen, Input.OnscreenKeyboardType.Default, font44)
    private val inputSumma   = AInputText(screen, Input.OnscreenKeyboardType.NumberPad, font44)

    private val btnSave = ATextButton(screen, "başla", ls53rw, AButton.Type.Blue)

    private val aHarcamalar = AHarcamalar(screen)
    private val aGelir      = AGelir(screen)

    // field
    private var isIncome      = false
    private var inputDataItem = DataItem()

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addLblTitle()
        addInput()
        addBtnSave()
        addBox()

        addAHarcamalar()
        addAGelir()

        animShowMain()

        handlerAMenu()
    }

    override fun dispose() {
        Gdx.input.setOnscreenKeyboardVisible(false)
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(33f, 1647f, 668f, 248f)
    }

    private fun addInput() {
        addActor(imgInput)
        imgInput.setBounds(84f, 549f, 668f, 248f)

        addActors(inputComment, inputSumma)
        inputComment.apply {
            setBounds(84f, 702f, 667f, 52f)

//            var currentText = ""
//            blockTextFieldListener = { text ->
//                if (text.length <= 15) currentText = text
//                textField.text = currentText
//            }
        }
        inputSumma.apply {
            setBounds(84f, 551f, 667f, 52f)

            var currentText = ""
            blockTextFieldListener = { text ->
                if (text.length <= 6) currentText = text
                textField.text = currentText
            }
        }
    }

    private fun addBtnSave() {
        addActor(btnSave)
        btnSave.setBounds(44f, 255f, 744f, 111f)

        btnSave.setOnClickListener {
            if (checkAndSaveDataItem()) screen.hideScreen {
                gdxGame.navigationManager.back()
            } else {
                gdxGame.soundUtil.apply { play(fail) }
            }
        }
    }

    private fun addBox() {
        addActors(boxHarcamalar, boxGelir)
        boxHarcamalar.setBounds(86f, 1506f, 324f, 82f)
        boxGelir.setBounds(588f, 1506f, 157f, 82f)

        val cbg = ACheckBoxGroup()

        boxHarcamalar.checkBoxGroup = cbg
        boxGelir.checkBoxGroup = cbg

        boxHarcamalar.check()

        boxHarcamalar.setOnCheckListener { isCheck ->
            if (isCheck) {
                hideGelir()
                this@AMainAdd.isIncome = false
            }
        }
        boxGelir.setOnCheckListener { isCheck ->
            if (isCheck) {
                showGelir()
                this@AMainAdd.isIncome = true
            }
        }
    }

    private fun addAHarcamalar() {
        addActor(aHarcamalar)
        aHarcamalar.setBounds(96f, 944f, 640f, 527f)

        aHarcamalar.blockIndex = { inputDataItem.categoryIndex = it }
    }

    private fun addAGelir() {
        addActor(aGelir)
        aGelir.color.a = 0f
        aGelir.disable()
        aGelir.setBounds(96f, 944f, 640f, 527f)

        aGelir.blockIndex = { inputDataItem.categoryIndex = it }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic -----------------------------------------------

    private fun showGelir() {
        aHarcamalar.animHide(0.2f)
        aHarcamalar.disable()

        aGelir.animShow(0.2f)
        aGelir.enable()

        inputDataItem.categoryIndex = -1
    }

    private fun hideGelir() {
        aGelir.animHide(0.2f)
        aGelir.disable()

        aHarcamalar.animShow(0.2f)
        aHarcamalar.enable()

        inputDataItem.categoryIndex = -1
    }

    private fun checkAndSaveDataItem(): Boolean {
        inputDataItem.date     = getFormattedDate()
        inputDataItem.summa    = inputSumma.textField.text.isNumTake(6)
        inputDataItem.isIncome = this.isIncome

        if (
            inputDataItem.summa         != 0  &&
            inputDataItem.categoryIndex != -1
        ) {
            gdxGame.ds_ItemData.update {
                val mList = it.toMutableList()
                mList.add(inputDataItem)
                mList
            }
            return true
        } else return false
    }

    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }


    private fun handlerAMenu() {
        aMenu.blockHome = {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
        aMenu.blockPlus = {}
        aMenu.blockHistory = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
    }

}