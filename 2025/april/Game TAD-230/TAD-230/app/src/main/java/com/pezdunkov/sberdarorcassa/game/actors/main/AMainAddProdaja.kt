package com.pezdunkov.sberdarorcassa.game.actors.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pezdunkov.sberdarorcassa.game.actors.ABtns
import com.pezdunkov.sberdarorcassa.game.actors.AItem
import com.pezdunkov.sberdarorcassa.game.actors.AMenu
import com.pezdunkov.sberdarorcassa.game.actors.AScrollPane
import com.pezdunkov.sberdarorcassa.game.actors.autoLayout.AVerticalGroup
import com.pezdunkov.sberdarorcassa.game.data.DataItem
import com.pezdunkov.sberdarorcassa.game.screens.AddProdajaScreen
import com.pezdunkov.sberdarorcassa.game.utils.Block
import com.pezdunkov.sberdarorcassa.game.utils.actor.animHide
import com.pezdunkov.sberdarorcassa.game.utils.actor.disable
import com.pezdunkov.sberdarorcassa.game.utils.actor.setOnTouchListener
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedMainGroup
import com.pezdunkov.sberdarorcassa.game.utils.font.FontParameter
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame
import com.pezdunkov.sberdarorcassa.game.utils.isNumTake
import com.pezdunkov.sberdarorcassa.game.utils.isNumeric
import com.pezdunkov.sberdarorcassa.util.log
import java.text.SimpleDateFormat
import java.util.*

class AMainAddProdaja(override val screen: AddProdajaScreen): AdvancedMainGroup() {
    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font37        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(37))
    private val font30        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(30))
    private val font26        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(26))
    private val font22        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(22))

    private val ls37 = Label.LabelStyle(font37, Color.WHITE)
    private val ls30 = Label.LabelStyle(font30, Color.WHITE)
    private val ls26 = Label.LabelStyle(font26, Color.WHITE)
    private val ls22 = Label.LabelStyle(font22, Color.WHITE)

    private val lblInput  = Label("", ls26)
    private val imgInput  = Image(gdxGame.assetsAll.amount_sales)
    private val aBtns     = ABtns(screen)

    private val listDataItem = gdxGame.ds_ItemData.flow.value.filter { it.isProdano.not() }.reversed()

    private val aVerticalGroup = AVerticalGroup(screen, 18f, isWrap = true)
    private val aScroll        = AScrollPane(aVerticalGroup)

    private val listAItem = List(listDataItem.size) { AItem(screen, listDataItem[it], ls37, ls30, ls22) }

    // field
    private var selectedDataItem = DataItem()

    override fun addActorsOnGroup() {
        addInput()
        addABtns()
        addScroll()

        handlerBtns()
    }

    override fun dispose() {
        Gdx.input.setOnscreenKeyboardVisible(false)
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addInput() {
        addActor(imgInput)
        imgInput.setBounds(38f, 192f, 635f, 129f)

        addActor(lblInput)
        lblInput.setAlignment(Align.center or Align.left)

        lblInput.setBounds(57f, 192f, 597f, 84f)

        lblInput.setOnTouchListener {
            Gdx.input.getTextInput(object : Input.TextInputListener {
                override fun input(text: String) {
                    lblInput.setText(text.isNumTake(3).toString())
                }

                override fun canceled() {}
            }, "Количество проданного товара", "", "", Input.OnscreenKeyboardType.NumberPad)
        }
    }

    private fun addABtns() {
        addActor(aBtns)
        aBtns.setBounds(37f, 54f, 636f, 95f)
    }

    private fun addScroll() {
        addActor(aScroll)
        aScroll.setBounds(71f, 333f, 568f, 674f)

        aVerticalGroup.setSize(568f, 674f)

        listAItem.forEachIndexed { index, item ->
            item.setSize(569f, 255f)
            aVerticalGroup.addActor(item)

            item.setOnTouchListener(gdxGame.soundUtil) {
                aScroll.disable()
                aVerticalGroup.children.filter { it != item }.forEach { it.animHide(0.15f) }

                selectedDataItem = item.dataItem
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
        val valueCount = lblInput.text.toString().isNumTake(3)

        if (valueCount > 0 && selectedDataItem.kilkist >= valueCount) {
            gdxGame.ds_ItemData.update {
                val mList = it.toMutableList()
                mList.remove(selectedDataItem)

                selectedDataItem.isProdano      = true
                selectedDataItem.kilkistProdano = valueCount

                mList.add(selectedDataItem)
                mList
            }
            return true
        } else return false
    }

    private fun handlerBtns() {
        aBtns.blockSave = {
            if (checkAndSaveDataItem()) screen.hideScreen {
                gdxGame.navigationManager.back()
            } else {
                gdxGame.soundUtil.apply { play(fail) }
            }
        }
    }

}