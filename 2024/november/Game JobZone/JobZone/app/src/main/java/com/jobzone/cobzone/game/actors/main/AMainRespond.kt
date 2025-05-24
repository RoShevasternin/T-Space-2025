package com.jobzone.cobzone.game.actors.main

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jobzone.cobzone.game.actors.AInputText
import com.jobzone.cobzone.game.actors.AItems
import com.jobzone.cobzone.game.actors.button.AButton
import com.jobzone.cobzone.game.actors.checkbox.ACheckBox
import com.jobzone.cobzone.game.actors.checkbox.ACheckBoxGroup
import com.jobzone.cobzone.game.screens.AppreciationScreen
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.HEIGHT_UI
import com.jobzone.cobzone.game.utils.TIME_ANIM_SCREEN
import com.jobzone.cobzone.game.utils.actor.animHide
import com.jobzone.cobzone.game.utils.actor.animHideSuspend
import com.jobzone.cobzone.game.utils.actor.animShowSuspend
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainRespond(override val screen: AdvancedScreen): AdvancedGroup() {

    private val btnOtklick = AButton(screen, AButton.Type.Otklick_On_Vac)
    private val imgTitle   = Image(screen.game.assetsAll.sapolni_formu)
    private val boxDa      = ACheckBox(screen, ACheckBox.Type.Yes)
    private val boxNo      = ACheckBox(screen, ACheckBox.Type.Not)
    private val items      = AItems(screen)

    private val inputAge     = AInputText(screen, "Ваш возраст", Input.OnscreenKeyboardType.NumberPad)
    private val inputName    = AInputText(screen, "Имя")
    private val inputSurName = AInputText(screen, "Фамилия")
    private val inputNumber  = AInputText(screen, "Номер телефона", Input.OnscreenKeyboardType.NumberPad)

    // Field
    private val listInput  = listOf(inputAge, inputName, inputSurName, inputNumber)
    private val listInputY = listOf(601f, 472f, 343f, 214f)

    override fun addActorsOnGroup() {
        handleKeyboard()

        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgTitle()
                addBoxes()
                addBtnOtklick()
                addItems()
                addInputTexts()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(81f, 1105f, 569f, 197f)
    }

    private fun addBoxes() {
        addActors(boxDa, boxNo)
        boxDa.setBounds(148f, 1039f, 108f, 49f)
        boxNo.setBounds(454f, 1039f, 108f, 49f)

        val cbg = ACheckBoxGroup()
        boxDa.checkBoxGroup = cbg
        boxNo.checkBoxGroup = cbg

        boxDa.check(false)
    }

    private fun addBtnOtklick() {
        addActor(btnOtklick)
        btnOtklick.setBounds(45f, 78f, 619f, 88f)
        btnOtklick.setOnClickListener {
            screen.hideScreen {
                screen.game.navigationManager.navigate(AppreciationScreen::class.java.name)
            }
        }
    }

    private fun addItems() {
        addActor(items)
        items.setBounds(78f, 740f, 554f, 234f)
    }

    private fun addInputTexts() {
        addActors(inputAge, inputName, inputSurName, inputNumber)
        inputAge.setBounds(58f, 601f, 594f, 82f)
        inputName.setBounds(58f, 472f, 594f, 82f)
        inputSurName.setBounds(58f, 343f, 594f, 82f)
        inputNumber.setBounds(58f, 214f, 594f, 82f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withContext(Dispatchers.Default) {
            animShowSuspend(TIME_ANIM_SCREEN)
        }
    }

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Logic --------------------------------------------------------------------

    private fun handleKeyboard() {
        screen.game.activity.apply {
            blockOpenKeyboard.add { heightKeyboard ->
                val input = stage.keyboardFocus.parent

                val screenCoords = Vector2(0f, heightKeyboard.toFloat())
                stage.viewport.unproject(screenCoords)

                val newY = (HEIGHT_UI - screenCoords.y)

                if (input is AInputText) {
                    if (newY > 601f) input.animShowAndHideAllInputText(newY)
                    if (newY > input.y) input.animShowAndHideAllInputText(601f)
                }
            }
            blockCloseKeyboard.add {
                stage.unfocus(stage.keyboardFocus)
                animShowAllInputText()
            }
        }
    }

    private fun AInputText.animShowAndHideAllInputText(newY: Float) {
        listInput.onEachIndexed { index, input ->
            input.clearActions()
            input.animHide(0.15f) {
                if (index == listInput.lastIndex) {
                    this.clearActions()
                    this.addAction(Actions.parallel(
                        Actions.moveTo(58f, newY, 0.25f, Interpolation.exp10),
                        Actions.fadeIn(0.25f)
                    ))
                }
            }
        }

    }

    private fun animShowAllInputText() {
        listInput.onEachIndexed { index, input ->
            input.clearActions()
            input.addAction(Actions.parallel(
                Actions.moveTo(58f, listInputY[index], 0.25f, Interpolation.exp10),
                Actions.fadeIn(0.25f)
            ))
        }
    }

}