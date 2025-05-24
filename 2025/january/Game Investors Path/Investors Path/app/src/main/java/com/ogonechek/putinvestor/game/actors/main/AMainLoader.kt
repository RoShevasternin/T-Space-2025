package com.ogonechek.putinvestor.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ogonechek.putinvestor.game.actors.ALogo
import com.ogonechek.putinvestor.game.actors.shader.AFireGroup
import com.ogonechek.putinvestor.game.utils.Block
import com.ogonechek.putinvestor.game.utils.TIME_ANIM_SCREEN
import com.ogonechek.putinvestor.game.utils.actor.animHide
import com.ogonechek.putinvestor.game.utils.actor.animShow
import com.ogonechek.putinvestor.game.utils.actor.setBounds
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedGroup
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.gdxGame
import com.ogonechek.putinvestor.game.utils.runGDX
import com.ogonechek.putinvestor.util.log
import kotlinx.coroutines.launch

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val ogon  = Image(gdxGame.assetsLoader.ogon)
    private val aLogo = ALogo(screen)
    private val aFire = AFireGroup(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addOgon()
                addLogo()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addOgon() {
        addActor(aFire)
        aFire.setBounds(0f, 118f, 1362f, 2718f)
        aFire.addAndFillActor(ogon)
    }

    private fun addLogo() {
        addActor(aLogo)
        aLogo.setBounds(192f, 1634f, 960f, 306f)
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