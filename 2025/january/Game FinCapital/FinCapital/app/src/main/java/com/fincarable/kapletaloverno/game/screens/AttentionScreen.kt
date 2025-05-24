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

class AttentionScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgAttention = Image(game.all.attention)
    private val btnTryAgain  = AButton(this, AButton.Type.TryAgain)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.background_3.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM) {
            game.soundUtil.apply { play(fail, 0.5f) }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgAttention, btnTryAgain)
        imgAttention.setBounds(147f,827f,583f,449f)
        btnTryAgain.setBounds(154f,623f,569f,128f)

        btnTryAgain.setOnClickListener {
            btnTryAgain.disable()
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(Factory_0_Screen::class.java.name)
            }
        }

    }

}