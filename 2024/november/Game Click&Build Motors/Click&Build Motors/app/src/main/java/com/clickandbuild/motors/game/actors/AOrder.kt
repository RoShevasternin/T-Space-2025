package com.clickandbuild.motors.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.clickandbuild.motors.game.screens.Factory_0_Screen
import com.clickandbuild.motors.game.utils.advanced.AdvancedGroup
import com.clickandbuild.motors.game.utils.advanced.AdvancedScreen

class AOrder(
    override val screen: AdvancedScreen,
    interiorIndex: Int,
    bodyIndex    : Int,
) : AdvancedGroup() {

    private val img         = Image(screen.game.all.order)
    private val imgInterior = Image(screen.game.all.listColorBWB[interiorIndex])
    private val imgBody     = Image(screen.game.all.listColorRBG[bodyIndex])

    override fun addActorsOnGroup() {
        addAndFillActor(img)
        addActors(imgInterior, imgBody)
        imgInterior.setBounds(152f, 187f, 80f, 111f)
        imgBody.setBounds(152f, 35f, 85f, 83f)
    }

    fun update(interiorIndex: Int, bodyIndex    : Int) {
        imgInterior.drawable = TextureRegionDrawable(screen.game.all.listColorBWB[interiorIndex])
        imgBody.drawable     = TextureRegionDrawable(screen.game.all.listColorRBG[bodyIndex])
    }

}