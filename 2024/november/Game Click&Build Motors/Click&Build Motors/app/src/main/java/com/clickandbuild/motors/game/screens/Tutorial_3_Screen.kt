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

class Tutorial_3_Screen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgLogo   = Image(game.all.logo)
    private val imgText   = Image(game.all.text_3)
    private val btnToGame = AButton(this, AButton.Type.ToGame)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.background_2.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgLogo, imgText, btnToGame)
        imgLogo.setBounds(146f,1453f,585f,317f)
        imgText.setBounds(145f,536f,587f,650f)
        btnToGame.setBounds(157f,131f,563f,128f)

        btnToGame.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(Factory_0_Screen::class.java.name, Tutorial_3_Screen::class.java.name)
            }
        }

    }

}