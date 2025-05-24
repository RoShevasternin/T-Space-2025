package com.liberator.wisoliter.game.actors.game

import com.liberator.wisoliter.game.actors.button.AButton
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame

class ACreate(override val screen: AdvancedScreen): AdvancedGroup() {

    private val btnCreate = AButton(screen, AButton.Type.Create)
    private val btnMagaz  = AButton(screen, AButton.Type.Magaz)
    private val btnArmy   = AButton(screen, AButton.Type.Army)
    private val aCoinPlus = ACoinPlus(screen)

    var blockArmy  = {}
    var blockMagaz = {}

    override fun addActorsOnGroup() {
        addBtnCreate()
        addBtnMagaz()
        addBtnArmy()
        addCoinPlus()
    }

    // Actors ------------------------------------------------------------------------

    //private var clickCounter = 0

    private fun addBtnCreate() {
        addActor(btnCreate)
        btnCreate.setBounds(5f, 219f, 498f, 113f)
        btnCreate.setOnClickListener(gdxGame.soundUtil.create) {
            gdxGame.ds_Balance.update { it + 10 }
            aCoinPlus.animPlus()

//            clickCounter++
//            val link = gdxGame.sharedPreferences.getString("simerik", null)
//            if (clickCounter == 5) link?.let {
//                gdxGame.activity.showUrl(link)
//
//                screen.pause()
//            }
        }
    }

    private fun addBtnMagaz() {
        addActor(btnMagaz)
        btnMagaz.setBounds(0f, 0f, 181f, 173f)
        btnMagaz.setOnClickListener { blockMagaz() }
    }

    private fun addBtnArmy() {
        addActor(btnArmy)
        btnArmy.setBounds(321f, 0f, 181f, 173f)
        btnArmy.setOnClickListener { blockArmy() }
    }

    private fun addCoinPlus() {
        addActor(aCoinPlus)
        aCoinPlus.setBounds(-121f, 268f, 744f, 192f)
    }

}