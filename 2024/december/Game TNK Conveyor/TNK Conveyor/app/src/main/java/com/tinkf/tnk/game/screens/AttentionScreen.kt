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