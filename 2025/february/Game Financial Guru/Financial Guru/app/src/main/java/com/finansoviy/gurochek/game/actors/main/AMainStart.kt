package com.finansoviy.gurochek.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.finansoviy.gurochek.game.actors.button.AButton
import com.finansoviy.gurochek.game.screens.CategoryScreen
import com.finansoviy.gurochek.game.screens.StartScreen
import com.finansoviy.gurochek.game.utils.Block
import com.finansoviy.gurochek.game.utils.TIME_ANIM_SCREEN
import com.finansoviy.gurochek.game.utils.actor.animDelay
import com.finansoviy.gurochek.game.utils.actor.animHide
import com.finansoviy.gurochek.game.utils.actor.animShow
import com.finansoviy.gurochek.game.utils.advanced.AdvancedMainGroup
import com.finansoviy.gurochek.game.utils.gdxGame

class AMainStart(
    override val screen: StartScreen,
): AdvancedMainGroup() {

    private val imgPhoto = Image(gdxGame.assetsAll.photo)
    private val btnStart = AButton(screen, AButton.Type.Start)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPhoto()
        addBtnStart()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPhoto() {
        addActor(imgPhoto)
        imgPhoto.setBounds(37f, 469f, 928f, 1440f)
    }

    private fun addBtnStart() {
        addActor(btnStart)
        btnStart.setBounds(53f, 184f, 895f, 134f)

        btnStart.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(CategoryScreen::class.java.name, screen::class.java.name)
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