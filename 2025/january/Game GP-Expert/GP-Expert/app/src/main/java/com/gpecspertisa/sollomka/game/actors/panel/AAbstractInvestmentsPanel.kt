package com.gpecspertisa.sollomka.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gpecspertisa.sollomka.game.screens.AbstractWorkScreen
import com.gpecspertisa.sollomka.game.utils.Block
import com.gpecspertisa.sollomka.game.utils.actor.setOnClickListener
import com.gpecspertisa.sollomka.game.utils.advanced.AdvancedGroup

abstract class AAbstractInvestmentsPanel: AdvancedGroup() {

    abstract val screenType     : AbstractWorkScreen.ScreenType
    abstract val listItemSum    : List<Int>
    abstract val listItemResult : List<Int>
    abstract val listItemSeconds: List<Int>

    private val imgInvestments by lazy { Image(screen.game.all.listInvestPanel[screenType.ordinal]) }

    private val aBack  = Actor()
    private val aItem1 = Actor()
    private val aItem2 = Actor()
    private val aItem3 = Actor()

    private val listBtnItem = listOf(aItem1,aItem2,aItem3)

    var blockBack = Block {}

    var blockItem: (Int, Int, Int, Int) -> Unit = { _,_,_,_ -> }

    override fun addActorsOnGroup() {
        addImgImprovements()
        addBtnBack()
        addBtnItems()
    }

    private fun addImgImprovements() {
        addActor(imgInvestments)
        imgInvestments.setBounds(43f, 1077f, 1028f, 892f)
    }

    private fun addBtnBack() {
        addActor(aBack)
        aBack.apply {
            setBounds(44f, 1827f, 133f, 133f)
            setOnClickListener(screen.game.soundUtil) {
                blockBack.invoke()
            }
        }
    }

    private fun addBtnItems() {
        var ny = 1552f

        listBtnItem.onEachIndexed { index, btn ->
            addActor(btn)
            btn.apply {
                setBounds(815f, ny, 255f, 148f)
                ny -= 89 + 148

                setOnClickListener(screen.game.soundUtil) {
                    blockItem(index, listItemSum[index], listItemResult[index], listItemSeconds[index])
                }
            }
        }
    }

}