package com.liberator.wisoliter.game.actors.tutorial

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.liberator.wisoliter.game.actors.button.AButton
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame

class ATutorial_1(override val screen: AdvancedScreen): AdvancedGroup() {

    private val btnCreate = AButton(screen, AButton.Type.Create)
    private val listPlus  = List(5) { Image(gdxGame.assetsAll.plus10) }
    private val btnMagaz  = AButton(screen, AButton.Type.Magaz)
    private val btnArmy   = AButton(screen, AButton.Type.Army)

    override fun addActorsOnGroup() {
        addBtnCreate()
        addBtnMagaz()
        addBtnArmy()
        addListPlus()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnCreate() {
        addActor(btnCreate)
        btnCreate.setBounds(143f, 1126f, 498f, 113f)
    }

    private fun addListPlus() {
        val listXY = listOf(
            Vector2(17f, 1177f),
            Vector2(141f, 1284f),
            Vector2(340f, 1324f),
            Vector2(526f, 1284f),
            Vector2(656f, 1176f),
        )
        listPlus.onEachIndexed { index, img ->
            addActor(img)
            img.setBounds(listXY[index].x, listXY[index].y, 103f, 41f)
        }
    }

    private fun addBtnMagaz() {
        addActor(btnMagaz)
        btnMagaz.setBounds(138f, 906f, 181f, 173f)
    }

    private fun addBtnArmy() {
        addActor(btnArmy)
        btnArmy.setBounds(459f, 906f, 181f, 173f)
    }

}