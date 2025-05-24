package com.clickandbuild.motors.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.clickandbuild.motors.game.LibGDXGame
import com.clickandbuild.motors.game.actors.AButton
import com.clickandbuild.motors.game.utils.TIME_ANIM
import com.clickandbuild.motors.game.utils.actor.animHide
import com.clickandbuild.motors.game.utils.actor.animShow
import com.clickandbuild.motors.game.utils.advanced.AdvancedScreen
import com.clickandbuild.motors.game.utils.advanced.AdvancedStage
import com.clickandbuild.motors.game.utils.region

class Tutorial_1_Screen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgLogo  = Image(game.all.logo)
    private val imgOrder = Image(game.all.tutorial_order)
    private val imgText  = Image(game.all.text_1)
    private val btnNext  = AButton(this, AButton.Type.Next)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.background_2.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgLogo, imgOrder, imgText, btnNext)
        imgLogo.setBounds(146f,1453f,585f,317f)
        imgOrder.setBounds(147f,811f,583f,449f)
        imgText.setBounds(145f,458f,587f,300f)
        btnNext.setBounds(157f,131f,563f,128f)

        btnNext.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(Tutorial_2_Screen::class.java.name, Tutorial_1_Screen::class.java.name)
            }
        }

    }

}