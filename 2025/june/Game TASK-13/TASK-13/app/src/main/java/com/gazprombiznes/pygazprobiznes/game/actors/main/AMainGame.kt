package com.gazprombiznes.pygazprobiznes.game.actors.main

import androidx.core.graphics.createBitmap
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.gazprombiznes.pygazprobiznes.game.actors.ABalance
import com.gazprombiznes.pygazprobiznes.game.actors.progress.AProgressBig
import com.gazprombiznes.pygazprobiznes.game.screens.GameScreen
import com.gazprombiznes.pygazprobiznes.game.utils.*
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animDelay
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animHide
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animShow
import com.gazprombiznes.pygazprobiznes.game.utils.actor.disable
import com.gazprombiznes.pygazprobiznes.game.utils.actor.setOnClickListener
import com.gazprombiznes.pygazprobiznes.game.utils.actor.setOnClickListenerWithBlock
import com.gazprombiznes.pygazprobiznes.game.utils.actor.setPosition
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedMainGroup

class AMainGame(override val screen: GameScreen): AdvancedMainGroup() {

    private val level    = gdxGame.ds_Level.flow.value
    private val indexBur = if (level > 7) 6 else level.dec()

    private val aBalance = ABalance(screen)
    private val imgBud   = Image(gdxGame.assetsAll.listItemeser[indexBur])

    private val progress = AProgressBig(screen)

    private val listImg = List(50) { Image(gdxGame.assetsAll.oil) }

    private var currentOilIndex = 0

    override fun addActorsOnGroup() {
        color.a = 0f

        addABalance()
        addImgBurel()
        addProgress()
        addOilList()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addABalance() {
        addActor(aBalance)
        aBalance.setBounds(48f, 1184f, 511f, 71f)
    }

    private fun addOilList() {
        listImg.forEach {
            addActor(it)
            it.setBounds(WIDTH_UI, HEIGHT_UI, 163f, 108f)
            it.disable()
        }
    }

    private fun addImgBurel() {
        addAndFillActor(imgBud)

        var clickCount   = 0
        var currentLevel = level

        imgBud.setOnClickListenerWithBlock(
            gdxGame.soundUtil,
            touchDownBlock = { x, y ->
                clickCount++
                gdxGame.ds_Gaz.update { it + 1 }

                val newLevelCount = (currentLevel * 10)

                progress.progressPercentFlow.value += 100f / newLevelCount

                if (currentOilIndex >= 49) currentOilIndex = 0 else currentOilIndex++

                listImg[currentOilIndex].apply {
                    setPosition(x - 80, y - 54)
                    animDelay(0.5f) {
                        setPosition(WIDTH_UI, HEIGHT_UI)
                    }
                }

                if (clickCount >= newLevelCount) {
                    if (level < 7) {
                        gdxGame.ds_Level.update { it + 1 }
                        currentLevel++
                        progress.progressPercentFlow.value = 0f
                        clickCount = 0

                        val indexBur2 = if (currentLevel > 7) 6 else currentLevel.dec()
                        imgBud.drawable = TextureRegionDrawable(gdxGame.assetsAll.listItemeser[indexBur2])
                    }
                }
            }
        )
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(48f, 77f, 512f, 34f)
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}