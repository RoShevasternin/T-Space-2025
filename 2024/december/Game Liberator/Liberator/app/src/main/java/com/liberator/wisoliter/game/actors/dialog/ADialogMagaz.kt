package com.liberator.wisoliter.game.actors.dialog

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.liberator.wisoliter.game.actors.AScrollPane
import com.liberator.wisoliter.game.actors.ATmpGroup
import com.liberator.wisoliter.game.actors.button.AButton
import com.liberator.wisoliter.game.dataStore.DataItem
import com.liberator.wisoliter.game.dataStore.DataItemType
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.ITEM_COUNT
import com.liberator.wisoliter.game.utils.MAX_ITEM_COUNT
import com.liberator.wisoliter.game.utils.SizeScaler
import com.liberator.wisoliter.game.utils.actor.setBoundsScaled
import com.liberator.wisoliter.game.utils.actor.setOnClickListener
import com.liberator.wisoliter.game.utils.actor.setSizeScaled
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.font.FontParameter
import com.liberator.wisoliter.game.utils.gdxGame

class ADialogMagaz(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 775f)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font45        = screen.fontGenerator_PusiaBold.generateFont(fontParameter.setSize(45))
    private val font41        = screen.fontGenerator_PusiaBold.generateFont(fontParameter.setSize(41))

    private val ls45 = LabelStyle(font45, GameColor.white)
    private val ls41 = LabelStyle(font41, GameColor.green_44)

    private val imgWhite  = Image(screen.drawerUtil.getTexture(Color.WHITE))
    private val imgTop    = Image(gdxGame.assetsAll.green_panel)
    private val lblTitle  = Label("Магазин", ls45)
    private val aX        = Actor()

    private val tmpGroup  = ATmpGroup(screen)
    private val scroll    = AScrollPane(tmpGroup)
    private val listItem  = List(ITEM_COUNT) { Image(gdxGame.assetsAll.listItem[it]) }
    private val listXP    = List(ITEM_COUNT) { Label("+${gdxGame.listItemXP[it]} xp", ls41) }
    private val listSTO   = List(ITEM_COUNT) { AButton(screen, AButton.Type.STO) }

    var blockX = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgWhite)
        imgWhite.y -= (height * 0.05f)
        addImgTop()
        addLblTitle()
        addX()

        addScroll()
    }

    // Actors ----------------------------------------------------------------------------

    private fun addImgTop() {
        addActor(imgTop)
        imgTop.setBoundsScaled(sizeScaler, 0f, 1034f, 776f, 125f)
    }

    private fun addX() {
        addActor(aX)
        aX.setBoundsScaled(sizeScaler, 693f, 1065f, 62f, 62f)
        aX.setOnClickListener(gdxGame.soundUtil) { blockX() }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBoundsScaled(sizeScaler, 292f, 1081f, 190f, 31f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBoundsScaled(sizeScaler, 0f, 0f, 775f, 1034f)

        tmpGroup.setSizeScaled(sizeScaler, 775f, 1595f)
        tmpGroup.addListItem()
        tmpGroup.addListXP()
        tmpGroup.addListSTO()

    }

    // Actors TmpGroup -----------------------------------------------

    private fun AdvancedGroup.addListItem() {
        var ny = 1389f
        listItem.onEachIndexed { index, img ->
            addActor(img)
            img.setBoundsScaled(this@ADialogMagaz.sizeScaler, 41f, ny, 300f, 165f)
            ny -= (52 + 165)
        }
    }

    private fun AdvancedGroup.addListXP() {
        var ny = 1457f
        listXP.onEachIndexed { index, lbl ->
            addActor(lbl)
            lbl.setBoundsScaled(this@ADialogMagaz.sizeScaler, 370f, ny, 130f, 29f)
            lbl.setAlignment(Align.right)
            ny -= (189 + 29)
        }
    }

    private fun AdvancedGroup.addListSTO() {
        var ny = 1430f
        listSTO.onEachIndexed { index, btn ->
            addActor(btn)
            btn.setBoundsScaled(this@ADialogMagaz.sizeScaler, 576f, ny, 157f, 82f)
            ny -= (136 + 82)

            btn.setOnClickListener {
                val balance    = gdxGame.ds_Balance.flow.value
                val targetList = gdxGame.ds_DataItem.flow.value[index]

                if (targetList.size < MAX_ITEM_COUNT && balance >= 100) {
                    gdxGame.soundUtil.apply { play(buy) }

                    gdxGame.ds_Balance.update { it - 100 }
                    gdxGame.ds_DataItem.update { mainList -> mainList.toMutableList().also {
                        it[index] = it[index].toMutableList().also { targetList ->
                            targetList.add(DataItem(DataItemType.entries[index]))
                        }
                    } }
                    gdxGame.ds_XP.update { it + gdxGame.listItemXP[index]}
                } else {
                    gdxGame.soundUtil.apply { play(fail) }
                }
            }
        }
    }

}