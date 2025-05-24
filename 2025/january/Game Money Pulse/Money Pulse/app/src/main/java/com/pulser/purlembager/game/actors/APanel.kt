package com.pulser.purlembager.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.utils.SizeScaler
import com.pulser.purlembager.game.utils.actor.setBoundsScaled
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame

class APanel(
    override val screen: AdvancedScreen,
    region: TextureRegion,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 1176f)

    private val imgMain = Image(region)

    var blockHome = {}
    var blockAdd  = {}
    var blockBlog = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgMain)
        addBtns()
    }

    // Actors ----------------------------------------------------------------------

    private fun addBtns() {
        val aHome = Actor()
        val aAdd  = Actor()
        val aBlog = Actor()

        addActors(aHome, aAdd, aBlog)
        aHome.apply {
            setBoundsScaled(sizeScaler, 94f, 0f, 191f, 200f)
            setOnClickListener {
                imgMain.drawable = TextureRegionDrawable(gdxGame.assetsAll.panel_home)
                blockHome()
            }
        }
        aAdd.apply {
            setBoundsScaled(sizeScaler, 493f, 0f, 188f, 266f)
            setOnClickListener {
                imgMain.drawable = TextureRegionDrawable(gdxGame.assetsAll.panel_add)
                blockAdd()
            }
        }
        aBlog.apply {
            setBoundsScaled(sizeScaler, 890f, 0f, 191f, 200f)
            setOnClickListener {
                imgMain.drawable = TextureRegionDrawable(gdxGame.assetsAll.panel_blog)
                blockBlog()
            }
        }
    }

}