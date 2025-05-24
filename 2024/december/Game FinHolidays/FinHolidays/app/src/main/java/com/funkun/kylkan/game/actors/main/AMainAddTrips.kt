package com.funkun.kylkan.game.actors.main

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.funkun.kylkan.game.actors.AInputText
import com.funkun.kylkan.game.actors.APanel
import com.funkun.kylkan.game.actors.button.AButton
import com.funkun.kylkan.game.dataStore.TripData
import com.funkun.kylkan.game.screens.HistoryScreen
import com.funkun.kylkan.game.screens.MainScreen
import com.funkun.kylkan.game.utils.Block
import com.funkun.kylkan.game.utils.CalendarUtil
import com.funkun.kylkan.game.utils.GameColor
import com.funkun.kylkan.game.utils.TIME_ANIM_SCREEN
import com.funkun.kylkan.game.utils.actor.animHideSuspend
import com.funkun.kylkan.game.utils.actor.animShowSuspend
import com.funkun.kylkan.game.utils.actor.setOnClickListener
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.font.FontParameter
import com.funkun.kylkan.game.utils.gdxGame
import com.funkun.kylkan.game.utils.runGDX
import com.funkun.kylkan.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainAddTrips(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val font54        = screen.fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(54))

    private val ls54 = Label.LabelStyle(font54, GameColor.black_36)

    private val imgKDS = Image(gdxGame.assetsAll.kuda_data_cum)
    private val aPanel = APanel(screen, gdxGame.assetsAll.m_def)
    private val btnAdd = AButton(screen, AButton.Type.Add)
    private val inputK = AInputText(screen, Input.OnscreenKeyboardType.Default)
    private val inputS = AInputText(screen, Input.OnscreenKeyboardType.NumberPad)
    private val lblD1  = Label("", ls54)
    private val lblD2  = Label("", ls54)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgKDS()
                addAPanel()
                addBtnAdd()
                addInputK()
                addInputS()
                addLblD()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgKDS() {
        addActor(imgKDS)
        imgKDS.setBounds(101f, 1068f, 809f, 803f)
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
                screen.hideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(HistoryScreen::class.java.name)
                }
            }
        }
    }

    private fun addBtnAdd() {
        addActor(btnAdd)
        btnAdd.setBounds(101f, 482f, 809f, 108f)

        btnAdd.setOnClickListener {
            if (
                inputK.textField.text.isNotEmpty() &&
                lblD1.text.toString().isNotEmpty() &&
                lblD2.text.toString().isNotEmpty() &&
                inputS.textField.text.isNotEmpty()
            ) {
                gdxGame.ds_TripData.update {
                    val mutableList = it.toMutableList()

                    val tripData = TripData(
                        inputK.textField.text,
                        lblD1.text.toString(), lblD2.text.toString(),
                        inputS.textField.text.take(6).toInt(),
                    )

                    mutableList.add(tripData)
                    mutableList
                }

                screen.hideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MainScreen::class.java.name)
                }
            } else {
                // Sound Fail
            }
        }
    }

    private fun addInputK() {
        addActor(inputK)
        inputK.setBounds(153f, 1659f, 700f, 133f)
    }

    private fun addInputS() {
        addActor(inputS)
        inputS.setBounds(153f, 1071f, 700f, 133f)
    }

    private fun addLblD() {
        addActors(lblD1, lblD2)
        lblD1.setBounds(134f, 1369f, 272f, 123f)
        lblD2.setBounds(604f, 1369f, 272f, 123f)

        lblD1.setOnClickListener {
            gdxGame.activity.showDatePickerDialog(CalendarUtil.currentDate,
                blockDate = { selectDate ->
                    runGDX { lblD1.setText(selectDate) }
                },
                blockCancel = {}
            )
        }
        lblD2.setOnClickListener {
            gdxGame.activity.showDatePickerDialog(CalendarUtil.currentDate,
                blockDate = { selectDate ->
                    runGDX { lblD2.setText(selectDate) }
                },
                blockCancel = {}
            )
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

}