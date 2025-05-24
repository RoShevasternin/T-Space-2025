package com.cinvestor.crotcevni.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.cinvestor.crotcevni.game.utils.actor.PosSize
import com.cinvestor.crotcevni.game.utils.actor.setBounds
import com.cinvestor.crotcevni.game.utils.actor.setBoundsScaled
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedGroup
import com.cinvestor.crotcevni.game.utils.advanced.AdvancedScreen
import com.cinvestor.crotcevni.game.utils.gdxGame
import com.cinvestor.crotcevni.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ALevel(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val level = gdxGame.ds_Level.flow.value

    private val imgPanel = Image(gdxGame.assetsAll.OFFICE)
    private val imgLvl1  = Image(gdxGame.assetsAll.one)
    private val imgLvl2  = Image(gdxGame.assetsAll.two)
    private val imgLvl3  = Image(gdxGame.assetsAll.tre)

    private val listLevel = listOf(imgLvl1, imgLvl2, imgLvl3).take(level-1)

    private val listPosSize = listOf(
        PosSize(203f, 241f, 149f, 115f),
        PosSize(421f, 241f, 150f, 129f),
        PosSize(462f, 0f, 170f, 81f),
    )

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        listLevel.onEachIndexed { index, lvl ->
            addActor(lvl)
            lvl.setBounds(listPosSize[index])
        }
    }

}