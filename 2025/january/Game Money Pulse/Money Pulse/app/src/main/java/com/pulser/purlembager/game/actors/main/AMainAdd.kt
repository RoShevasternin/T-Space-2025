package com.pulser.purlembager.game.actors.main

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.actors.ABack
import com.pulser.purlembager.game.actors.ADate
import com.pulser.purlembager.game.actors.AKeyboard
import com.pulser.purlembager.game.actors.AName
import com.pulser.purlembager.game.actors.APanel
import com.pulser.purlembager.game.actors.button.AButton
import com.pulser.purlembager.game.dataStore.DS_Transactions
import com.pulser.purlembager.game.dataStore.DataDesire
import com.pulser.purlembager.game.dataStore.DataTransaction
import com.pulser.purlembager.game.dataStore.DataTransactionType
import com.pulser.purlembager.game.screens.AddScreen
import com.pulser.purlembager.game.screens.BlogScreen
import com.pulser.purlembager.game.screens.MenuScreen
import com.pulser.purlembager.game.utils.*
import com.pulser.purlembager.game.utils.actor.animHide
import com.pulser.purlembager.game.utils.actor.animHideSuspend
import com.pulser.purlembager.game.utils.actor.animShow
import com.pulser.purlembager.game.utils.actor.animShowSuspend
import com.pulser.purlembager.game.utils.actor.disable
import com.pulser.purlembager.game.utils.actor.enable
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.font.FontParameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainAdd(
    override val screen: AdvancedScreen,
    val aPanel: APanel
): AdvancedGroup() {

    private val isDesire = AddScreen.ADD_TYPE == AddScreen.AddType.Desire

    private val itemAdd: String = when(AddScreen.ADD_TYPE) {
        AddScreen.AddType.Desire  -> "желание"
        AddScreen.AddType.Income  -> "доход"
        AddScreen.AddType.Expense -> "росход"
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font37        = screen.fontGenerator_SemiBold.generateFont(fontParameter.setSize(37))
    private val fontR_37      = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(37))
    private val font81        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(81))

    private val ls37   = Label.LabelStyle(font37, GameColor.black_31)
    private val lsR_37 = Label.LabelStyle(fontR_37, GameColor.black_31.cpy().apply { a = 0.5f })
    private val ls81   = Label.LabelStyle(font81, GameColor.yellow_FF)

    private val lblName   = Label("Введите название", ls37)
    private val lblSumma  = Label("Введите сумму", lsR_37)
    private val lblDate   = Label("Введите срок", lsR_37)
    private val aName     = AName(screen, ls81)
    private val keyboard  = AKeyboard(screen)
    private val btnNext   = AButton(screen, AButton.Type.Next)
    private val aData     = ADate(screen, ls37)
    private val aBack     = ABack(screen, "Добавить $itemAdd", ABack.Type.Black)

    // Field
    private var selectedLabel = lblSumma
    private var selectedDate  = ""

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addABack()
                addLblName()
                if (isDesire) addLblSummaAndDate() else addLblSumma()
                addAName()
                addKeyboard()
                addBtnNext()
                if (isDesire) addADate()

                handleAPanel()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addABack() {
        addActor(aBack)
        aBack.setBounds(0f, 2367f, 1176f, 63f)
        aBack.blockBack = {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addLblName() {
        addActor(lblName)
        lblName.setBounds(386f, 2096f, 404f, 48f)
    }

    private fun addLblSummaAndDate() {
        addActors(lblSumma, lblDate)
        lblSumma.setBounds(63f, 1509f, 371f, 245f)
        lblDate.setBounds(742f, 1509f, 371f, 245f)

        lblSumma.apply {
            setAlignment(Align.center)
            setOnClickListener {
                selectedLabel = lblSumma
            }
        }
        lblDate.apply {
            setAlignment(Align.center)
            setOnClickListener {
                showADate()
                selectedLabel = lblDate
            }
        }
    }

    private fun addLblSumma() {
        addActor(lblSumma)
        lblSumma.setBounds(89f, 1483f, 338f, 48f)
    }

    private fun addAName() {
        addActor(aName)
        aName.setBounds(62f, 1754f, 1050f, 279f)
    }

    private fun addADate() {
        addActor(aData)
        aData.setBounds(105f, 1398f, 979f, 119f)
        aData.color.a = 0f
        aData.disable()

        aData.blockSelect = {
            selectedDate = it
            hideADate()
        }
    }

    private fun addKeyboard() {
        addActor(keyboard)
        keyboard.setBounds(164f, 368f, 848f, 854f)

        keyboard.apply {
            blockInput = {
                if (selectedLabel.text.contains("В")) selectedLabel.setText(it)
                else {
                    if (selectedLabel.text.replace(" ", "").count() < 8) {
                        if (selectedLabel == lblSumma) selectedLabel.setText((selectedLabel.text.toString() + it).toSeparate())
                        else selectedLabel.setText(selectedLabel.text.toString() + it)
                    }
                }
            }
            blockDrop = {
                if (selectedLabel == lblSumma) selectedLabel.setText(selectedLabel.text.dropLast(1).toString().replace(" ", "").toSeparate())
                else selectedLabel.setText(selectedLabel.text.dropLast(1).toString())
            }
        }
    }

    private fun addBtnNext() {
        addActor(btnNext)
        if (AddScreen.ADD_TYPE == AddScreen.AddType.Desire) btnNext.setBounds(411f, 1345f, 355f, 120f)
        else btnNext.setBounds(732f, 1446f, 355f, 120f)

        btnNext.setOnClickListener {
            if (checkIsAllInput()) {
                val dName : String = aName.lblName.text.toString()
                val dSumma: Int    = lblSumma.text.toString().replace(" ", "").toInt()

                if (isDesire) {
                    val dDate: String = lblDate.text.toString()
                    val dDay : String = selectedDate

                    gdxGame.ds_Desire.update {
                        val mList = it.toMutableList()
                        mList.add(DataDesire(dName, dSumma, dDate, dDay))
                        mList
                    }
                } else {
                    gdxGame.ds_Transaction.update {
                        val mList = it.toMutableList()
                        mList.add(DataTransaction(
                            if (AddScreen.ADD_TYPE == AddScreen.AddType.Income) DataTransactionType.Income else DataTransactionType.Expense,
                            dName, dSumma
                        ))
                        mList
                    }
                }

                screen.hideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            } else {
                // Fail sound
                gdxGame.soundUtil.apply { play(fail) }
            }
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        animShowSuspend(TIME_ANIM_SCREEN)
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Logic ----------------------------------------------------------

    private fun handleAPanel() {
        aPanel.apply {
            blockHome = {
                screen.hideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
            blockBlog = {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(BlogScreen::class.java.name, AddScreen::class.java.name)
                }
            }
        }
    }

    private fun checkIsAllInput(): Boolean {
        if (lblSumma.text.toString() == "Введите сумму") return false

        return if (isDesire) {
            aName.lblName.text.isNotEmpty() && lblSumma.text.isNotEmpty() && lblDate.text.isNotEmpty() &&
                    lblDate.text.toString() != "Введите срок"
        }
        else aName.lblName.text.isNotEmpty() && lblSumma.text.isNotEmpty()
    }

    private fun showADate() {
        aData.animShow(TIME_ANIM_SCREEN)
        aData.enable()

        btnNext.disable()
        btnNext.animHide(TIME_ANIM_SCREEN)
    }

    private fun hideADate() {
        addAction(Actions.sequence(
            Actions.delay(1f),
            Actions.run {
                btnNext.animShow(TIME_ANIM_SCREEN)
                btnNext.enable()

                aData.disable()
                aData.animHide(TIME_ANIM_SCREEN)
            }
        ))
    }

}