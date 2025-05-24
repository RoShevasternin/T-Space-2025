package com.barabanovich.helowerskay.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.barabanovich.helowerskay.game.actors.button.AButton
import com.barabanovich.helowerskay.game.screens.CategoryScreen
import com.barabanovich.helowerskay.game.screens.StartScreen
import com.barabanovich.helowerskay.game.utils.Block
import com.barabanovich.helowerskay.game.utils.TIME_ANIM_SCREEN
import com.barabanovich.helowerskay.game.utils.actor.animDelay
import com.barabanovich.helowerskay.game.utils.actor.animHide
import com.barabanovich.helowerskay.game.utils.actor.animShow
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedMainGroup
import com.barabanovich.helowerskay.game.utils.gdxGame

class AMainStart(
    override val screen: StartScreen,
): AdvancedMainGroup() {

    private val imgPhoto = Image(gdxGame.assetsAll.prognoz)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPhoto()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPhoto() {
        addActor(imgPhoto)
        imgPhoto.setBounds(54f, 456f, 905f, 319f)

        this.animDelay(2f) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(CategoryScreen::class.java.name)
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