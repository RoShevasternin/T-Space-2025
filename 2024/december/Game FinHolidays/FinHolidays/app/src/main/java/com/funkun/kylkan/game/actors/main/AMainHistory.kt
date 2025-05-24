package com.funkun.kylkan.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.funkun.kylkan.game.actors.APanel
import com.funkun.kylkan.game.actors.AScrollPane
import com.funkun.kylkan.game.actors.ATrip
import com.funkun.kylkan.game.actors.autoLayout.AVerticalGroup
import com.funkun.kylkan.game.screens.AddTripsScreen
import com.funkun.kylkan.game.screens.MainScreen
import com.funkun.kylkan.game.utils.Block
import com.funkun.kylkan.game.utils.GameColor
import com.funkun.kylkan.game.utils.TIME_ANIM_SCREEN
import com.funkun.kylkan.game.utils.actor.animHideSuspend
import com.funkun.kylkan.game.utils.actor.animShowSuspend
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.font.FontParameter
import com.funkun.kylkan.game.utils.gdxGame
import com.funkun.kylkan.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainHistory(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font54        = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setSize(54))
    private val font37        = screen.fontGenerator_Roboto_Regular.generateFont(fontParameter.setSize(37))
    private val font48        = screen.fontGenerator_TTFirsNeue_Medium.generateFont(fontParameter.setSize(48))

    private val ls54  = Label.LabelStyle(font54, GameColor.black_36)
    private val ls37  = Label.LabelStyle(font37, GameColor.gray_95)
    private val ls48  = Label.LabelStyle(font48, GameColor.black_36)

    private val imgPoez = Image(gdxGame.assetsAll.your_poezd)
    private val aPanel  = APanel(screen, gdxGame.assetsAll.m_hystory)

    private val verticalGroup = AVerticalGroup(screen, 54f, isWrap = true)
    private val scroll        = AScrollPane(verticalGroup)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgPoez()
                addScroll()
                addAPanel()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPoez() {
        addActor(imgPoez)
        imgPoez.setBounds(80f, 1814f, 891f, 120f)
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
        }
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(60f, 255f, 891f, 1506f)

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