package com.tinkf.tnk.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tinkf.tnk.game.LibGDXGame
import com.tinkf.tnk.game.actors.AButton
import com.tinkf.tnk.game.utils.TIME_ANIM
import com.tinkf.tnk.game.utils.actor.animHide
import com.tinkf.tnk.game.utils.actor.animShow
import com.tinkf.tnk.game.utils.advanced.AdvancedScreen
import com.tinkf.tnk.game.utils.advanced.AdvancedStage
import com.tinkf.tnk.game.utils.region

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
        imgLogo.setBounds(79f,1436f,719f,351f)
        imgText.setBounds(145f,432f,587f,741f)
        btnToGame.setBounds(157f,131f,563f,128f)

        btnToGame.setOnClickListener {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(Factory_0_Screen::class.java.name, Tutorial_3_Screen::class.java.name)
            }
        }

    }

}