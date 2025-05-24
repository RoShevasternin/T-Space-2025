package com.funkun.kylkan.game.actors.main

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.funkun.kylkan.game.actors.AInputText
import com.funkun.kylkan.game.actors.APanel
import com.funkun.kylkan.game.actors.button.AButton
import com.funkun.kylkan.game.dataStore.Trata
import com.funkun.kylkan.game.dataStore.TripData
import com.funkun.kylkan.game.screens.HistoryScreen
import com.funkun.kylkan.game.screens.MainScreen
import com.funkun.kylkan.game.screens.TripsOpenedScreen
import com.funkun.kylkan.game.utils.Block
import com.funkun.kylkan.game.utils.TIME_ANIM_SCREEN
import com.funkun.kylkan.game.utils.actor.animHideSuspend
import com.funkun.kylkan.game.utils.actor.animShowSuspend
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.gdxGame
import com.funkun.kylkan.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.text.take

class AMainAddSpend(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val imgNC  = Image(gdxGame.assetsAll.trata_cena)
    private val aPanel = APanel(screen, gdxGame.assetsAll.m_def)
    private val btnAdd = AButton(screen, AButton.Type.AddSum)

    private val inputN = AInputText(screen, Input.OnscreenKeyboardType.Default)
    private val inputC = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgNC()
                addAPanel()
                addBtnAdd()
                addInputK()
                addInputS()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgNC() {
        addActor(imgNC)
        imgNC.setBounds(101f, 1362f, 809f, 509f)
    }

    private fun addAPanel() {
        addActor(aPanel)
        aPanel.setBounds(22f, 11f, 967f, 257f)

        aPanel.apply {
            blockMenu = {
                screen.hideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MainScreen::class.java.name)
                }
            }
            blockPlus = {

            }
            blockHistory = {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name)
            }
        }
    }

    private fun addBtnAdd() {
        addActor(btnAdd)
        btnAdd.setBounds(101f, 482f, 809f, 108f)

        btnAdd.setOnClickListener {
            if (
                inputN.textField.text.isNotEmpty() &&
                inputC.textField.text.isNotEmpty()
            ) {
                gdxGame.ds_TripData.update {
                    val mutableList = it.toMutableList()

                    mutableList[TripsOpenedScreen.CURRENT_SELECTED_TRIP_INDEX].listTrat.add(
                        Trata(
                            inputN.textField.text,
                            inputC.textField.text.take(6).toInt(),
                        )
                    )

                    mutableList
                }

                screen.hideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(TripsOpenedScreen::class.java.name)
                }
            } else {
                // Sound Fail
            }
        }
    }

    private fun addInputK() {
        addActor(inputN)
        inputN.setBounds(147f, 1670f, 719f, 110f)
    }

    private fun addInputS() {
        addActor(inputC)
        inputC.setBounds(147f, 1375f, 719f, 110f)
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

}