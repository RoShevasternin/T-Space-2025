package com.funkun.kylkan.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.funkun.kylkan.game.actors.APanel
import com.funkun.kylkan.game.actors.AScrollPane
import com.funkun.kylkan.game.actors.ATrata
import com.funkun.kylkan.game.actors.ATrip
import com.funkun.kylkan.game.actors.autoLayout.AVerticalGroup
import com.funkun.kylkan.game.screens.AddSpendScreen
import com.funkun.kylkan.game.screens.AddTripsScreen
import com.funkun.kylkan.game.screens.HistoryScreen
import com.funkun.kylkan.game.screens.MainScreen
import com.funkun.kylkan.game.screens.TripsOpenedScreen
import com.funkun.kylkan.game.utils.*
import com.funkun.kylkan.game.utils.actor.animHideSuspend
import com.funkun.kylkan.game.utils.actor.animShowSuspend
import com.funkun.kylkan.game.utils.actor.disable
import com.funkun.kylkan.game.utils.actor.setOnClickListener
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.font.FontParameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainTripsOpened(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font54        = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setSize(54))
    private val font37        = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setSize(37))
    private val font48        = screen.fontGenerator_TTFirsNeue_Medium.generateFont(fontParameter.setSize(48))
    private val fontReg       = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setSize(44))
    private val fontMed       = screen.fontGenerator_Roboto_Medium.generateFont(fontParameter.setSize(44))

    private val ls54  = Label.LabelStyle(font54, GameColor.black_36)
    private val ls37  = Label.LabelStyle(font37, GameColor.gray_95)
    private val ls48  = Label.LabelStyle(font48, GameColor.black_36)
    private val lsReg = Label.LabelStyle(fontReg, GameColor.black_36)
    private val lsMed = Label.LabelStyle(fontMed, GameColor.green)

    private val currentTripData = gdxGame.ds_TripData.flow.value[TripsOpenedScreen.CURRENT_SELECTED_TRIP_INDEX]

    private val imgMain = Image(gdxGame.assetsAll.poezdka)
    private val aPanel  = APanel(screen, gdxGame.assetsAll.m_def)
    private val trip    = ATrip(screen, currentTripData, ls54, ls37, ls48)
    private val aBack   = Actor()
    private val aEdit   = Actor()
    private val aRemove = Actor()

    private val verticalGroup = AVerticalGroup(screen, 54f, isWrap = true)
    private val scroll        = AScrollPane(verticalGroup)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgMain()
                addScroll()
                addAPanel()
                addTrip()
                addABack()
                addAdii()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMain() {
        addActor(imgMain)
        imgMain.setBounds(0f, 1021f, 1012f, 850f)
    }

    private fun addTrip() {
        trip.isEditable = false
        addActor(trip)
        trip.setBounds(60f, 1410f, 891f, 326f)
        trip.disable()
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
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(AddSpendScreen::class.java.name, screen::class.java.name)
                }
            }
            blockHistory = {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name)
            }
        }
    }

    private fun addABack() {
        addActor(aBack)
        aBack.setBounds(28f, 1765f, 133f, 126f)
        aBack.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(MainScreen::class.java.name)
            }
        }
    }

    private fun addAdii() {
        addActors(aEdit, aRemove)
        aEdit.setBounds(60f, 1222f, 450f, 107f)
        aRemove.setBounds(650f, 1222f, 301f, 107f)

        aEdit.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.ds_TripData.update {
                val mutableList = it.toMutableList()
                mutableList.removeAt(TripsOpenedScreen.CURRENT_SELECTED_TRIP_INDEX)
                mutableList
            }

            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(AddTripsScreen::class.java.name)
            }
        }
        aRemove.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.ds_TripData.update {
                val mutableList = it.toMutableList()
                mutableList.removeAt(TripsOpenedScreen.CURRENT_SELECTED_TRIP_INDEX)
                mutableList
            }

            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(MainScreen::class.java.name)
            }
        }
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(128f, 254f, 755f, 714f)

        verticalGroup.apply {
            gdxGame.ds_TripData.flow.value[TripsOpenedScreen.CURRENT_SELECTED_TRIP_INDEX].listTrat.onEach { trataData ->
                val trata = ATrata(screen, trataData, lsReg, lsMed)
                trata.setSize(755f, 99f)
                addActor(trata)
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

}