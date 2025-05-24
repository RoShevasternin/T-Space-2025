package com.romander.navfenixgater.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.romander.navfenixgater.game.actors.ABottom
import com.romander.navfenixgater.game.actors.button.AButton
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
import com.romander.navfenixgater.game.utils.toSeparateWithSymbol

class AMainMenu(
    override val screen: MenuScreen,
    val aBottom: ABottom,
): AdvancedMainGroup() {

    private val _1_SUMMA = gdxGame.ds_LizingData.flow.value.sumOf { it.totalPayments } +
            gdxGame.ds_CreditData.flow.value.sumOf { it.totalPayment } +
            gdxGame.ds_IpotekaData.flow.value.sumOf { it.totalPayment } +
            gdxGame.ds_DepositData.flow.value.sumOf { it.totalInterest }
    private val _2_SUMMA = gdxGame.ds_LizingData.flow.value.sumOf { it.monthlyPayment } +
            gdxGame.ds_CreditData.flow.value.sumOf { it.creditBody } +
            gdxGame.ds_IpotekaData.flow.value.sumOf { it.interestPaid } +
            gdxGame.ds_DepositData.flow.value.sumOf { it.totalBody }
    private val _3_SUMMA = gdxGame.ds_DepositData.flow.value.sumOf { it.totalInterest }
    private val _4_SUMMA = gdxGame.ds_CreditData.flow.value.sumOf { it.totalPayment }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))

    private val ls51 = Label.LabelStyle(font51, GameColor.black_39)

    private val imgText = Image(gdxGame.assetsAll.MAINTEXT)
    private val btnNew  = AButton(screen, AButton.Type.New)

    private val lblTitle = Label("Главная", ls51)
    private val lbl1     = Label(_1_SUMMA.toSeparateWithSymbol(' '), ls51)
    private val lbl2     = Label(_2_SUMMA.toSeparateWithSymbol(' '), ls51)
    private val lbl3     = Label(_3_SUMMA.toSeparateWithSymbol(' '), ls51)
    private val lbl4     = Label(_4_SUMMA.toSeparateWithSymbol(' '), ls51)

    override fun addActorsOnGroup() {
        color.a = 0f

        addText()
        addLblTitle()
        addBtns()
        addLbls()

        animShowMain()

        handlerABottom()
    }

    // Actors ------------------------------------------------------------------------

    private fun addText() {
        addActor(imgText)
        imgText.setBounds(41f, 466f, 609f, 632f)
    }

    private fun addBtns() {
        addActor(btnNew)
        btnNew.setBounds(41f, 240f, 610f, 84f)

        btnNew.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(CalculatorScreen::class.java.name, this::class.java.name)
            }
        }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(240f, 1200f, 212f, 76f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addLbls() {
        addActors(lbl1, lbl2, lbl3, lbl4)
        lbl1.setBounds(42f, 968f, 609f, 76f)
        lbl2.setBounds(42f, 786f, 609f, 76f)
        lbl3.setBounds(42f, 558f, 609f, 76f)
        lbl4.setBounds(42f, 390f, 609f, 76f)
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
        aBottom.blockGlav = {}
        aBottom.blockCalc = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(CalculatorScreen::class.java.name, screen::class.java.name)
            }
        }
        aBottom.blockHist = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
    }

}