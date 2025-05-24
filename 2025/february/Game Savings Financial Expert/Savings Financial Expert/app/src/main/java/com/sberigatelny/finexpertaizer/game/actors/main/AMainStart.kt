package com.sberigatelny.finexpertaizer.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sberigatelny.finexpertaizer.game.screens.GameScreen
import com.sberigatelny.finexpertaizer.game.screens.StartScreen
import com.sberigatelny.finexpertaizer.game.utils.Block
import com.sberigatelny.finexpertaizer.game.utils.TIME_ANIM_SCREEN
import com.sberigatelny.finexpertaizer.game.utils.actor.animDelay
import com.sberigatelny.finexpertaizer.game.utils.actor.animHide
import com.sberigatelny.finexpertaizer.game.utils.actor.animShow
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedMainGroup
import com.sberigatelny.finexpertaizer.game.utils.gdxGame

class AMainStart(
    override val screen: StartScreen,
): AdvancedMainGroup() {

    private val imgDialog = Image(gdxGame.assetsAll.d1)
    private val imgChel   = Image(gdxGame.assetsAll.chel)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgAll()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgAll() {
        addActors(imgDialog, imgChel)
        imgDialog.setBounds(52f, 1108f, 881f, 295f)
        imgChel.setBounds(230f, 29f, 526f, 959f)

        this.animDelay(3f) {
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