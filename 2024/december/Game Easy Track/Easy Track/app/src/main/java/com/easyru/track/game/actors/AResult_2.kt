package com.easyru.track.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.easyru.track.game.utils.HEIGHT_UI
import com.easyru.track.game.utils.WIDTH_UI
import com.easyru.track.game.utils.actor.animShow
import com.easyru.track.game.utils.actor.disable
import com.easyru.track.game.utils.actor.setBounds
import com.easyru.track.game.utils.advanced.AdvancedGroup
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.gdxGame
import com.easyru.track.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AResult_2(override val screen: AdvancedScreen): AdvancedGroup(), ResultGroup {

    private val imgListCoin = List(8) { Image(gdxGame.assetsAll.b_coin).apply { color.a = 0f } }
    private val imgChel     = Image(gdxGame.assetsAll.b_chel)

    override fun addActorsOnGroup() {
        disable()
        addActors(imgListCoin)
        addActor(imgChel)

        imgChel.setBounds(249f, HEIGHT_UI, 250f, 523f)
        var ny = 651f
        imgListCoin.onEach {
            it.setBounds(309f, ny, 243f, 78f)
            ny += (-39f + 78f)
        }
    }

    override fun startAnim() {
        coroutine?.launch {
            imgListCoin.onEach {
                delay(200)
                runGDX { it.animShow(0.2f) }
            }
            delay(150)
            runGDX { imgChel.addAction(Actions.moveTo(249f, 752f, 0.7f, Interpolation.bounceOut)) }
        }
    }

}