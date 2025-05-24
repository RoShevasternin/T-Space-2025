package com.sberigatelny.finexpertaizer.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.sberigatelny.finexpertaizer.game.utils.Acts
import com.sberigatelny.finexpertaizer.game.utils.TIME_ANIM_SCREEN
import com.sberigatelny.finexpertaizer.game.utils.actor.animHide
import com.sberigatelny.finexpertaizer.game.utils.actor.animShow
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedGroup
import com.sberigatelny.finexpertaizer.game.utils.advanced.AdvancedScreen
import com.sberigatelny.finexpertaizer.game.utils.gdxGame

class APers(
    override val screen: AdvancedScreen,
    indexChel: Int = 0
): AdvancedGroup() {

    private val imgMain = Image(gdxGame.assetsAll.listResult[indexChel])

    override fun addActorsOnGroup() {
        addAndFillActor(imgMain)
    }

    fun updateChel(index: Int) {
        imgMain.drawable = TextureRegionDrawable(gdxGame.assetsAll.listResult[index])
    }

    fun startAnim() {
        this.addAction(Acts.forever(
            Acts.sequence(
                Acts.delay(5f),
                Acts.run {
                    imgMain.animHide(TIME_ANIM_SCREEN) {
                        imgMain.drawable = TextureRegionDrawable(gdxGame.assetsAll.listResult.random())
                        imgMain.animShow(TIME_ANIM_SCREEN)
                    }
                }
            )
        ))
    }

}