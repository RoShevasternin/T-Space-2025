package com.tinkf.tnk.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tinkf.tnk.game.LibGDXGame
import com.tinkf.tnk.game.actors.AButton
import com.tinkf.tnk.game.actors.AChooseColor_BWB
import com.tinkf.tnk.game.utils.TIME_ANIM
import com.tinkf.tnk.game.utils.actor.animHide
import com.tinkf.tnk.game.utils.actor.animShow
import com.tinkf.tnk.game.utils.advanced.AdvancedScreen
import com.tinkf.tnk.game.utils.advanced.AdvancedStage
import com.tinkf.tnk.game.utils.region

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
        imgLogo.setBounds(79f,1436f,719f,351f)
        imgOrder.setBounds(147f,899f,583f,352f)
        imgText.setBounds(78f,334f,721f,456f)
        btnNext.setBounds(157f,131f,563f,128f)

        btnNext.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(Tutorial_3_Screen::class.java.name, Tutorial_2_Screen::class.java.name)
            }
        }

    }

}