package com.liberator.wisoliter.game.actors.world

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.liberator.wisoliter.game.utils.HEIGHT_UI
import com.liberator.wisoliter.game.utils.SizeScaler
import com.liberator.wisoliter.game.utils.WORLD_WIDTH
import com.liberator.wisoliter.game.utils.actor.PosSize
import com.liberator.wisoliter.game.utils.actor.setBoundsScaled
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame

class AWorldBackground(override val screen: AdvancedScreen) : AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WORLD_WIDTH)

    private val listImgW = List(9) { Image(gdxGame.assetsAll.listWorldBackground[it]) }

    override fun addActorsOnGroup() {
        addListImgW()
    }

    // Actors -----------------------------------------------------------------------------------

    private fun addListImgW() {
        val listPosSize = listOf(
            PosSize(0f, 1764f, 2162f, 2409f),
            PosSize(2162f, 1764f, 2162f, 2409f),
            PosSize(4324f, 1764f, 2162f, 2409f),
            PosSize(6486f, 1764f, 2162f, 2409f),
            PosSize(8648f, 1764f, 2219f, 2409f),
            PosSize(2469f, 0f, 1698f, 1764f),
            PosSize(4324f, 0f, 2162f, 1764f),
            PosSize(6486f, 0f, 2162f, 1764f),
            PosSize(8648f, 0f, 2119f, 1764f),
        )
        listImgW.onEachIndexed { index, img ->
            addActor(img)
            img.setBoundsScaled(
                sizeScaler,
                listPosSize[index].x,
                listPosSize[index].y,
                listPosSize[index].w,
                listPosSize[index].h
            )
        }
    }

}