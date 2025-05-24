package com.ogonechek.putinvestor.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.ogonechek.putinvestor.game.actors.AInvest
import com.ogonechek.putinvestor.game.actors.APanel
import com.ogonechek.putinvestor.game.actors.AScrollPane
import com.ogonechek.putinvestor.game.actors.autoLayout.AVerticalGroup
import com.ogonechek.putinvestor.game.actors.button.AButton
import com.ogonechek.putinvestor.game.screens.AllScreen
import com.ogonechek.putinvestor.game.screens.PlusScreen
import com.ogonechek.putinvestor.game.utils.Block
import com.ogonechek.putinvestor.game.utils.GameColor
import com.ogonechek.putinvestor.game.utils.TIME_ANIM_SCREEN
import com.ogonechek.putinvestor.game.utils.actor.animHide
import com.ogonechek.putinvestor.game.utils.actor.animShow
import com.ogonechek.putinvestor.game.utils.actor.setOnClickListener
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedGroup
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.font.FontParameter
import com.ogonechek.putinvestor.game.utils.gdxGame

class AMainMenu(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val aPanel    = APanel(screen)
    private val btnPlus   = AButton(screen, AButton.Type.Plus)
    private val imgSeeAll = Image(gdxGame.assetsAll.s_all)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontB_58      = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(58))
    private val fontR_80      = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(80))
    private val fontR_58      = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(58))
    private val fontR_43      = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(43))

    private val lsB_58 = Label.LabelStyle(fontB_58, GameColor.blue_20)
    private val lsR_80 = Label.LabelStyle(fontR_80, GameColor.blue_20)
    private val lsR_58 = Label.LabelStyle(fontR_58, GameColor.blue_20)
    private val lsR_43 = Label.LabelStyle(fontR_43, GameColor.blue_20)

    private val listInvestData = gdxGame.ds_Invest.flow.value.reversed()

    private val vertical    = AVerticalGroup(screen, 6f, isWrap = true)
    private val scroll      = AScrollPane(vertical)
    private val listAInvest = List(listInvestData.size) { AInvest(
        screen, listInvestData[it],
        lsB_58,
        lsR_80,
        lsR_58,
        lsR_43,
    ) }

    override fun addActorsOnGroup() {
        color.a = 0f

        addScroll()
        addPanel()
        addBtnStart()
        addSeeAll()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addPanel() {
        addActor(aPanel)
        aPanel.setBounds(28f, 2106f, 1306f, 725f)
    }

    private fun addBtnStart() {
        addActor(btnPlus)
        btnPlus.setBounds(519f, 79f, 324f, 204f)

        btnPlus.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(PlusScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addSeeAll() {
        addActor(imgSeeAll)
        imgSeeAll.setBounds(72f, 2040f, 1216f, 73f)

        val aAll = Actor()
        addActor(aAll)
        aAll.setBounds(871f, 2010f, 440f, 132f)
        aAll.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AllScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(58f, 0f, 1246f, 2020f)

        vertical.setSize(1246f, 2020f)
        listAInvest.onEach { invest ->
            invest.setSize(1246f, 557f)
            vertical.addActor(invest)
        }
    }

    // Anim Main ------------------------------------------------

    private fun animShowMain() {
        animShow(TIME_ANIM_SCREEN)
    }

    fun animHideMain(block: Block = Block {}) {
        animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

}