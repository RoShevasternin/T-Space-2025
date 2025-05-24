package com.fincarable.kapletaloverno.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fincarable.kapletaloverno.game.LibGDXGame
import com.fincarable.kapletaloverno.game.actors.AButton
import com.fincarable.kapletaloverno.game.utils.TIME_ANIM
import com.fincarable.kapletaloverno.game.utils.actor.animHide
import com.fincarable.kapletaloverno.game.utils.actor.animShow
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedScreen
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedStage
import com.fincarable.kapletaloverno.game.utils.region

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
        imgLogo.setBounds(145f,1476f,587f,272f)
        imgOrder.setBounds(147f,811f,583f,449f)
        imgText.setBounds(41f,339f,795f,361f)
        btnNext.setBounds(157f,131f,563f,128f)

        btnNext.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(Tutorial_2_Screen::class.java.name, Tutorial_1_Screen::class.java.name)
            }
        }

    }

}