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
        imgLogo.setBounds(145f,1476f,587f,272f)
        imgText.setBounds(145f,432f,587f,741f)
        btnToGame.setBounds(157f,131f,563f,128f)

        btnToGame.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(Factory_0_Screen::class.java.name, Tutorial_3_Screen::class.java.name)
            }
        }

    }

}