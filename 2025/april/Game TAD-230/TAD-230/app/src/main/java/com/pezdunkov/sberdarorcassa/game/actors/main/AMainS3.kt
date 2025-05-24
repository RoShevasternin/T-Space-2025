package com.pezdunkov.sberdarorcassa.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.pezdunkov.sberdarorcassa.game.actors.AItem
import com.pezdunkov.sberdarorcassa.game.actors.AMenu
import com.pezdunkov.sberdarorcassa.game.actors.AScrollPane
import com.pezdunkov.sberdarorcassa.game.actors.ATop
import com.pezdunkov.sberdarorcassa.game.actors.autoLayout.AVerticalGroup
import com.pezdunkov.sberdarorcassa.game.screens.AddTovarScreen
import com.pezdunkov.sberdarorcassa.game.screens.S1Screen
import com.pezdunkov.sberdarorcassa.game.screens.S2Screen
import com.pezdunkov.sberdarorcassa.game.screens.S3Screen
import com.pezdunkov.sberdarorcassa.game.screens.S4Screen
import com.pezdunkov.sberdarorcassa.game.utils.Block
import com.pezdunkov.sberdarorcassa.game.utils.TIME_ANIM_SCREEN
import com.pezdunkov.sberdarorcassa.game.utils.actor.animDelay
import com.pezdunkov.sberdarorcassa.game.utils.actor.animHide
import com.pezdunkov.sberdarorcassa.game.utils.actor.animShow
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedMainGroup
import com.pezdunkov.sberdarorcassa.game.utils.font.FontParameter
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame
import java.text.SimpleDateFormat
import java.util.*

class AMainS3(override val screen: S3Screen): AdvancedMainGroup() {

//    private val valueExpense = gdxGame.ds_ItemData.flow.value.filter { it.isIncome.not() }.sumOf { it.summa }
//    private val valueBalance = valueIncome - valueExpense

    private val listDataItem = gdxGame.ds_ItemData.flow.value.filter { it.isProdano.not() }.reversed()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font37        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(37))
    private val font30        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(30))
    private val font22        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(22))

    private val ls37 = Label.LabelStyle(font37, Color.WHITE)
    private val ls30 = Label.LabelStyle(font30, Color.WHITE)
    private val ls22 = Label.LabelStyle(font22, Color.WHITE)

    private val aMenu = AMenu(screen)

    private val aVerticalGroup = AVerticalGroup(screen, 18f, isWrap = true)
    private val aScroll        = AScrollPane(aVerticalGroup)

    private val listAItem = List(listDataItem.size) { AItem(screen, listDataItem[it], ls37, ls30, ls22) }

    override fun addActorsOnGroup() {
        addAMenu()
        addScroll()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAMenu() {
        addActor(aMenu)
        aMenu.setBounds(37f, 892f, 636f, 189f)

        aMenu.boxS3.check()

        aMenu.blockS1 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(S1Screen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockS2 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(S2Screen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockS3 = {

        }
        aMenu.blockS4 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(S4Screen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addScroll() {
        addActor(aScroll)
        aScroll.setBounds(71f, 0f, 568f, 856f)

        aVerticalGroup.setSize(568f, 856f)

        listAItem.forEachIndexed { index, item ->
            item.setSize(569f, 255f)
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

}