package com.proccaptald.proffesionalestic.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.proccaptald.proffesionalestic.game.LibGDXGame
import com.proccaptald.proffesionalestic.game.actors.AButton
import com.proccaptald.proffesionalestic.game.actors.checkbox.ACheckBox
import com.proccaptald.proffesionalestic.game.actors.layout.AVerticalGroup
import com.proccaptald.proffesionalestic.game.utils.TIME_ANIM
import com.proccaptald.proffesionalestic.game.utils.actor.animHide
import com.proccaptald.proffesionalestic.game.utils.actor.animShow
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedGroup
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedStage
import com.proccaptald.proffesionalestic.game.utils.region

class MainScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true
    }

    private val imgSuitcase = Image(game.all.suitcase)

    private val boxSound = ACheckBox(this, ACheckBox.Static.Type.SOUND)
    private val btnInfo  = AButton(this, AButton.Static.Type.Info)
    private val btnPlay  = AButton(this, AButton.Static.Type.Play)
    private val btnRules = AButton(this, AButton.Static.Type.Rules)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = coins.apply {
                isLooping = true
                volumeLevelFlow.value = 15f
            } }
        }

        stageUI.root.animHide()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgSuitcase()
        addBoxSound()
        addBtns()
    }

    private fun AdvancedStage.addImgSuitcase() {
        addActor(imgSuitcase)
        imgSuitcase.setBounds(158f,629f,332f,395f)
    }

    private fun AdvancedStage.addBoxSound() {
        addActor(boxSound)
        boxSound.setBounds(52f,1157f,126f,132f)
        if (game.musicUtil.music?.isPlaying?.not() == true) boxSound.check(false)

        boxSound.setOnCheckListener { isCheck ->
            if (isCheck) {
                game.musicUtil.music?.pause()
            } else {
                game.musicUtil.music?.play()
            }
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnInfo, btnPlay, btnRules)
        btnInfo.apply {
            setBounds(479f,1157f,126f,132f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(PPScreen::class.java.name, MainScreen::class.java.name)
                }
            }
        }
        btnPlay.apply {
            setBounds(159f,419f,339f,97f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SelectTargetAndPlayersScreen::class.java.name, MainScreen::class.java.name)
                }
            }
        }
        btnRules.apply {
            setBounds(159f,276f,339f,97f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(RulesScreen::class.java.name, MainScreen::class.java.name)
                }
            }
        }
    }

}