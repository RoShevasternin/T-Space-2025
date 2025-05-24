package com.funkun.kylkan.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.funkun.kylkan.game.utils.actor.setBounds
import com.funkun.kylkan.game.utils.actor.setOnClickListener
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.gdxGame

class APanel(
    override val screen: AdvancedScreen,
    startTexture: TextureRegion
): AdvancedGroup() {

    private val imgPanel = Image(startTexture)

    var blockMenu    = {}
    var blockPlus    = {}
    var blockHistory = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        val aMenu    = Actor()
        val aPlus    = Actor()
        val aHistory = Actor()
        addActors(aMenu, aPlus, aHistory)

        aMenu.setBounds(65f, 62f, 350f, 153f)
        aPlus.setBounds(416f, 71f, 135f, 135f)
        aHistory.setBounds(551f, 62f, 350f, 153f)

        aMenu.setOnClickListener(gdxGame.soundUtil) {
            blockMenu()
            imgPanel.drawable = TextureRegionDrawable(gdxGame.assetsAll.m_home)
        }
        aPlus.setOnClickListener(gdxGame.soundUtil) {
            blockPlus()
        }
        aHistory.setOnClickListener(gdxGame.soundUtil) {
            blockHistory()
            imgPanel.drawable = TextureRegionDrawable(gdxGame.assetsAll.m_hystory)
        }
    }

}