package com.borocbernay.kasshsemir.game.actors.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.borocbernay.kasshsemir.game.actors.AMenu
import com.borocbernay.kasshsemir.game.actors.checkbox.ACheckBox
import com.borocbernay.kasshsemir.game.screens.AddSalesScreen
import com.borocbernay.kasshsemir.game.screens.AddTovarScreen
import com.borocbernay.kasshsemir.game.screens.AnalScreen
import com.borocbernay.kasshsemir.game.screens.HistoryScreen
import com.borocbernay.kasshsemir.game.screens.TovarsScreen
import com.borocbernay.kasshsemir.game.utils.Block
import com.borocbernay.kasshsemir.game.utils.GameColor
import com.borocbernay.kasshsemir.game.utils.actor.setOnClickListener
import com.borocbernay.kasshsemir.game.utils.actor.setOnTouchListener
import com.borocbernay.kasshsemir.game.utils.advanced.AdvancedMainGroup
import com.borocbernay.kasshsemir.game.utils.font.FontParameter
import com.borocbernay.kasshsemir.game.utils.gdxGame
import com.borocbernay.kasshsemir.game.utils.isNumTake

class AMainAddSales(
    override val screen: AddSalesScreen,
    val aMenu: AMenu,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font46        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(46))
    private val font32        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(32))

    private val ls46 = Label.LabelStyle(font46, GameColor.black_26)
    private val ls32 = Label.LabelStyle(font32, GameColor.green_22_40)

    private val lblTitle  = Label("Добавить", ls46)
    private val box       = ACheckBox(screen, ACheckBox.Type.Box)
    private val imgInput  = Image(gdxGame.assetsAll.PRODAJKA)
    private val lblName   = Label("", ls32)
    private val lblCount  = Label("", ls32)

    private val listDataItem = gdxGame.ds_ItemData.flow.value.filter { it.isProdano.not() }

    override fun addActorsOnGroup() {
        addLblTitleAndBox()

        addInput()
        addBtnAdd()

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
        box.check()

        box.setOnCheckListener { isSales ->
            if (isSales.not()) gdxGame.navigationManager.navigate(AddTovarScreen::class.java.name)
        }
    }

    private fun addInput() {
        addActor(imgInput)
        imgInput.setBounds(46f, 966f, 773f, 529f)

        addActor(lblName)
        lblName.setBounds(73f, 1337f, 772f, 101f)
        lblName.setOnTouchListener {
            Gdx.input.getTextInput(object : Input.TextInputListener {
                override fun input(text: String) {
                    lblName.setText(text.take(15).toString())
                }

                override fun canceled() {}
            }, "Название товара", "", "", Input.OnscreenKeyboardType.Default)
        }

        addActor(lblCount)
        lblCount.setBounds(73f, 1125f, 772f, 101f)
        lblCount.setOnTouchListener {
            Gdx.input.getTextInput(object : Input.TextInputListener {
                override fun input(text: String) {
                    lblCount.setText(text.isNumTake(3).toString())
                }

                override fun canceled() {}
            }, "Количество (шт)", "", "", Input.OnscreenKeyboardType.NumberPad)
        }
    }

    private fun addBtnAdd() {
        val aBtnAdd = Actor()
        addActor(aBtnAdd)
        aBtnAdd.setBounds(46f, 966f, 772f, 103f)

        aBtnAdd.setOnClickListener(gdxGame.soundUtil) {
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
        val valueName  = lblName.text.toString()
        val valueCount = lblCount.text.toString().isNumTake(3)

        val dataItem = listDataItem.find { it.nName.contains(valueName) }

        if (dataItem!= null && valueCount > 0) {
            gdxGame.ds_ItemData.update {
                val mList = it.toMutableList()
                mList.find { it.nName.contains(dataItem.nName) }?.let {
                    it.kilkistProdano = valueCount
                    it.isProdano      = true
                }
                mList
            }
            return true
        } else return false
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