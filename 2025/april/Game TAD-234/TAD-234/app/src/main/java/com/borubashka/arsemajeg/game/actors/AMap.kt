package com.borubashka.arsemajeg.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.borubashka.arsemajeg.game.screens.LevelsScreen
import com.borubashka.arsemajeg.game.screens.QuizScreen
import com.borubashka.arsemajeg.game.utils.GLOBAL_listLevelPos
import com.borubashka.arsemajeg.game.utils.GameColor
import com.borubashka.arsemajeg.game.utils.SizeScaler
import com.borubashka.arsemajeg.game.utils.actor.setBoundsScaled
import com.borubashka.arsemajeg.game.utils.actor.setOnClickListener
import com.borubashka.arsemajeg.game.utils.actor.setOnTouchListener
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedGroup
import com.borubashka.arsemajeg.game.utils.advanced.AdvancedScreen
import com.borubashka.arsemajeg.game.utils.font.FontParameter
import com.borubashka.arsemajeg.game.utils.gdxGame

class AMap(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 3216f)

    private val listDataLevel = gdxGame.ds_ItemData.flow.value

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font59        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(59))

    private val ls59 = Label.LabelStyle(font59, Color.WHITE)

    private val listImgMaps = List(2) { Image(gdxGame.assetsAll.listMap[it]) }
    private val listALevel  = List(12) { ALevel(screen, listDataLevel[it], ls59) }

    override fun addActorsOnGroup() {
        addImgMaps()
        addALevels()
    }

    override fun getPrefWidth(): Float { return width }
    override fun getPrefHeight(): Float { return height }

    // Actors ------------------------------------------------------------

    private fun addImgMaps() {
        var nx = 0f

        listImgMaps.forEachIndexed { index, imgMap ->
            addActor(imgMap)
            imgMap.setBoundsScaled(sizeScaler, nx, 0f, 1608f, 2005f)

            nx += 1605f + 0f
        }
    }

    private fun addALevels() {
        listALevel.forEachIndexed { index, aLevel ->
            val pos = GLOBAL_listLevelPos[index]
            addActor(aLevel)
            aLevel.setBoundsScaled(sizeScaler, pos.x, pos.y, 125f, 125f)

            aLevel.setOnTouchListener(gdxGame.soundUtil) {
                if (listDataLevel[index].isLock) return@setOnTouchListener

                LevelsScreen.SELECTED_LVL_INDEX = index

                screen.hideScreen {
                    gdxGame.navigationManager.navigate(QuizScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }


}