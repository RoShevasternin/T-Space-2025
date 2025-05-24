package com.kaskazer.kazmuchero.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.kaskazer.kazmuchero.game.LibGDXGame
import com.kaskazer.kazmuchero.game.actors.AButton
import com.kaskazer.kazmuchero.game.actors.APanelBalance
import com.kaskazer.kazmuchero.game.utils.GColor
import com.kaskazer.kazmuchero.game.utils.TIME_ANIM
import com.kaskazer.kazmuchero.game.utils.actor.animHide
import com.kaskazer.kazmuchero.game.utils.actor.animShow
import com.kaskazer.kazmuchero.game.utils.actor.setOnClickListener
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedStage
import com.kaskazer.kazmuchero.game.utils.font.FontParameter
import com.kaskazer.kazmuchero.game.utils.region

class MarketScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font44        = fontGenerator_AlbertSans.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "/").setSize(44))

    private val ls44_red  = LabelStyle(font44, GColor.red)
    private val ls44_dark = LabelStyle(font44, GColor.dark)

    private val LVL get() = game.dataStore.lvl

    private val panelBalance = APanelBalance(this)
    private val imgMarket    = Image(game.all.market)
    private val btnGazSatmak = AButton(this, AButton.Type.GazSatmak)
    private val btnGeriGel   = AButton(this, AButton.Type.GeriGel)
    private val listImgClose = List(6) { Image(game.all.closed) }
    private val listLblCount = List(6) { Label("", ls44_dark) }

    // Field

    private val listCount = listOf(2, 2, 2, 2, 1, 1)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.background_game.region)
        super.show()
        stageBack.addAndFillActor(Image(drawerUtil.getRegion(GColor.dark_1A_70)).apply {
            color.a = 0f
            animShow(TIME_ANIM)
        })
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelBalance()
        addImgMarket()
        addBtnGazSatmak()
        addBtnGeriGel()

        addList_LblCount()
        addList_ImgItem()
        addList_ImgClosed()
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addPanelBalance() {
        addActor(panelBalance)
        panelBalance.setBounds(82f, 2035f, 879f, 126f)

        panelBalance.apply {
            updateGaz { game.dataStore.gaz }
            updateCoin { game.dataStore.coin }
        }
    }

    private fun AdvancedStage.addImgMarket() {
        addActor(imgMarket)
        imgMarket.setBounds(83f, 339f, 877f, 1580f)
    }

    private fun AdvancedStage.addBtnGazSatmak() {
        addActor(btnGazSatmak)
        btnGazSatmak.setBounds(0f, 54f, 490f, 168f)

        btnGazSatmak.setOnClickListener {
            if (panelBalance.getValueGaz > 0) {
                game.soundUtil.apply { play(gaz_satmak, 0.15f) }
                panelBalance.updateGaz(true) { gaz ->
                    panelBalance.updateCoin(true) { coin -> coin + (gaz * 1.5f).toInt() }
                    0
                }
            }
        }
    }

    private fun AdvancedStage.addBtnGeriGel() {
        addActor(btnGeriGel)
        btnGeriGel.setBounds(553f, 54f, 490f, 168f)

        val listLVLScreenName = listOf(
            LVL_1_Screen::class.java.name,
            LVL_2_Screen::class.java.name,
            LVL_3_Screen::class.java.name,
            LVL_4_Screen::class.java.name,
            LVL_5_Screen::class.java.name,
            LVL_6_Screen::class.java.name,
            LVL_7_Screen::class.java.name,
            LVL_8_Screen::class.java.name,
            LVL_9_Screen::class.java.name,
            LVL_10_Screen::class.java.name,
        )

        btnGeriGel.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(listLVLScreenName[LVL-1], MarketScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addList_ImgItem() {
        var nx = 79f
        var ny = 1405f

        val listImg  = List(6) { Actor() }
        val listCost = listOf(500, 1000, 5000, 10_000, 15_000, 20_000)

        listImg.onEachIndexed { index, item ->
            addActor(item)
            item.setBounds(nx, ny, 426f, 520f)
            nx += 33 + 426
            if (index.inc() % 2 == 0) {
                nx = 79f
                ny -= 13 + 520
            }

            item.setOnClickListener {
                if(panelBalance.getValueCoin >= listCost[index]) {
                    game.dataStore.updateItemCountByIndex(index) { count -> if (count < listCount[index]) {
                        val newCount = count + 1

                        game.soundUtil.apply { play(buy, 0.15f) }
                        panelBalance.updateCoin(true) { coin -> coin - listCost[index] }
                        listLblCount[index].setText("$newCount/${listCount[index]}")

                        game.dataStore.updateLVL { lvl -> if (lvl + 1 <= 10) lvl + 1 else lvl }
                        checkLVL()

                        newCount
                    } else {
                        count
                    } }
                }
            }
        }
    }

    private fun AdvancedStage.addList_LblCount() {
        var nx = 111f
        var ny = 1849f

        listLblCount.onEachIndexed { index, lbl ->
            addActor(lbl)
            lbl.setBounds(nx, ny, 71f, 44f)
            nx += 388+71
            if (index.inc() % 2 == 0) {
                nx = 111f
                ny -= 500+44
            }

            lbl.setText("${game.dataStore.getItemCountByIndex(index)}/${listCount[index]}")
        }

    }

    private fun AdvancedStage.addList_ImgClosed() {
        var nx = 79f
        var ny = 1405f

        listImgClose.onEachIndexed { index, image ->
            addActor(image)
            image.setBounds(nx, ny, 426f, 520f)
            nx += 33+426
            if (index.inc() % 2 == 0) {
                nx = 79f
                ny -= 13+520
            }
        }

        checkLVL()
    }


    // Logic ------------------------------------------------------------------------------

    private fun checkLVL() {
        when(LVL) {
            in (9..10) -> {
                listImgClose.onEach { it.addAction(Actions.removeActor()) }
            }
            8 -> {
                listImgClose.take(5).onEach { it.addAction(Actions.removeActor()) }
            }
            in (6..7) -> {
                listImgClose.take(4).onEach { it.addAction(Actions.removeActor()) }
            }
            in (4..5) -> {
                listImgClose.take(3).onEach { it.addAction(Actions.removeActor()) }
            }
            in (2..3) -> {
                listImgClose.take(2).onEach { it.addAction(Actions.removeActor()) }
            }
            1 -> {
                listImgClose.take(1).onEach { it.addAction(Actions.removeActor()) }
            }
        }
    }



}