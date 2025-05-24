package com.borubashka.arsemajeg.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.borubashka.arsemajeg.game.actors.progress.AProgressMini
import com.borubashka.arsemajeg.game.screens.LevelsScreen
import com.borubashka.arsemajeg.game.utils.Block
import com.borubashka.arsemajeg.game.utils.GameColor
import com.borubashka.arsemajeg.game.utils.TIME_ANIM_SCREEN
import com.borubashka.arsemajeg.game.utils.actor.animDelay
import com.borubashka.arsemajeg.game.utils.actor.animHide
import com.borubashka.arsemajeg.game.utils.actor.animShow
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedMainGroup
import com.borubashka.arsemajeg.game.utils.font.FontParameter
import com.borubashka.arsemajeg.game.utils.gdxGame
import com.borubashka.arsemajeg.util.log

class AMainLevels(override val screen: LevelsScreen): AdvancedMainGroup() {

//    private val listDataItem = gdxGame.ds_ItemData.flow.value.reversed()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font34        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(34))
    private val font49        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(49))

    private val ls34 = Label.LabelStyle(font34, GameColor.black)
    private val ls49 = Label.LabelStyle(font49, GameColor.red)

    private val valueScore = gdxGame.ds_Score.flow.value

    private val progress = AProgressMini(screen)
    private val lblScore = Label("Очки", ls34)
    private val lblValue = Label("$valueScore", ls49)

    override fun addActorsOnGroup() {
        screen.stageBack.root.color.a = 0f
        color.a = 0f

        addLbls()

        addProgress()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addProgress() {
        val imgBack = Image(gdxGame.assetsAll.white_back_mini)
        addActor(imgBack)
        imgBack.setBounds(333f, 1859f, 263f, 26f)

        addActor(progress)
        progress.setBounds(335f, 1862f, 257f, 20f)

        val lvlOpenedCount = gdxGame.ds_ItemData.flow.value.count { it.isLock.not() }
        progress.progressPercentFlow.value = (lvlOpenedCount.toFloat() / 12f) * 100f
    }

    private fun addLbls() {
        addActors(lblScore, lblValue)
        lblScore.setBounds(385f, 1796f, 85f, 50f)
        lblValue.setBounds(482f, 1781f, 58f, 80f)
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}