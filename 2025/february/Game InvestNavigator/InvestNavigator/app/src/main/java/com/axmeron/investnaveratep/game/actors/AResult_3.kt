package com.axmeron.investnaveratep.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.axmeron.investnaveratep.game.utils.actor.animShow
import com.axmeron.investnaveratep.game.utils.actor.disable
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.gdxGame

class AResult_3(override val screen: AdvancedScreen): AdvancedGroup(), ResultGroup {

    private val imgChel = Image(gdxGame.assetsAll.c_chel).apply { color.a = 0f }
    private val imgX1   = Image(gdxGame.assetsAll.c_x1).apply { color.a = 0f }
    private val imgX2   = Image(gdxGame.assetsAll.c_x2).apply { color.a = 0f }

    override fun addActorsOnGroup() {
        disable()
        addActors(imgX1, imgX2)
        addActor(imgChel)

        imgChel.setBounds(91f, 570f, 621f, 775f)
        imgX1.setBounds(253f, 1033f, 56f, 56f)
        imgX2.setBounds(623f, 788f, 56f, 56f)

        children.onEach { it.setOrigin(Align.center) }
    }

    override fun startAnim() {
        imgX1.addAction(Actions.forever(Actions.rotateBy(360f, 3f)))
        imgX2.addAction(Actions.forever(Actions.rotateBy(-360f, 3f)))

        imgChel.animShow(0.5f) {
            imgX1.animShow(0.4f) {
                imgX2.animShow(0.3f)
            }
        }
    }

}