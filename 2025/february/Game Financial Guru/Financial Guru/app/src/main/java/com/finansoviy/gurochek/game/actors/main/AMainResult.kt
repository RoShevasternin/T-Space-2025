package com.finansoviy.gurochek.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.finansoviy.gurochek.game.actors.button.AButton
import com.finansoviy.gurochek.game.screens.CategoryScreen
import com.finansoviy.gurochek.game.screens.GameScreen
import com.finansoviy.gurochek.game.screens.ResultScreen
import com.finansoviy.gurochek.game.screens.StartScreen
import com.finansoviy.gurochek.game.utils.Block
import com.finansoviy.gurochek.game.utils.GameColor
import com.finansoviy.gurochek.game.utils.TIME_ANIM_SCREEN
import com.finansoviy.gurochek.game.utils.actor.animDelay
import com.finansoviy.gurochek.game.utils.actor.animHide
import com.finansoviy.gurochek.game.utils.actor.animShow
import com.finansoviy.gurochek.game.utils.actor.setBounds
import com.finansoviy.gurochek.game.utils.advanced.AdvancedMainGroup
import com.finansoviy.gurochek.game.utils.font.FontParameter
import com.finansoviy.gurochek.game.utils.gdxGame
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class AMainResult(
    override val screen: ResultScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font      = screen.fontGenerator_Bold.generateFont(parameter.setSize(58))
    private val ls        =  Label.LabelStyle(font, GameColor.black_1E)

    private val resultIndex = when {
        GameScreen.WIN_COUNT <= 4 -> 0
        GameScreen.WIN_COUNT <= 8 -> 1
        GameScreen.WIN_COUNT <= 10 -> 2
        else -> 0
    }

    private val imgPhoto  = Image(gdxGame.assetsAll.listResult[resultIndex])
    private val btnNext   = AButton(screen, AButton.Type.Next)
    private val btnReset  = AButton(screen, AButton.Type.Reset)
    private val lblResult = Label("${GameScreen.WIN_COUNT.absoluteValue} из 10", ls)

    override fun addActorsOnGroup() {
        val onePercent    = 10 / 100f
        val resultPercent = (GameScreen.WIN_COUNT.absoluteValue / onePercent).roundToInt()

        when(CategoryScreen.CURRENT_QUIZ_INDEX) {
            0 -> gdxGame.ds_Percent.update { it.apply { quiz1 = resultPercent } }
            1 -> gdxGame.ds_Percent.update { it.apply { quiz2 = resultPercent } }
            2 -> gdxGame.ds_Percent.update { it.apply { quiz3 = resultPercent } }
            3 -> gdxGame.ds_Percent.update { it.apply { quiz4 = resultPercent } }
            4 -> gdxGame.ds_Percent.update { it.apply { quiz5 = resultPercent } }
        }

        color.a = 0f

        addImgPhoto()
        addBtnNext()
        addBtnReset()

        addActor(lblResult)
        lblResult.setBounds(397f, 572f, 206f, 75f)
        lblResult.setAlignment(Align.center)

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPhoto() {
        addActor(imgPhoto)
        imgPhoto.setBounds(37f, 469f, 928f, 1440f)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(194f, 400f, 614f, 134f)

        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addBtnReset() {
        addActor(btnReset)
        btnReset.setBounds(194f, 226f, 614f, 134f)

        btnReset.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name)
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}