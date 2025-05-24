package com.progressmas.samsuster.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.progressmas.samsuster.game.screens.AbstractWorkScreen
import com.progressmas.samsuster.game.utils.Block
import com.progressmas.samsuster.game.utils.actor.setOnClickListener
import com.progressmas.samsuster.game.utils.advanced.AdvancedGroup

abstract class AAbstractImprovementsPanel: AdvancedGroup() {

    abstract val screenType : AbstractWorkScreen.ScreenType
    abstract val listItemSum: List<Int>

    private val imgImprovements by lazy { Image(screen.game.all.listImprovePanel[screenType.ordinal]) }

    private val aBack  = Actor()
    private val aItem1 = Actor()
    private val aItem2 = Actor()
    private val aItem3 = Actor()
    private val aItem4 = Actor()

    private val listBtnItem = listOf(aItem1,aItem2,aItem3,aItem4)

    var blockBack = Block {}

    var blockItem: (Int, Float) -> Unit = { _,_ -> }

    override fun addActorsOnGroup() {
        addImgImprovements()
        addBtnBack()
        addBtnItems()
    }

    private fun addImgImprovements() {
        addActor(imgImprovements)
        imgImprovements.setBounds(43f, 839f, 1028f, 1130f)
    }

    private fun addBtnBack() {
        addActor(aBack)
        aBack.apply {
            setBounds(44f, 1835f, 133f, 133f)
            setOnClickListener(screen.game.soundUtil) {
                blockBack.invoke()
            }
        }
    }

    private fun addBtnItems() {
        val listItem = listOf(3f, 10f, 20f, 50f)

        var ny = 1567f

        listBtnItem.onEachIndexed { index, btn ->
            addActor(btn)
            btn.apply {
                setBounds(855f, ny, 215f, 148f)
                ny -= 89 + 148

                setOnClickListener(null) {
                    blockItem(listItemSum[index], listItem[index])
                }
            }
        }
    }

}