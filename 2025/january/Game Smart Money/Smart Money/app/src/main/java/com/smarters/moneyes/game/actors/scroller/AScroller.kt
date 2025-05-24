package com.smarters.moneyes.game.actors.scroller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.smarters.moneyes.game.actors.AScrollPane
import com.smarters.moneyes.game.actors.autoLayout.AHorizontalGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.gdxGame
import com.smarters.moneyes.util.log

class AScroller(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val horizontalGroup = AHorizontalGroup(screen, 70f, paddingLeft = 61f, paddingRight = 61f, isWrapHorizontal = true)
    private val scroll          = AScrollPane(horizontalGroup)
    private val aPoints         = APoints(screen)
    private val listImgData     = listOf(
        Image(gdxGame.assetsAll.d1),
        Image(gdxGame.assetsAll.d2),
        Image(gdxGame.assetsAll.d3),
    )

    // Field
    //private val listPercentX = listOf(0f, 0.5f, 1f)

    override fun addActorsOnGroup() {
        addScroll()
        addPoints()
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 54f, 1150f, 654f)
        horizontalGroup.addListData()
    }

    private fun AHorizontalGroup.addListData() {
        listImgData.onEach { img ->
            img.setSize(1028f, 654f)
            addActor(img)
        }
    }

    private fun addPoints() {
        addActor(aPoints)
        aPoints.setBounds(506f, 0f, 138f, 25f)

//        aPoints.blockCheck = { indexBox ->
//            log("aaa $indexBox")
//            scroll.scrollPercentX = listPercentX[indexBox]
//        }
    }

    private var time = 0f
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        time += Gdx.graphics.deltaTime
        if (time >= 0.075) {
            time = 0f

            scroll.scrollPercentX.also { percentX ->
                when (percentX) {
                    in 0f..0.40f    -> 0
                    in 0.40f..0.94f -> 1
                    in 0.95f..1f    -> 2
                    else                   -> 0
                }.also { indexPoint ->
                    aPoints.check(indexPoint) }
            }
        }
    }
}