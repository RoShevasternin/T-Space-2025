package com.kaskazer.kazmuchero.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.kaskazer.kazmuchero.R
import com.kaskazer.kazmuchero.game.LibGDXGame
import com.kaskazer.kazmuchero.game.actors.ABuild_1
import com.kaskazer.kazmuchero.game.actors.AButton
import com.kaskazer.kazmuchero.game.utils.TIME_ANIM
import com.kaskazer.kazmuchero.game.utils.actor.animHide
import com.kaskazer.kazmuchero.game.utils.actor.animShow
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedScreen
import com.kaskazer.kazmuchero.game.utils.advanced.AdvancedStage
import com.kaskazer.kazmuchero.game.utils.region

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
        imgTextHello.setBounds(41f, 703f, 963f, 479f)
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