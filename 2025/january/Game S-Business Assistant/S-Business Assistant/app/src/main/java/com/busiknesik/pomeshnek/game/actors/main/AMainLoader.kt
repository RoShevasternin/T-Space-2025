package com.busiknesik.pomeshnek.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.busiknesik.pomeshnek.game.actors.progress.AProgress
import com.busiknesik.pomeshnek.game.utils.Block
import com.busiknesik.pomeshnek.game.utils.TIME_ANIM_SCREEN
import com.busiknesik.pomeshnek.game.utils.actor.animHide
import com.busiknesik.pomeshnek.game.utils.actor.animShow
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedGroup
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.gdxGame

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val logo = Image(gdxGame.assetsLoader.logo)
    val prog = AProgress(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addLogo()
        addProg()

        animShowMain()

    }

    // Actors ------------------------------------------------------------------------

    private fun addLogo() {
        addActor(logo)
        logo.setBounds(79f, 1037f, 1033f, 512f)
    }

    private fun addProg() {
        addActor(prog)
        prog.setBounds(198f, 1196f, 795f, 33f)
    }

    // Anim Main ------------------------------------------------

    private fun animShowMain() {
        animShow(TIME_ANIM_SCREEN)
    }

    fun animHideMain(block: Block = Block {}) {
        animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

}