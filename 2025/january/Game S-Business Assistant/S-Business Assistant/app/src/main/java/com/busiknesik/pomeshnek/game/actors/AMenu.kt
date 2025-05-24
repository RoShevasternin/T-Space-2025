package com.busiknesik.pomeshnek.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.busiknesik.pomeshnek.game.utils.actor.setOnClickListener
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedGroup
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.gdxGame

class AMenu(
    override val screen: AdvancedScreen,
    val region: TextureRegion,
): AdvancedGroup() {

    private val imgMenu = Image(region)

    var blockHome    = {}
    var blockHistory = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgMenu)

        val aHome    = Actor()
        val aHistory = Actor()
        addActors(aHome, aHistory)
        aHome.apply {
            setBounds(196f, 26f, 199f, 199f)
            setOnClickListener(gdxGame.soundUtil) {
                imgMenu.drawable = TextureRegionDrawable(gdxGame.assetsAll.home)
                blockHome()
            }
        }
        aHistory.apply {
            setBounds(649f, 26f, 199f, 199f)
            setOnClickListener(gdxGame.soundUtil) {
                imgMenu.drawable = TextureRegionDrawable(gdxGame.assetsAll.histore)
                blockHistory()
            }
        }
    }

}