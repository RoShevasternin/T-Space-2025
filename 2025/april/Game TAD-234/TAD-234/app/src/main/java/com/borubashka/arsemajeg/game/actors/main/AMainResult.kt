package com.borubashka.arsemajeg.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.borubashka.arsemajeg.game.actors.button.AButton
import com.borubashka.arsemajeg.game.actors.button.ATextButton
import com.borubashka.arsemajeg.game.screens.LevelsScreen
import com.borubashka.arsemajeg.game.screens.QuizScreen
import com.borubashka.arsemajeg.game.screens.ResultScreen
import com.borubashka.arsemajeg.game.utils.Block
import com.borubashka.arsemajeg.game.utils.GameColor
import com.borubashka.arsemajeg.game.utils.TIME_ANIM_SCREEN
import com.borubashka.arsemajeg.game.utils.actor.animDelay
import com.borubashka.arsemajeg.game.utils.actor.animHide
import com.borubashka.arsemajeg.game.utils.actor.animShow
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedMainGroup
import com.borubashka.arsemajeg.game.utils.font.FontParameter
import com.borubashka.arsemajeg.game.utils.gdxGame
import com.borubashka.arsemajeg.util.log

class AMainResult(override val screen: ResultScreen): AdvancedMainGroup() {

    private val resultIndex = when {
        QuizScreen.WIN_COUNT <= 1 -> 0
        QuizScreen.WIN_COUNT <= 3 -> 1
        QuizScreen.WIN_COUNT <= 5 -> 2
        else -> 0
    }

    private val resultText = listOf(
        "Уровень не пройден...",
        "Уровень пройден!",
        "Уровень пройден!",
    )[resultIndex]

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))

    private val ls51B = Label.LabelStyle(font51, GameColor.blue)
    private val ls51G = Label.LabelStyle(font51, GameColor.gray)

    private val imgPanel   = Image(gdxGame.assetsAll.panel_white)
    private val lblTitle   = Label(resultText, ls51B)
    private val imgResult  = Image(gdxGame.assetsAll.listResult[resultIndex])
    private val btnRestart = ATextButton(screen, "Начать сначала", ls51G, AButton.Type.White)
    private val btnNext    = ATextButton(screen, "Дальше", ls51G, AButton.Type.White)

    override fun addActorsOnGroup() {
        setBonuses()

        screen.stageBack.root.color.a = 0f
        color.a = 0f

        addBtns()
        addImgPanel()
        addLblResult()
        addImgResult()

        animShowMain()
    }

    override fun dispose() {
        super.dispose()
        QuizScreen.WIN_COUNT = 0
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtns() {
        addActors(btnRestart, btnNext)
        btnRestart.setBounds(49f, 296f, 827f, 123f)
        btnNext.setBounds(49f, 111f, 827f, 123f)

        btnRestart.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(QuizScreen::class.java.name)
            }
        }
        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(LevelsScreen::class.java.name)
            }
        }
    }

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(49f, 1659f, 828f, 198f)
    }

    private fun addLblResult() {
        addActor(lblTitle)
        lblTitle.setBounds(74f, 1696f, 777f, 123f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addImgResult() {
        addActor(imgResult)
        imgResult.setBounds(98f, 817f, 729f, 539f)
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

    // Logic ------------------------------------------------

    private val listScore = listOf(10, 30, 50)

    private fun setBonuses() {
        gdxGame.ds_Score.update { it + listScore[resultIndex] }

        val nextIndex = if (LevelsScreen.SELECTED_LVL_INDEX < 11) LevelsScreen.SELECTED_LVL_INDEX + 1 else 11

        gdxGame.ds_ItemData.update {
            val mData = it.toMutableList()
            mData[LevelsScreen.SELECTED_LVL_INDEX].also { data ->
                data.stars  = resultIndex.inc()
            }
            mData[nextIndex].also { data ->
                data.isLock = false
            }
            mData
        }
    }

}