package com.romander.navfenixgater.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.romander.navfenixgater.game.actors.ABottom
import com.romander.navfenixgater.game.actors.AScrollPane
import com.romander.navfenixgater.game.screens.CalculatorScreen
import com.romander.navfenixgater.game.screens.HistoryScreen
import com.romander.navfenixgater.game.screens.MenuScreen
import com.romander.navfenixgater.game.utils.Block
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.TIME_ANIM_SCREEN
import com.romander.navfenixgater.game.utils.actor.animDelay
import com.romander.navfenixgater.game.utils.actor.animHide
import com.romander.navfenixgater.game.utils.actor.animShow
import com.romander.navfenixgater.game.utils.advanced.AdvancedMainGroup
import com.romander.navfenixgater.game.utils.font.FontParameter
import com.romander.navfenixgater.game.utils.gdxGame
import com.romander.navfenixgater.game.actors.autoLayout.AVerticalGroup
import com.romander.navfenixgater.game.actors.autoLayout.AutoLayout
import com.romander.navfenixgater.game.actors.items.AItemCredit
import com.romander.navfenixgater.game.actors.items.AItemDeposit
import com.romander.navfenixgater.game.actors.items.AItemIpoteka
import com.romander.navfenixgater.game.actors.items.AItemLizing
import com.romander.navfenixgater.game.screens.ResultScreen
import com.romander.navfenixgater.game.utils.actor.setOnTouchListener

class AMainHistory(
    override val screen: HistoryScreen,
    val aBottom: ABottom,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))

    private val ls51 = Label.LabelStyle(font51, GameColor.black_39)

    private val lblTitle = Label("История", ls51)

    private val verticalGroup = AVerticalGroup(screen, 37f, alignmentHorizontal = AutoLayout.AlignmentHorizontal.CENTER, isWrap = true)
    private val scroll        = AScrollPane(verticalGroup)

    override fun addActorsOnGroup() {
        color.a = 0f

        addLblTitle()
        addScroll()

        animShowMain()

        handlerABottom()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(240f, 1200f, 212f, 76f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 145f, 692f, 1035f)

        verticalGroup.setSize(692f, 1035f)

        gdxGame.ds_LizingData.flow.value.forEach {
            verticalGroup.addActor(AItemLizing(screen, it).also { item ->
                item.setOnTouchListener(gdxGame.soundUtil) {
                    AMainResult.SHOWED_ITEM = item.data
                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(ResultScreen::class.java.name, screen::class.java.name)
                    }
                }
                item.setSize(609f, 406f) })
        }
        gdxGame.ds_CreditData.flow.value.forEach {
            verticalGroup.addActor(AItemCredit(screen, it).also { item ->
                item.setOnTouchListener(gdxGame.soundUtil) {
                    AMainResult.SHOWED_ITEM = item.data
                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(ResultScreen::class.java.name, screen::class.java.name)
                    }
                }
                item.setSize(609f, 406f) })
        }
        gdxGame.ds_IpotekaData.flow.value.forEach {
            verticalGroup.addActor(AItemIpoteka(screen, it).also { item ->
                item.setOnTouchListener(gdxGame.soundUtil) {
                    AMainResult.SHOWED_ITEM = item.data
                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(ResultScreen::class.java.name, screen::class.java.name)
                    }
                }
                item.setSize(609f, 406f) })
        }
        gdxGame.ds_DepositData.flow.value.forEach {
            verticalGroup.addActor(AItemDeposit(screen, it).also { item ->
                item.setOnTouchListener(gdxGame.soundUtil) {
                    AMainResult.SHOWED_ITEM = item.data
                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(ResultScreen::class.java.name, screen::class.java.name)
                    }
                }
                item.setSize(609f, 236f) })
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ------------------------------------------------

    private fun handlerABottom() {
        aBottom.blockGlav = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name, screen::class.java.name)
            }
        }
        aBottom.blockCalc = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(CalculatorScreen::class.java.name, screen::class.java.name)
            }
        }
        aBottom.blockHist = {}
    }

}