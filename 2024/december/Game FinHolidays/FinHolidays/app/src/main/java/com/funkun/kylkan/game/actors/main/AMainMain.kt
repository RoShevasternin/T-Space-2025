package com.funkun.kylkan.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.funkun.kylkan.game.actors.APanel
import com.funkun.kylkan.game.actors.AScrollPane
import com.funkun.kylkan.game.actors.ATrip
import com.funkun.kylkan.game.actors.autoLayout.AVerticalGroup
import com.funkun.kylkan.game.screens.AddTripsScreen
import com.funkun.kylkan.game.screens.HistoryScreen
import com.funkun.kylkan.game.utils.Block
import com.funkun.kylkan.game.utils.GameColor
import com.funkun.kylkan.game.utils.TIME_ANIM_SCREEN
import com.funkun.kylkan.game.utils.actor.animHideSuspend
import com.funkun.kylkan.game.utils.actor.animShowSuspend
import com.funkun.kylkan.game.utils.actor.setBounds
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.font.FontParameter
import com.funkun.kylkan.game.utils.gdxGame
import com.funkun.kylkan.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainMain(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font129       = screen.fontGenerator_Roboto_Medium.generateFont(fontParameter.setSize(129))
    private val font54        = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setSize(54))
    private val font37        = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setSize(37))
    private val font48        = screen.fontGenerator_TTFirsNeue_Medium.generateFont(fontParameter.setSize(48))

    private val ls129 = Label.LabelStyle(font129, Color.WHITE)
    private val ls54  = Label.LabelStyle(font54, GameColor.black_36)
    private val ls37  = Label.LabelStyle(font37, GameColor.gray_95)
    private val ls48  = Label.LabelStyle(font48, GameColor.black_36)

    private val imgGreen = Image(gdxGame.assetsAll.green)
    private val aPanel   = APanel(screen, gdxGame.assetsAll.m_home)
    private val lbl_1    = Label("0", ls129)
    private val lbl_2    = Label("0", ls129)
    private val lbl_3    = Label("0", ls129)

    private val verticalGroup = AVerticalGroup(screen, 53f, isWrap = true)
    private val scroll        = AScrollPane(verticalGroup)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgGreen()
                addLbl()
                addScroll()
                addAPanel()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgGreen() {
        addActor(imgGreen)
        imgGreen.setBounds(60f, 839f, 891f, 1032f)
    }

    private fun addAPanel() {
        addActor(aPanel)
        aPanel.setBounds(22f, 11f, 967f, 257f)

        aPanel.apply {
            blockPlus = {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(AddTripsScreen::class.java.name, screen::class.java.name)
                }
            }
            blockHistory = {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name)
            }
        }
    }

    private fun addLbl() {
        addActors(lbl_1, lbl_2, lbl_3)
        lbl_1.apply {
            setAlignment(Align.center)
            setBounds(205f, 1565f, 129f, 92f)
        }
        lbl_2.apply {
            setAlignment(Align.center)
            setBounds(704f, 1565f, 75f, 92f)
        }
        lbl_3.apply {
            setAlignment(Align.center)
            setBounds(467f, 1207f, 76f, 92f)
        }

        lbl_1.setText(gdxGame.ds_TripData.flow.value.count())
        lbl_2.setText((0..gdxGame.ds_TripData.flow.value.count()).random())
        lbl_3.setText((0..gdxGame.ds_TripData.flow.value.count()).random())
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(60f, 260f, 891f, 526f)

        verticalGroup.apply {
            gdxGame.ds_TripData.flow.value.onEach { tripData ->
                val trip = ATrip(screen, tripData, ls54, ls37, ls48)
                trip.setSize(891f, 335f)
                addActor(trip)
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