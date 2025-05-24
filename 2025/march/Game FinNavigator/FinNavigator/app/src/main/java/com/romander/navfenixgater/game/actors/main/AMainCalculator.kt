package com.romander.navfenixgater.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.romander.navfenixgater.game.actors.ABottom
import com.romander.navfenixgater.game.actors.ADownList
import com.romander.navfenixgater.game.actors.button.AButton
import com.romander.navfenixgater.game.actors.calculators.ACalculatorAnimator
import com.romander.navfenixgater.game.actors.calculators.ACalculatorCredit
import com.romander.navfenixgater.game.actors.calculators.ACalculatorDeposit
import com.romander.navfenixgater.game.actors.calculators.ACalculatorInvest
import com.romander.navfenixgater.game.actors.calculators.ACalculatorIpoteka
import com.romander.navfenixgater.game.actors.calculators.ACalculatorLizing
import com.romander.navfenixgater.game.screens.CalculatorScreen
import com.romander.navfenixgater.game.screens.HistoryScreen
import com.romander.navfenixgater.game.screens.MenuScreen
import com.romander.navfenixgater.game.utils.Block
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.TIME_ANIM_SCREEN
import com.romander.navfenixgater.game.utils.actor.animDelay
import com.romander.navfenixgater.game.utils.actor.animHide
import com.romander.navfenixgater.game.utils.actor.animShow
import com.romander.navfenixgater.game.utils.actor.setBounds
import com.romander.navfenixgater.game.utils.advanced.AdvancedMainGroup
import com.romander.navfenixgater.game.utils.font.FontParameter
import com.romander.navfenixgater.game.utils.gdxGame

class AMainCalculator(
    override val screen: CalculatorScreen,
    val aBottom: ABottom,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))
    private val font30        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(30))

    private val ls51 = Label.LabelStyle(font51, GameColor.black_39)
    private val ls30 = Label.LabelStyle(font30, GameColor.black_39)

    private val lblTitle  = Label("Калькулятор", ls51)
    private val lblType   = Label("Тип", ls30)
    private val btnReset  = AButton(screen, AButton.Type.Reset)
    private val aDownList = ADownList(screen)

    private var aCalculatorAnimator: ACalculatorAnimator = ACalculatorLizing(screen)
    private var currentType = ADownList.Type.Lizing

    override fun addActorsOnGroup() {
        color.a = 0f

        addLblTitle()
        addBtnReset()
        addLblType()
        addACalculatorAnimator()
        addADownList()

        animShowMain()

        handlerABottom()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnReset() {
        addActor(btnReset)
        btnReset.setBounds(591f, 1192f, 91f, 91f)

        btnReset.setOnClickListener {
            val newACalculatorAnimator = when (currentType) {
                ADownList.Type.Lizing  -> ACalculatorLizing(screen)
                ADownList.Type.Credit  -> ACalculatorCredit(screen)
                ADownList.Type.Ipoteca -> ACalculatorIpoteka(screen)
                ADownList.Type.Deposit -> ACalculatorDeposit(screen)
                ADownList.Type.Invest  -> ACalculatorInvest(screen)
            }
            changeACalculatorAnimator(newACalculatorAnimator)
        }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(240f, 1200f, 212f, 76f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addLblType() {
        addActor(lblType)
        lblType.setBounds(41f, 1142f, 55f, 38f)
    }

    private fun addADownList() {
        addActor(aDownList)
        aDownList.setBounds(41f, 1040f, 610f, 84f)
        aDownList.blockSelectType = {
            currentType = it
            val newACalculatorAnimator = when (it) {
                ADownList.Type.Lizing  -> ACalculatorLizing(screen)
                ADownList.Type.Credit  -> ACalculatorCredit(screen)
                ADownList.Type.Ipoteca -> ACalculatorIpoteka(screen)
                ADownList.Type.Deposit -> ACalculatorDeposit(screen)
                ADownList.Type.Invest  -> ACalculatorInvest(screen)
            }
            changeACalculatorAnimator(newACalculatorAnimator)
        }
    }

    private fun addACalculatorAnimator() {
        addActor(aCalculatorAnimator)
        aCalculatorAnimator.setBounds(41f, 164f, 609f, 841f)
        aCalculatorAnimator.zIndex = 0
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
        aBottom.blockCalc = {}
        aBottom.blockHist = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun changeACalculatorAnimator(newACalculatorAnimator: ACalculatorAnimator) {
        aCalculatorAnimator.remove()
        aCalculatorAnimator = newACalculatorAnimator
        addACalculatorAnimator()
    }

}