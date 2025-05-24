package com.easyru.track.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.easyru.track.game.utils.WIDTH_UI
import com.easyru.track.game.utils.actor.animShow
import com.easyru.track.game.utils.actor.disable
import com.easyru.track.game.utils.advanced.AdvancedGroup
import com.easyru.track.game.utils.advanced.AdvancedScreen
import com.easyru.track.game.utils.gdxGame

class AResult_1(override val screen: AdvancedScreen): AdvancedGroup(), ResultGroup {

    private val imgTel  = Image(gdxGame.assetsAll.a_tel)
    private val imgCoin = Image(gdxGame.assetsAll.a_coin)
    private val imgChel = Image(gdxGame.assetsAll.a_chel)

    override fun addActorsOnGroup() {
        disable()
        addActors(imgTel, imgCoin, imgChel)
        imgTel.setBounds(WIDTH_UI, 700f, 328f, 598f)
        imgCoin.setBounds(374f, 902f, 175f, 193f)
        imgCoin.color.a = 0f
        imgChel.setBounds(-400f, 500f, 364f, 449f)
    }

    override fun startAnim() {
        imgTel.addAction(Actions.moveTo(365f, 677f, 0.5f, Interpolation.swingOut))
        imgCoin.animShow(1f)
        imgChel.addAction(Actions.moveTo(109f, 673f, 0.5f, Interpolation.swingOut))
    }

}

interface ResultGroup {
    fun startAnim()
}