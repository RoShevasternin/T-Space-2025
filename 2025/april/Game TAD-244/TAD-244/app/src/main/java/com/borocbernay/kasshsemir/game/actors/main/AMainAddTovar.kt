package com.borocbernay.kasshsemir.game.actors.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.borocbernay.kasshsemir.game.actors.AInputs
import com.borocbernay.kasshsemir.game.actors.AMenu
import com.borocbernay.kasshsemir.game.actors.AScrollPane
import com.borocbernay.kasshsemir.game.actors.autoLayout.AVerticalGroup
import com.borocbernay.kasshsemir.game.actors.checkbox.ACheckBox
import com.borocbernay.kasshsemir.game.data.DataItem
import com.borocbernay.kasshsemir.game.screens.AddSalesScreen
import com.borocbernay.kasshsemir.game.screens.AddTovarScreen
import com.borocbernay.kasshsemir.game.screens.AnalScreen
import com.borocbernay.kasshsemir.game.screens.HistoryScreen
import com.borocbernay.kasshsemir.game.screens.TovarsScreen
import com.borocbernay.kasshsemir.game.utils.Block
import com.borocbernay.kasshsemir.game.utils.GameColor
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedMainGroup
import com.borocbernay.kasshsemir.game.utils.font.FontParameter
import com.borocbernay.kasshsemir.game.utils.gdxGame
import com.borocbernay.kasshsemir.game.utils.isNumeric
import java.text.SimpleDateFormat
import java.util.*

class AMainAddTovar(
    override val screen: AddTovarScreen,
    val aMenu: AMenu,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font46        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(46))

    private val ls46 = Label.LabelStyle(font46, GameColor.black_26)

    private val lblTitle = Label("Добавить", ls46)
    private val box      = ACheckBox(screen, ACheckBox.Type.Box)

    private val aVerticalGroup = AVerticalGroup(screen, isWrap = true, paddingBottom = 50f)
    private val aScroll        = AScrollPane(aVerticalGroup)

    private val aInputs = AInputs(screen)

    // field
    private var inputDataItem = DataItem()

    override fun addActorsOnGroup() {
        addLblTitleAndBox()
        addScroll()

        handlerAMenu()
    }

    override fun dispose() {
        Gdx.input.setOnscreenKeyboardVisible(false)
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblTitleAndBox() {
        addActor(lblTitle)
        lblTitle.setBounds(34f, 1676f, 227f, 56f)

        addActor(box)
        box.setBounds(237f, 1540f, 391f, 94f)

        box.setOnCheckListener { isSales ->
            if (isSales) gdxGame.navigationManager.navigate(AddSalesScreen::class.java.name)
        }
    }

    private fun addScroll() {
        addActor(aScroll)
        aScroll.setBounds(46f, 155f, 773f, 1340f)

        aVerticalGroup.setSize(773f, 1340f)

        aInputs.setSize(772f, 1377f)
        aVerticalGroup.addActor(aInputs)

        aInputs.blockAdd = {
            if (checkAndSaveDataItem()) {
                gdxGame.navigationManager.back()
            } else {
                gdxGame.soundUtil.apply { play(fail) }
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        blockEnd.invoke()
    }

    override fun animHideMain(blockEnd: Block) {
        blockEnd.invoke()
    }

    // Logic -----------------------------------------------

    private fun checkAndSaveDataItem(): Boolean {
        inputDataItem.isProdano     = false
        inputDataItem.nName         = aInputs.listInputs[0].text.toString()
        inputDataItem.date          = getFormattedDate()
        inputDataItem.kilkist       = aInputs.listInputs[1].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }
        inputDataItem.priceZakupu1  = aInputs.listInputs[2].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }
        inputDataItem.priceProdaja1 = aInputs.listInputs[3].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }
        inputDataItem.nalog         = aInputs.listInputs[4].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }
        inputDataItem.marja         = aInputs.listInputs[5].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }

        if (
            inputDataItem.nName.isNotEmpty() &&
            inputDataItem.kilkist        > 0 &&
            inputDataItem.priceZakupu1   > 0 &&
            inputDataItem.priceProdaja1  > 0 &&
            inputDataItem.nalog          > 0 &&
            inputDataItem.marja          > 0
        ) {
            gdxGame.ds_ItemData.update {
                val mList = it.toMutableList()
                mList.add(inputDataItem)
                mList
            }
            return true
        } else return false
    }

    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    // Logic ------------------------------------------------------

    private fun handlerAMenu() {
        //aMenu.boxS1.check()

        aMenu.blockS1 = {
            gdxGame.navigationManager.clearBackStack()
            gdxGame.navigationManager.navigate(TovarsScreen::class.java.name)
        }
        aMenu.blockS2 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
        aMenu.blockS3 = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AnalScreen::class.java.name, screen::class.java.name)
            }
        }
    }

}