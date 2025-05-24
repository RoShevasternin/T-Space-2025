package com.borocbernay.kasshsemir.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.borocbernay.kasshsemir.game.actors.AItem
import com.borocbernay.kasshsemir.game.actors.AMenu
import com.borocbernay.kasshsemir.game.actors.AScrollPane
import com.borocbernay.kasshsemir.game.actors.autoLayout.AVerticalGroup
import com.borocbernay.kasshsemir.game.actors.button.AButton
import com.borocbernay.kasshsemir.game.screens.AddTovarScreen
import com.borocbernay.kasshsemir.game.screens.AnalScreen
import com.borocbernay.kasshsemir.game.screens.HistoryScreen
import com.borocbernay.kasshsemir.game.screens.TovarsScreen
import com.borocbernay.kasshsemir.game.utils.Block
import com.borocbernay.kasshsemir.game.utils.GameColor
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedMainGroup
import com.borocbernay.kasshsemir.game.utils.font.FontParameter
import com.borocbernay.kasshsemir.game.utils.gdxGame

class AMainHistory(
    override val screen: HistoryScreen,
    val aMenu: AMenu,
): AdvancedMainGroup() {

    private val listDataItem = gdxGame.ds_ItemData.flow.value.filter { it.isProdano }.reversed()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font46        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(46))
    private val font36        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(36))
    private val font27        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(27))

    private val ls46 = Label.LabelStyle(font46, GameColor.black_26)
    private val ls36 = Label.LabelStyle(font36, GameColor.green_41)
    private val ls27 = Label.LabelStyle(font27, GameColor.black_26_60)

    private val lblTitle = Label("История продаж", ls46)
    private val btnPlus  = AButton(screen, AButton.Type.Plus)

    private val aVerticalGroup = AVerticalGroup(screen, 45f, isWrap = true, paddingBottom = 100f)
    private val aScroll        = AScrollPane(aVerticalGroup)

    private val listAItem = List(listDataItem.size) { AItem(screen, listDataItem[it], ls46, ls36, ls27) }

    override fun addActorsOnGroup() {
        addLblTitleAndPlus()
        addScroll()

        handlerAMenu()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblTitleAndPlus() {
        addActor(lblTitle)
        lblTitle.setBounds(34f, 1415f, 310f, 56f)

        addActor(btnPlus)
        btnPlus.setBounds(738f, 1397f, 94f, 94f)

        btnPlus.setOnClickListener {
            gdxGame.navigationManager.navigate(AddTovarScreen::class.java.name, screen::class.java.name)
        }
    }

    private fun addScroll() {
        addActor(aScroll)
        aScroll.setBounds(34f, 152f, 797f, 1200f)

        aVerticalGroup.setSize(797f, 1200f)

        listAItem.forEachIndexed { index, item ->
            item.setSize(797f, 370f)
            aVerticalGroup.addActor(item)
        }

    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        blockEnd.invoke()
    }

    override fun animHideMain(blockEnd: Block) {
        blockEnd.invoke()
    }

    // Logic ------------------------------------------------------

    private fun handlerAMenu() {
        aMenu.boxS2.check()

        aMenu.blockS1 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(TovarsScreen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockS2 = {}
        aMenu.blockS3 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AnalScreen::class.java.name, screen::class.java.name)
            }
        }
    }

}