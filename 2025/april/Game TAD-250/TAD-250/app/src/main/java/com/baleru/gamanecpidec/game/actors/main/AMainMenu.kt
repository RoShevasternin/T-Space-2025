package com.baleru.gamanecpidec.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.baleru.gamanecpidec.game.actors.AMenu
import com.baleru.gamanecpidec.game.actors.ATop
import com.baleru.gamanecpidec.game.screens.MenuScreen
import com.baleru.gamanecpidec.game.screens.AddScreen
import com.baleru.gamanecpidec.game.screens.HistoryScreen
import com.baleru.gamanecpidec.game.utils.*
import com.baleru.gamanecpidec.game.utils.actor.animDelay
import com.baleru.gamanecpidec.game.utils.actor.animHide
import com.baleru.gamanecpidec.game.utils.actor.animShow
import com.baleru.gamanecpidec.game.utils.actor.setOnClickListener
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedMainGroup
import com.baleru.gamanecpidec.game.utils.font.FontParameter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AMainMenu(
    override val screen: MenuScreen,
    val aMenu: AMenu,
    val aTop: ATop,
): AdvancedMainGroup() {

    private val valueIncome  = gdxGame.ds_ItemData.flow.value.filter { it.isIncome }.sumOf { it.summa }
    private val valueExpense = gdxGame.ds_ItemData.flow.value.filter { it.isIncome.not() }.sumOf { it.summa }
    private val valueBalance = valueIncome - valueExpense

    private val imgHistory = Image(gdxGame.assetsAll.histr)

    override fun addActorsOnGroup() {
        aTop.updateBalance(valueIncome, valueBalance, valueExpense)

        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addImgHistory()

        animShowMain()

        handlerAMenu()

    }

    // Actors ------------------------------------------------------------------------

    private fun addImgHistory() {
        addActor(imgHistory)
        imgHistory.setBounds(47f, 1227f, 793f, 68f)

        imgHistory.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
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

    // Logic ------------------------------------------------------------------------

    private fun handlerAMenu() {
        aMenu.aPlus.check()

        aMenu.blockPolza = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AddScreen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockPlus = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AddScreen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockHistory = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
    }

}