package com.vectorvesta.bronfinteh.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.vectorvesta.bronfinteh.game.utils.actor.setOnClickListener
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGroup
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedScreen
import com.vectorvesta.bronfinteh.game.utils.gdxGame

class APanel(
    override val screen: AdvancedScreen,
    val texture: TextureRegion
): AdvancedGroup() {

   private val imgPanel = Image(texture)

    var blockMain = {}
    var blockHist = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        val aMain = Actor()
        val aHist = Actor()
        addActors(aMain, aHist)
        aMain.apply {
            setBounds(7f, 7f, 204f, 81f)
            setOnClickListener(gdxGame.soundUtil) {
                imgPanel.drawable = TextureRegionDrawable(gdxGame.assetsAll.main)
                blockMain()
            }
        }
        aHist.apply {
            setBounds(257f, 7f, 204f, 81f)
            setOnClickListener(gdxGame.soundUtil) {
                imgPanel.drawable = TextureRegionDrawable(gdxGame.assetsAll.hist)
                blockHist()
            }
        }
    }

}