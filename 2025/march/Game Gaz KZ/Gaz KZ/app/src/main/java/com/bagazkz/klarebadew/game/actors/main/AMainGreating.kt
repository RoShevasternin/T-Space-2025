package com.bagazkz.klarebadew.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bagazkz.klarebadew.game.actors.button.AButton
import com.bagazkz.klarebadew.game.screens.GreatingScreen
import com.bagazkz.klarebadew.game.screens.MenuScreen
import com.bagazkz.klarebadew.game.utils.Block
import com.bagazkz.klarebadew.game.utils.TIME_ANIM_SCREEN
import com.bagazkz.klarebadew.game.utils.actor.animDelay
import com.bagazkz.klarebadew.game.utils.actor.animHide
import com.bagazkz.klarebadew.game.utils.actor.animShow
import com.bagazkz.klarebadew.game.utils.actor.setBounds
import com.bagazkz.klarebadew.game.utils.actor.setOnClickListener
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedMainGroup
import com.bagazkz.klarebadew.game.utils.gdxGame

class AMainGreating(
    override val screen: GreatingScreen,
): AdvancedMainGroup() {

    private val imgDialog = Image(gdxGame.assetsAll.DIALOG)
    private val btnX      = AButton(screen, AButton.Type.X)

    override fun addActorsOnGroup() {
        color.a = 0f

        addDialog()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addDialog() {
        addActor(imgDialog)
        imgDialog.setBounds(173f, 735f, 611f, 609f)

        addActor(btnX)
        btnX.setBounds(742f, 1862f, 215f, 215f)
        btnX.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }

        val aStart = Actor()
        addActor(aStart)
        aStart.setBounds(324f, 761f, 307f, 86f)
        aStart.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
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