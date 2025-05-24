package com.axmeron.investnaveratep.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.axmeron.investnaveratep.game.actors.ATestPanel
import com.axmeron.investnaveratep.game.actors.autoLayout.AVerticalGroup
import com.axmeron.investnaveratep.game.actors.autoLayout.AutoLayout
import com.axmeron.investnaveratep.game.actors.button.AButton
import com.axmeron.investnaveratep.game.screens.MenuScreen
import com.axmeron.investnaveratep.game.screens.TestScreen
import com.axmeron.investnaveratep.game.utils.Block
import com.axmeron.investnaveratep.game.utils.TIME_ANIM_SCREEN
import com.axmeron.investnaveratep.game.utils.actor.animHideSuspend
import com.axmeron.investnaveratep.game.utils.actor.animShowSuspend
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.font.FontParameter
import com.axmeron.investnaveratep.game.utils.gdxGame
import com.axmeron.investnaveratep.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainStartTest(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val currentTitle = listOf(
        "Как хорошо вы понимаете инвестиции?",
        "Ваш уровень финансовой грамотности",
        "Готовы ли вы начать инвестировать?",
        "Умеете ли вы планировать бюджет?",
    )[MenuScreen.SELECTED_TEST_INDEX]
    private val currentText  = listOf(
        "Инвестиции — это не просто способ заработать деньги, \n" +
                "но и искусство их управления. Знаете ли вы, что такое акции, облигации, дивиденды и сложный процент? А как выбрать стратегию, которая подойдет именно вам? Этот тест проверит ваши знания об основах инвестирования, различных финансовых инструментах и их особенностях. Погрузитесь в мир инвестиций, узнайте, насколько вы готовы \n" +
                "к принятию грамотных финансовых решений, и получите опыт по улучшению вашего подхода. Вперед к росту капитала!",
        "Финансовая грамотность — это \n" +
                "не только умение считать деньги, но и понимание того, как ими управлять. Знаете ли вы, что такое финансовая подушка безопасности, как работают проценты по вкладам и чем дебетовая карта отличается \n" +
                "от кредитной? Этот тест оценит, насколько уверенно \n" +
                "вы управляете своим бюджетом, планируете расходы и принимаете решения о сбережениях. Узнайте, насколько вы финансово подкованы, и откройте для себя новые горизонты эффективного управления личными финансами",
        "Инвестиции — это важный шаг \n" +
                "к вашему финансовому благополучию, но готовы \n" +
                "ли вы к этому? Этот тест поможет вам определить, насколько \n" +
                "вы подготовлены к началу инвестирования. \n" +
                "Есть ли у вас финансовая подушка безопасности? \n" +
                "Знаете ли вы, как управлять рисками? Этот тест выявит ваши сильные стороны и слабые места \n" +
                "в подготовке к инвестициям, \n" +
                "а также подскажет, как лучше начать свой путь \n" +
                "в мир финансовых вложений. Сделайте первый шаг \n" +
                "к уверенности в своих инвестициях!",
        "Планирование бюджета — это основа финансового успеха. Умеете ли вы отслеживать свои доходы и расходы, определять приоритеты и эффективно распределять ресурсы? Этот тест проверит ваши навыки управления личными финансами и покажет, насколько вы готовы \n" +
                "к выполнению финансовых целей. Узнайте, как сократить ненужные траты, увеличить сбережения \n" +
                "и добиться стабильности в своих финансах. Это ваш первый \n" +
                "шаг к финансовой независимости!",
    )[MenuScreen.SELECTED_TEST_INDEX]

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontSB_43     = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(43))

    private val lsSB_43 = Label.LabelStyle(fontSB_43, Color.WHITE)

    private val btnBack  = AButton(screen, AButton.Type.Back)
    private val lblTitle = Label(MenuScreen.SELECTED_TEST_INDEX.inc().toString() + " из 4", lsSB_43)

    private val vertical  = AVerticalGroup(screen, 63f, alignmentHorizontal = AutoLayout.AlignmentHorizontal.CENTER)
    private val testPanel = ATestPanel(screen, currentTitle, currentText)
    private val btnStart  = AButton(screen, AButton.Type.Start)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addBtnBack()
                addLblTitle()
                addVertical()

            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(53f, 1529f, 75f, 75f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }

    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(337f, 1550f, 119f, 30f)
    }

    // Vertical Actors -----------------------------------------------------------------------------

    private fun addVertical() {
        addActor(vertical)
        vertical.apply {
            setBounds(54f, 0f, 696f, 1398f)

            addTestPanel()
            addBtnStart()

        }
    }

    private fun AVerticalGroup.addTestPanel() {
        testPanel.setSize(696f, 1f)
        addActor(testPanel)
    }

    private fun AVerticalGroup.addBtnStart() {
        btnStart.setSize(445f, 98f)
        addActor(btnStart)
        btnStart.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(TestScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        animShowSuspend(TIME_ANIM_SCREEN)
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

}