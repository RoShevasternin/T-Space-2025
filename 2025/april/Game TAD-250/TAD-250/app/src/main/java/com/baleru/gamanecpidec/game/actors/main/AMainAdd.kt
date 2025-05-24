package com.baleru.gamanecpidec.game.actors.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.baleru.gamanecpidec.game.actors.AGelir
import com.baleru.gamanecpidec.game.actors.AHarcamalar
import com.baleru.gamanecpidec.game.actors.AMenu
import com.baleru.gamanecpidec.game.actors.button.AButton
import com.baleru.gamanecpidec.game.actors.checkbox.ACheckBox
import com.baleru.gamanecpidec.game.actors.checkbox.ACheckBoxGroup
import com.baleru.gamanecpidec.game.data.DataItem
import com.baleru.gamanecpidec.game.screens.AddScreen
import com.baleru.gamanecpidec.game.screens.HistoryScreen
import com.baleru.gamanecpidec.game.screens.MenuScreen
import com.baleru.gamanecpidec.game.utils.Block
import com.baleru.gamanecpidec.game.utils.GameColor
import com.baleru.gamanecpidec.game.utils.TIME_ANIM_SCREEN
import com.baleru.gamanecpidec.game.utils.actor.*
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedMainGroup
import com.baleru.gamanecpidec.game.utils.font.FontParameter
import com.baleru.gamanecpidec.game.utils.gdxGame
import java.text.SimpleDateFormat
import java.util.*

class AMainAdd(
    override val screen: AddScreen,
    val aMenu: AMenu,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font53        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(53))
    private val font53r       = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(53))
    private val font44        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(44))

    private val ls53   = Label.LabelStyle(font53, GameColor.blue_06)
    private val ls53rw = Label.LabelStyle(font53r, Color.WHITE)

    private val lblTitle = Label("Bir kategori seçin", ls53)

    private val boxHarcamalar = ACheckBox(screen, ACheckBox.Type.ZA_VSE_ZA_SEG)
    private val boxGelir      = ACheckBox(screen, ACheckBox.Type.ZA_VSE_ZA_SEG)

    private val imgInput = Image(gdxGame.assetsAll.input)

    private val inputComment = Actor()//AInputText(screen, Input.OnscreenKeyboardType.Default, font44)
    private val inputSumma   = Actor()//AInputText(screen, Input.OnscreenKeyboardType.NumberPad, font44)

    private val btnSave = AButton(screen, AButton.Type.Back)

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

//            var currentText = ""
//            blockTextFieldListener = { text ->
//                if (text.length <= 6) currentText = text
//                textField.text = currentText
//            }
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
//        inputDataItem.summa    = inputSumma.textField.text.isNumTake(6)
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
        aMenu.blockPolza = {
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