package com.basarili.baslangisc.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.basarili.baslangisc.R
import com.basarili.baslangisc.game.LibGDXGame
import com.basarili.baslangisc.game.actors.ABuild_1
import com.basarili.baslangisc.game.actors.AButton
import com.basarili.baslangisc.game.utils.TIME_ANIM
import com.basarili.baslangisc.game.utils.actor.animHide
import com.basarili.baslangisc.game.utils.actor.animShow
import com.basarili.baslangisc.game.utils.advanced.AdvancedScreen
import com.basarili.baslangisc.game.utils.advanced.AdvancedStage
import com.basarili.baslangisc.game.utils.region
import kotlinx.coroutines.flow.collect

class HelloScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirstShow = true
    }

    private val imgBuilding  = ABuild_1(this)
    private val imgTextHello = Image(game.all.text_hello)
    private val btnHello     = AButton(this, AButton.Type.Hello)

    override fun show() {
        if (isFirstShow) {
            isFirstShow = false

            game.musicUtil.also { mu ->
                mu.music = mu.hard_gaz.apply { isLooping = true }
                mu.coff = 0.025f
            }
        }

        game.activity.setNavBarColor(R.color.ocean)

        stageUI.root.animHide()
        setBackBackground(game.loader.background_loader.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgBuilding()
        addImgTextHello()
        addBtnHello()
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addImgBuilding() {
        addActor(imgBuilding)
        imgBuilding.setBounds(347f, 1372f, 349f, 389f)
    }

    private fun AdvancedStage.addImgTextHello() {
        addActor(imgTextHello)
        imgTextHello.setBounds(41f, 808f, 963f, 374f)
    }

    private fun AdvancedStage.addBtnHello() {
        addActor(btnHello)
        btnHello.setBounds(83f, 463f, 880f, 154f)

        val isTutorial = game.dataStore.isTutorial

        btnHello.setOnClickListener {
            if (isTutorial) {
                game.dataStore.updateIsTutorial { false }
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(TutorialScreen::class.java.name, HelloScreen::class.java.name)
                }
            } else {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(MarketScreen::class.java.name, HelloScreen::class.java.name)
                }
            }
            game.activity.setNavBarColor(R.color.brawn)
        }
    }

}