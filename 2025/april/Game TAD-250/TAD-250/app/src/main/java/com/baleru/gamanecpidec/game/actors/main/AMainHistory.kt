package com.baleru.gamanecpidec.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.baleru.gamanecpidec.game.actors.AItem
import com.baleru.gamanecpidec.game.actors.AMenu
import com.baleru.gamanecpidec.game.actors.AScrollPane
import com.baleru.gamanecpidec.game.actors.autoLayout.AVerticalGroup
import com.baleru.gamanecpidec.game.data.DataItem
import com.baleru.gamanecpidec.game.screens.MenuScreen
import com.baleru.gamanecpidec.game.screens.AddScreen
import com.baleru.gamanecpidec.game.screens.EditScreen
import com.baleru.gamanecpidec.game.screens.HistoryScreen
import com.baleru.gamanecpidec.game.utils.*
import com.baleru.gamanecpidec.game.utils.actor.animDelay
import com.baleru.gamanecpidec.game.utils.actor.animHide
import com.baleru.gamanecpidec.game.utils.actor.animShow
import com.baleru.gamanecpidec.game.utils.actor.setOnClickListener
import com.baleru.gamanecpidec.game.utils.actor.setOnTouchListener
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedMainGroup
import com.baleru.gamanecpidec.game.utils.font.FontParameter

class AMainHistory(
    override val screen: HistoryScreen,
    val aMenu: AMenu,
): AdvancedMainGroup() {

    private val listDataItem: List<DataItem> = gdxGame.ds_ItemData.flow.value.reversed()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font31        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(31))
    private val font53        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(53))

    private val ls31 = Label.LabelStyle(font31, GameColor.blue_06)
    private val ls53 = Label.LabelStyle(font53, GameColor.blue_06)

    private val listAItem = List(listDataItem.size) { AItem(screen, listDataItem[it], ls31) }
    private val lblTitle  = Label("Tarih", ls53)

    private val imgPanel = Image(gdxGame.assetsAll.DOHED)
    private val vertical = AVerticalGroup(screen, 33f, isWrap = true)
    private val scroll   = AScrollPane(vertical)

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addAll()
        addLblTitle()

        animShowMain()

        handlerAMenu()

        val aToper = Actor()
        addActor(aToper)
        aToper.setBounds(606f, 1614f, 194f, 96f)
        aToper.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }

    }

    // Actors ------------------------------------------------------------------------

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(67f, 1614f, 699f, 63f)
    }

    private fun addAll() {
        addActor(imgPanel)
        imgPanel.setBounds(33f, 171f, 767f, 1540f)

        addActor(scroll)
        scroll.setBounds(66f, 204f, 700f, 1388f)

        vertical.setSize(700f, 1388f)

        listAItem.forEachIndexed { index, item ->
            item.setSize(700f, 144f)
            vertical.addActor(item)

            item.setOnTouchListener(gdxGame.soundUtil) {
                EditScreen.EDIT_ITEM = item.dataItem
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(EditScreen::class.java.name, screen::class.java.name)
                }
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
        aMenu.blockPolza = {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
        aMenu.blockPlus = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AddScreen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockHistory = {}
    }

}