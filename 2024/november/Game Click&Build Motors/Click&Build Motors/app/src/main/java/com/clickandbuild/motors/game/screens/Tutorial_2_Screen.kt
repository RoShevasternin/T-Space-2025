package com.clickandbuild.motors.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.clickandbuild.motors.game.LibGDXGame
import com.clickandbuild.motors.game.actors.AButton
import com.clickandbuild.motors.game.actors.AChooseColor_BWB
import com.clickandbuild.motors.game.utils.TIME_ANIM
import com.clickandbuild.motors.game.utils.actor.animHide
import com.clickandbuild.motors.game.utils.actor.animShow
import com.clickandbuild.motors.game.utils.advanced.AdvancedScreen
import com.clickandbuild.motors.game.utils.advanced.AdvancedStage
import com.clickandbuild.motors.game.utils.region

class Tutorial_2_Screen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgLogo  = Image(game.all.logo)
    private val imgOrder = AChooseColor_BWB(this)
    private val imgText  = Image(game.all.text_2)
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
        imgOrder.setBounds(147f,899f,583f,352f)
        imgText.setBounds(145f,468f,587f,378f)
        btnNext.setBounds(157f,131f,563f,128f)

        btnNext.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(Tutorial_3_Screen::class.java.name, Tutorial_2_Screen::class.java.name)
            }
        }

    }

}