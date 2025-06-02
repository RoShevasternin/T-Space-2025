    package com.cburaktev.klavregasd.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cburaktev.klavregasd.game.actors.button.AButton
import com.cburaktev.klavregasd.game.screens.MenuScreen
import com.cburaktev.klavregasd.game.screens.TestResultScreen
import com.cburaktev.klavregasd.game.screens.TestScreen
import com.cburaktev.klavregasd.game.screens.TestSelectScreen
import com.cburaktev.klavregasd.game.utils.Block
import com.cburaktev.klavregasd.game.utils.GameColor
import com.cburaktev.klavregasd.game.utils.TIME_ANIM_SCREEN
import com.cburaktev.klavregasd.game.utils.actor.animDelay
import com.cburaktev.klavregasd.game.utils.actor.animHide
import com.cburaktev.klavregasd.game.utils.actor.animShow
import com.cburaktev.klavregasd.game.utils.actor.setOnClickListener
import com.cburaktev.klavregasd.game.utils.advanced.AdvancedMainGroup
import com.cburaktev.klavregasd.game.utils.font.FontParameter
import com.cburaktev.klavregasd.game.utils.gdxGame

class AMainTestResult(
    override val screen: TestResultScreen,
): AdvancedMainGroup() {

    private val resultIndex = when {
        TestScreen.WIN_COUNT <= 4 -> 0
        TestScreen.WIN_COUNT <= 8 -> 1
        TestScreen.WIN_COUNT <= 10 -> 2
        else -> 0
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(51))

    private val ls51 = Label.LabelStyle(font51, GameColor.black_21)

    private val lblTitle  = Label("Результат", ls51)
    private val btnX      = AButton(screen, AButton.Type.X)
    private val imgResult = Image(gdxGame.assetsAll.listR[resultIndex])

    override fun addActorsOnGroup() {
        color.a = 0f

        addTitle()
        addImgResult()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(322f, 1851f, 324f, 99f)
        lblTitle.setAlignment(Align.center)

        addActor(btnX)
        btnX.apply {
            setBounds(847f, 1851f, 77f, 77f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
    }

    private fun addImgResult() {
        addActor(imgResult)
        imgResult.setBounds(58f, 920f, 854f, 854f)

        val listBtn = List(2) { Actor() }
        var ny = 1078f

        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(58f, ny, 853f, 119f)
            ny -= 39 + 119

            btn.setOnClickListener(gdxGame.soundUtil) {
                when(index) {
                    0 -> {
                        screen.hideScreen {
                            gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                        }
                    }
                    1 -> {
                        screen.hideScreen {
                            gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(TestSelectScreen::class.java.name)
                        }
                    }
                }
            }
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

}