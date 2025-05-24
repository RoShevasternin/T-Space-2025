package com.barabanovich.helowerskay.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.barabanovich.helowerskay.game.actors.button.AButton
import com.barabanovich.helowerskay.game.screens.GameScreen
import com.barabanovich.helowerskay.game.screens.ResultScreen
import com.barabanovich.helowerskay.game.utils.Block
import com.barabanovich.helowerskay.game.utils.GameColor
import com.barabanovich.helowerskay.game.utils.TIME_ANIM_SCREEN
import com.barabanovich.helowerskay.game.utils.actor.animDelay
import com.barabanovich.helowerskay.game.utils.actor.animHide
import com.barabanovich.helowerskay.game.utils.actor.animShow
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedMainGroup
import com.barabanovich.helowerskay.game.utils.font.FontParameter
import com.barabanovich.helowerskay.game.utils.gdxGame
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class AMainResult(
    override val screen: ResultScreen,
): AdvancedMainGroup() {

    private val resultIndex = when {
        GameScreen.WIN_COUNT <= 4 -> 0
        GameScreen.WIN_COUNT <= 8 -> 1
        GameScreen.WIN_COUNT <= 10 -> 2
        else -> 0
    }

    private val imgPhoto  = Image(gdxGame.assetsAll.listResult[resultIndex])
    private val btnNext   = AButton(screen, AButton.Type.Next)
    private val btnReset  = AButton(screen, AButton.Type.Reset)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPhoto()
        addBtnNext()
        addBtnReset()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPhoto() {
        addActor(imgPhoto)
        imgPhoto.setBounds(54f, 781f, 906f, 1006f)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(53f, 199f, 906f, 123f)

        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addBtnReset() {
        addActor(btnReset)
        btnReset.setBounds(53f, 374f, 906f, 123f)

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