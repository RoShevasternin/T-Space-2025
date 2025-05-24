package com.traoya.yatirimya.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.traoya.yatirimya.game.actors.ABalance
import com.traoya.yatirimya.game.actors.button.AButton
import com.traoya.yatirimya.game.screens.GelyScreen
import com.traoya.yatirimya.game.screens.MenuScreen
import com.traoya.yatirimya.game.utils.*
import com.traoya.yatirimya.game.utils.actor.animDelay
import com.traoya.yatirimya.game.utils.actor.animHide
import com.traoya.yatirimya.game.utils.actor.animShow
import com.traoya.yatirimya.game.utils.actor.setBounds
import com.traoya.yatirimya.game.utils.actor.setOnClickListener
import com.traoya.yatirimya.game.utils.advanced.AdvancedMainGroup
import com.traoya.yatirimya.game.utils.font.FontParameter

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "/")
    private val font44        = screen.fontGenerator_SemiBold.generateFont(fontParameter.setSize(44))

    private val ls44 = Label.LabelStyle(font44, GameColor.black)


    private val aBalance = ABalance(screen)
    private val imgPanel = Image(gdxGame.assetsAll.BUTTONS)

    private val btnGL = AButton(screen, AButton.Type.GEL)
    private val btnNF = AButton(screen, AButton.Type.NAFTA)

    private val listLbl = List(6) { Label("", ls44) }
    private val listA   = List(6) { Actor() }

    private val ddPokupki = gdxGame.ds_PokupkiData.flow.value

    override fun addActorsOnGroup() {
        color.a = 0f

        addABalance()
        addImgBtns()
        addBtns()
        addBtnRN()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addABalance() {
        addActor(aBalance)
        aBalance.setBounds(0f, 2030f, 1040f, 125f)
    }

    private fun addImgBtns() {
        addActor(imgPanel)
        imgPanel.setBounds(83f, 346f, 875f, 1560f)
    }

    private fun addBtnRN() {
        addActors(btnGL, btnNF)
        btnGL.setBounds(532f, 67f, 426f, 140f)
        btnNF.setBounds(83f, 67f, 426f, 140f)

        btnGL.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(GelyScreen::class.java.name, screen::class.java.name)
            }
        }
        btnNF.setOnClickListener {
            if (gdxGame.ds_Gel.flow.value > 0) {
                gdxGame.ds_Gold.update { it + gdxGame.ds_Gel.flow.value }
                gdxGame.ds_Gel.update { 0 }
            }
        }
    }

    private fun addBtns() {
        var nx = 83f
        var ny = 1417f

        val listLblValue = listOf(2, 2, 2, 2, 1, 1)
        val listPrice    = listOf(500, 1000, 5000, 10000, 15000, 25000)

        listA.forEachIndexed { index, button ->
            addActor(button)
            button.setBounds(nx, ny, 416f, 488f)

            listLbl[index].also { lbl ->
                addActor(lbl)
                lbl.setBounds(nx + 27, ny + 419, 71f, 44f)

                lbl.setText("${ddPokupki[index]}/${listLblValue[index]}")

                if (index.inc() > 4) {
                    lbl.y -= 20f
                }
            }

            nx += 42 + 416
            if (index.inc() % 2 == 0) {
                nx = 83f
                ny -= 25 + 488
            }

            button.setOnClickListener(gdxGame.soundUtil) {
                if (
                    gdxGame.ds_PokupkiData.flow.value[index] < listLblValue[index] &&
                    gdxGame.ds_Gold.flow.value >= listPrice[index]
                ) {
                    gdxGame.ds_Gold.update { it - listPrice[index] }
                    gdxGame.ds_PokupkiData.update {
                        val mList = it.toMutableList()
                        mList[index] += 1
                        mList
                    }
                    gdxGame.ds_Level.update { if (it < 9) it + 1 else it }

                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                    }
                } else gdxGame.soundUtil.apply { play(fail) }
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}