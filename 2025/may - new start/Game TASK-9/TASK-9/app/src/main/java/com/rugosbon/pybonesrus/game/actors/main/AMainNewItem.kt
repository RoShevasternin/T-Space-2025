package com.rugosbon.pybonesrus.game.actors.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.rugosbon.pybonesrus.game.actors.AInputText
import com.rugosbon.pybonesrus.game.actors.AScrollPane
import com.rugosbon.pybonesrus.game.actors.ATmpGroup
import com.rugosbon.pybonesrus.game.actors.autoLayout.AVerticalGroup
import com.rugosbon.pybonesrus.game.actors.button.AButton
import com.rugosbon.pybonesrus.game.actors.checkbox.ACheckBox
import com.rugosbon.pybonesrus.game.data.DataItem
import com.rugosbon.pybonesrus.game.screens.NewItemScreen
import com.rugosbon.pybonesrus.game.utils.*
import com.rugosbon.pybonesrus.game.utils.actor.animDelay
import com.rugosbon.pybonesrus.game.utils.actor.animHide
import com.rugosbon.pybonesrus.game.utils.actor.animShow
import com.rugosbon.pybonesrus.game.utils.actor.disable
import com.rugosbon.pybonesrus.game.utils.actor.enable
import com.rugosbon.pybonesrus.game.utils.actor.setBounds
import com.rugosbon.pybonesrus.game.utils.actor.setOnClickListener
import com.rugosbon.pybonesrus.game.utils.actor.setOnTouchListener
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedGroup
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedMainGroup
import com.rugosbon.pybonesrus.game.utils.font.FontParameter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AMainNewItem(
    override val screen: NewItemScreen,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(51))
    private val font82        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(82))
    private val font40        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(40))

    private val ls51 = Label.LabelStyle(font51, GameColor.black_21)
    private val ls40 = Label.LabelStyle(font40, GameColor.black_21)

    private val imgLoga   = Image(gdxGame.assetsAll.loga)

    // Group 1
    private val tmpGroup1 = ATmpGroup(screen)
    private val lblTitle  = Label("Новый пункт", ls51)
    private val imgSepar  = Image(screen.drawerUtil.getTexture(Color.valueOf("929292")))
    private val btnSave   = AButton(screen, AButton.Type.Save)
    private val btnX      = AButton(screen, AButton.Type.X)
    private val box       = ACheckBox(screen, ACheckBox.Type.RosDoh)
    private val imgInput  = Image(gdxGame.assetsAll.INPUTE)

    private val inputSuma   = AInputText(screen, Input.OnscreenKeyboardType.NumberPad, font82)
    private val inputComent = AInputText(screen, Input.OnscreenKeyboardType.Default, font40)

    private val aCategory   = Actor()
    private val lblCategory = Label("", ls40)

    // Group 2
    private val tmpGroup2 = ATmpGroup(screen)
    private val lblTitle2 = Label("Категории", ls51)
    private val btnX2     = AButton(screen, AButton.Type.X)
    private val listImgCategory = List(15) { Image(gdxGame.assetsAll.listCategory[it]) }
    private val aVerticalGroup  = AVerticalGroup(screen, 19f, isWrap = true)
    private val aScroll         = AScrollPane(aVerticalGroup)

    // field
    private var isDohod = false
    private var inputDataItem = DataItem()

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgLoga()

        addAndFillActor(tmpGroup1)
        tmpGroup1.addTitle()
        tmpGroup1.addBtnSave()
        tmpGroup1.addBox()
        tmpGroup1.addPanels()

        addAndFillActor(tmpGroup2)
        tmpGroup2.color.a = 0f
        tmpGroup2.disable()

        tmpGroup2.addTitle2()
        tmpGroup2.addCategory()

        animShowMain()
    }

    override fun dispose() {
        Gdx.input.setOnscreenKeyboardVisible(false)
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoga() {
        addActor(imgLoga)
        imgLoga.setBounds(65f, 1911f, 354f, 129f)
    }

    // Actors toGroup1 ------------------------------------------------------------------------

    private fun AdvancedGroup.addTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(311f, 1772f, 347f, 71f)

        addActor(btnX)
        btnX.apply {
            setBounds(847f, 1769f, 77f, 77f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedGroup.addBtnSave() {
        addActor(btnSave)
        btnSave.setBounds(45f, 64f, 879f, 129f)

        btnSave.setOnClickListener {
            if (checkAndSaveDataItem()) screen.hideScreen {
                gdxGame.navigationManager.back()
            } else {
                gdxGame.soundUtil.apply { play(fail) }
            }
        }
    }

    private fun AdvancedGroup.addBox() {
        addActor(box)
        box.setBounds(34f, 1554f, 901f, 167f)

        box.setOnCheckListener { isDohod ->
            this@AMainNewItem.isDohod = isDohod
            inputDataItem.isDohod = isDohod
        }
    }

    private fun AdvancedGroup.addPanels() {
        addActor(imgSepar)
        imgSepar.setBounds(43f, 1334f, 881f, 4f)

        addActor(imgInput)
        imgInput.setBounds(43f, 877f, 882f, 353f)

        addActors(inputSuma, inputComent)
        inputSuma.apply {
            setBounds(45f, 1336f, 879f, 113f)
            textField.text = "100"

            var currentText = ""
            blockTextFieldListener = { text ->
                if (text.length <= 6) currentText = text
                textField.text = currentText
            }
        }
        inputComent.apply {
            setBounds(45f, 880f, 877f, 55f)

            var currentText = ""
            blockTextFieldListener = { text ->
                if (text.length <= 15) currentText = text
                textField.text = currentText
            }
        }

        addActors(lblCategory, aCategory)
        lblCategory.apply {
            setBounds(45f, 1093f, 877f, 55f)
            setAlignment(Align.center)
        }
        aCategory.apply {
            setBounds(45f, 1093f, 877f, 121f)
            setOnClickListener(gdxGame.soundUtil) {
                stage?.keyboardFocus = null
                Gdx.input.setOnscreenKeyboardVisible(false)
                showCategory()
            }
        }
    }

    // Actors toGroup2 ------------------------------------------------------------------------

    private fun AdvancedGroup.addTitle2() {
        addActor(lblTitle2)
        lblTitle2.setBounds(345f, 1772f, 345f, 71f)

        addActor(btnX2)
        btnX2.apply {
            setBounds(847f, 1769f, 77f, 77f)
            setOnClickListener { hideCategory() }
        }
    }

    private fun AdvancedGroup.addCategory() {
        addActor(aScroll)
        aScroll.setBounds(34f, 0f, 901f, 1721f)

        aVerticalGroup.setSize(901f, 1721f)

        listImgCategory.forEachIndexed { index, img ->
            img.setSize(901f, 157f)
            aVerticalGroup.addActor(img)

            img.setOnTouchListener(gdxGame.soundUtil) {
                inputDataItem.categoryIndex = index
                lblCategory.setText(GLOBAL_listCategoryName[index])
                hideCategory()
            }
        }

    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic -----------------------------------------------

    private fun showCategory() {
        tmpGroup1.animHide(0.2f)
        tmpGroup1.disable()

        tmpGroup2.animShow(0.2f)
        tmpGroup2.enable()
    }

    private fun hideCategory() {
        tmpGroup2.animHide(0.2f)
        tmpGroup2.disable()

        tmpGroup1.animShow(0.2f)
        tmpGroup1.enable()
    }

    private fun checkAndSaveDataItem(): Boolean {
        inputDataItem.date   = getCurrentTime()
        inputDataItem.summa  = inputSuma.textField.text.isNumTake(6)
        inputDataItem.coment = inputComent.textField.text

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

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date())
    }

}