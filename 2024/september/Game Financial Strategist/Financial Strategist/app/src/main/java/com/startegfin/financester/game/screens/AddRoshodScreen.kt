package com.startegfin.financester.game.screens

import android.text.InputType
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.startegfin.financester.game.LibGDXGame
import com.startegfin.financester.game.actors.AButton
import com.startegfin.financester.game.actors.ARashodItemGroup
import com.startegfin.financester.game.actors.TmpGroup
import com.startegfin.financester.game.data.Roshod
import com.startegfin.financester.game.global.Balance
import com.startegfin.financester.game.global.CalendarUtil
import com.startegfin.financester.game.global.toBalance
import com.startegfin.financester.game.utils.GColor
import com.startegfin.financester.game.utils.TIME_ANIM
import com.startegfin.financester.game.utils.actor.animHide
import com.startegfin.financester.game.utils.actor.animShow
import com.startegfin.financester.game.utils.actor.setOnClickListener
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen
import com.startegfin.financester.game.utils.advanced.AdvancedStage
import com.startegfin.financester.game.utils.disable
import com.startegfin.financester.game.utils.enable
import com.startegfin.financester.game.utils.font.FontParameter

class AddRoshodScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter   = FontParameter()
    private val fontExtraBold88 = fontGenerator_NunitoSans_10pt_ExtraBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "-₽").setSize(88))
    private val fontSemiBold32  = fontGenerator_NunitoSans_10pt_SemiBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(32))

    private val lsExtraBold88 = Label.LabelStyle(fontExtraBold88, GColor.text.cpy().apply { a = 0.70f })
    private val lsSemiBold32  = Label.LabelStyle(fontSemiBold32, GColor.text)

    // Actors

    private val imgLogo  = Image(game.all.logo)
    private val tmpGroup = TmpGroup(this)

    private val imgBox       = Image(game.all.roshod)
    private val btnBack      = AButton(this, AButton.Static.Type.Back)
    private val imgMainPanel = Image(game.all.TRANSACTION_PANEL)
    private val imgCategory  = Image(game.all.what)
    private val lblSuma      = Label("0 ₽", lsExtraBold88)
    private val lblData      = Label(CalendarUtil.currentDate, lsSemiBold32)
    private val lblCategory  = Label("Выбрать категорию", lsSemiBold32)
    private val lblDesc      = Label("Добавить описание", lsSemiBold32)
    private val btnSave      = AButton(this, AButton.Static.Type.Save)
    private val itemsPanel   = ARashodItemGroup(this)
    private val imgForeground = Image(drawerUtil.getRegion(GColor.foreground))
    private val aDohod        = Actor()

    // Field

    private var enteredSuma          = 0
    private var enteredCategoryIndex = -1

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgLogo)
        imgLogo.setBounds(40f,1439f,321f,81f)

        addAndFillActors(tmpGroup)
        tmpGroup.apply {
            color.a = 0f

            addImgBox()
            addImgMainPanel()
            addImgCategory()
            addBtnBack()
            addLblSuma()
            addLbls()
            addBtnSave()
            addADohod()

            animShow(TIME_ANIM)
        }

        addImgForeground()
        addItemsPanel()
    }

    private fun AdvancedGroup.addImgBox() {
        addActor(imgBox)
        imgBox.setBounds(199f,1333f,357f,81f)
    }

    private fun AdvancedGroup.addImgMainPanel() {
        addActor(imgMainPanel)
        imgMainPanel.setBounds(32f,599f,690f,708f)
    }

    private fun AdvancedGroup.addImgCategory() {
        addActor(imgCategory)
        imgCategory.setBounds(100f,930f,40f,40f)
    }

    private fun AdvancedGroup.addBtnBack() {
        addActor(btnBack)
        btnBack.apply {
            setBounds(36f,1334f,79f,80f)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedGroup.addADohod() {
        addActor(aDohod)
        aDohod.apply {
            setBounds(386f,1344f,166f,59f)
            setOnClickListener(game.soundUtil) {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AddDohodScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedGroup.addLblSuma() {
        addActor(lblSuma)
        lblSuma.setBounds(80f,1153f,592f,106f)
        lblSuma.setAlignment(Align.center)
        lblSuma.setOnClickListener(game.soundUtil) {
            showForeground()
            var currentText = if (lblSuma.text.toString() != "0 ₽") lblSuma.text.toString() else ""
            currentText     = removeNonDigits(currentText)
            game.activity.showInputDialog(currentText,"Сумма", "Введите сумму", InputType.TYPE_CLASS_NUMBER,
                { inputText ->
                    hideForeground()
                    var resultText = removeNonDigits(inputText)
                    if (inputText.isEmpty()) resultText = "0"
                    enteredSuma = resultText.toIntOrNull() ?: 0
                    lblSuma.setText("-${(resultText).toInt().toBalance} ₽")
                }, { hideForeground() })
        }
    }

    private fun AdvancedGroup.addLbls() {
        addActors(lblData, lblCategory, lblDesc)
        lblData.setBounds(180f,1053f,170f,39f)
        lblCategory.setBounds(180f,930f,170f,39f)
        lblDesc.setBounds(180f,808f,170f,39f)

        val aData     = Actor()
        val aCategory = Actor()
        val aDesc     = Actor()
        addActors(aData, aCategory, aDesc)
        aData.apply {
            setBounds(80f,1032f,593f,80f)
            setOnClickListener(game.soundUtil) {
                showForeground()
                game.activity.showDatePickerDialog(CalendarUtil.currentDate, { selectedData ->
                    hideForeground()
                    lblData.setText(selectedData)
                }, { hideForeground() })
            }
        }
        aCategory.apply {
            setBounds(80f,909f,593f,80f)
            setOnClickListener(game.soundUtil) {
                showItemsPanel()
            }
        }
        aDesc.apply {
            setBounds(80f,787f,593f,80f)
            setOnClickListener(game.soundUtil) {
                showForeground()
                val currentText = if (lblDesc.text.toString() != "Добавить описание") lblDesc.text.toString() else ""
                game.activity.showInputDialog(currentText, "Описание", "Введите описание", InputType.TYPE_CLASS_TEXT,
                    { inputText ->
                        hideForeground()
                        var resultText = inputText.take(22) + if (inputText.length > 22) "..." else ""
                        if (inputText.isEmpty()) resultText = "Добавить описание"
                        lblDesc.setText(resultText)
                    }, { hideForeground() })
            }
        }
    }

    private fun AdvancedGroup.addBtnSave() {
        addActor(btnSave)
        btnSave.apply {
            setBounds(80f,647f,593f,100f)
            setOnClickListener {
                if (enteredSuma <= 0 || enteredCategoryIndex == -1) {
                    game.soundUtil.apply { play(fail_game) }
                } else {
                    btnSave.disable()
                    game.dataStoreRoshodUtil.update { listRoshod ->
                        listRoshod.toMutableList().apply { add(
                            Roshod(
                                System.currentTimeMillis(),
                                enteredSuma,
                                lblData.text.toString(),
                                enteredCategoryIndex,
                                lblDesc.text.toString()
                            )
                        ) }
                    }
                    Balance.balanceUsed += enteredSuma
                    tmpGroup.animHide(TIME_ANIM) {
                        game.navigationManager.back()
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addItemsPanel() {
        addActor(itemsPanel)
        itemsPanel.apply {
            setBounds(0f,-800f,754f,772f)
            blockSelect = { selectedItemIndex ->
                enteredCategoryIndex = selectedItemIndex
                imgCategory.drawable = TextureRegionDrawable(game.all.listRoshodIcon[selectedItemIndex])
                lblCategory.setText(game.listCategoryRoshod[selectedItemIndex])
                hideItemsPanel()
            }
        }
    }

    private fun AdvancedStage.addImgForeground() {
        addAndFillActor(imgForeground)
        imgForeground.color.a = 0f
        imgForeground.disable()
    }

    // Logic --------------------------------------------------------------------------------------

    private fun showForeground() {
        imgForeground.enable()
        imgForeground.animShow(0.2f)
    }

    private fun hideForeground() {
        imgForeground.animHide(0.2f) {
            imgForeground.disable()
        }
    }

    private fun showItemsPanel() {
        showForeground()
        itemsPanel.addAction(Actions.moveTo(0f, 0f, TIME_ANIM, Interpolation.sineOut))
    }

    private fun hideItemsPanel() {
        itemsPanel.addAction(Actions.sequence(
            Actions.moveTo(0f, -800f, TIME_ANIM, Interpolation.sineIn),
            Actions.run { hideForeground() }
        ))
    }

    fun removeNonDigits(input: String): String {
        return input.replace(Regex("[^0-9]"), "").take(8) // Замінює все, що не є цифрами, на пустий рядок
    }

}