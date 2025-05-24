package com.ayrym.inperdader.game.actors

import com.ayrym.inperdader.game.screens.MenuScreen
import com.ayrym.inperdader.game.screens.QuizScreen
import com.ayrym.inperdader.game.utils.SizeScaler
import com.ayrym.inperdader.game.utils.actor.setBoundsScaled
import com.ayrym.inperdader.game.utils.actor.setOnClickListener
import com.ayrym.inperdader.game.utils.advanced.AdvancedGroup
import com.ayrym.inperdader.game.utils.advanced.AdvancedScreen
import com.ayrym.inperdader.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image

class ABottom(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 803f)

    private val imgPanel = Image(gdxGame.assetsAll.bottom)
    private val imgMenu  = Image(gdxGame.assetsAll.MENU)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addActor(imgMenu)

        imgMenu.setBoundsScaled(sizeScaler, -8f, 471f, 818f, 965f)

        var ny = 1125f
        List(3) { Actor() }.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBoundsScaled(sizeScaler, 51f, ny, 700f, 252f)
            ny -= 50 + 252

            actor.setOnClickListener(gdxGame.soundUtil) {
                MenuScreen.CURRENT_QUIZ_INDEX = index
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(QuizScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }

}