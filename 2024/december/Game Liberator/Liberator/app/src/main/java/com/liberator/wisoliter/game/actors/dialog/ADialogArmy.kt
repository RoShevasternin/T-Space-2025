package com.liberator.wisoliter.game.actors.dialog

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.liberator.wisoliter.game.actors.progress.AProgressXPLong
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.ITEM_COUNT
import com.liberator.wisoliter.game.utils.MAX_XP
import com.liberator.wisoliter.game.utils.SizeScaler
import com.liberator.wisoliter.game.utils.actor.setBoundsScaled
import com.liberator.wisoliter.game.utils.actor.setOnClickListener
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.font.FontParameter
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.game.utils.runGDX
import com.liberator.wisoliter.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.text.toFloat

class ADialogArmy(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 775f)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font45        = screen.fontGenerator_PusiaBold.generateFont(fontParameter.setSize(45))
    private val font41        = screen.fontGenerator_PusiaBold.generateFont(fontParameter.setSize(41))

    private val ls45 = LabelStyle(font45, GameColor.white)
    private val ls41 = LabelStyle(font41, GameColor.green_44)

    private val imgWhite  = Image(screen.drawerUtil.getTexture(Color.WHITE))
    private val imgTop    = Image(gdxGame.assetsAll.green_panel)
    private val lblTitle  = Label("Ваша армия", ls45)
    private val lblXP     = Label("", ls41)
    private val progress  = AProgressXPLong(screen)
    private val aX        = Actor()
    private val listItem  = List(ITEM_COUNT) { Image(gdxGame.assetsAll.listItem[it]) }
    private val listCount = List(ITEM_COUNT) { Label("", ls41) }

    var blockX = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgWhite)
        imgWhite.y -= (height * 0.1f)
        addImgTop()
        addLblTitle()
        addLblXP()
        addProgressXP()
        addX()
        addListItem()
        addListLblCount()
    }

    // Actors ----------------------------------------------------------------------------

    private fun addImgTop() {
        addActor(imgTop)
        imgTop.setBoundsScaled(sizeScaler, 0f, 647f, 776f, 125f)
    }

    private fun addX() {
        addActor(aX)
        aX.setBoundsScaled(sizeScaler, 693f, 679f, 62f, 62f)
        aX.setOnClickListener(gdxGame.soundUtil) { blockX() }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBoundsScaled(sizeScaler, 248f, 694f, 275f, 31f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addLblXP() {
        addActor(lblXP)
        lblXP.setBoundsScaled(sizeScaler, 238f, 557f, 302f, 49f)
        lblXP.setAlignment(Align.center)

        coroutine?.launch(Dispatchers.IO) {
            gdxGame.ds_XP.flow.collect { xp ->
                runGDX {
                    lblXP.setText("Мощь $xp xp (max $MAX_XP xp)")
                    progress.progressPercentFlow.value = (xp.toFloat() / MAX_XP.toFloat()) * 100f
                }


            }
        }
    }

    private fun addProgressXP() {
        addActor(progress)
        progress.setBoundsScaled(sizeScaler, 35f, 489f, 705f, 41f)
    }

    private fun addListItem() {
        var nx = 41f
        var ny = 365f - 15f
        listItem.onEachIndexed { index, img ->
            addActor(img)
            img.setBoundsScaled(sizeScaler, nx, ny, 149f, 82f)
            ny -= (26 + 82)
            if ((index.inc() % 4) == 0) {
                nx = 403f
                ny = 365f - 15f
            }
        }
    }

    private fun addListLblCount() {
        var nx = 202f
        var ny = 392f - 15f
        listCount.onEachIndexed { index, lbl ->
            addActor(lbl)
            lbl.setBoundsScaled(sizeScaler, nx, ny, 169f, 28f)
            lbl.setAlignment(Align.right)
            ny -= (80 + 28)
            if ((index.inc() % 4) == 0) {
                nx = 525f
                ny = 392f - 15f
            }
        }

        coroutine?.launch(Dispatchers.IO) {
            gdxGame.ds_DataItem.flow.collect { mainList ->
                mainList.onEachIndexed { index, targetList ->
                    runGDX { listCount[index].setText("${targetList.size} штук") }
                }
            }
        }
    }
}