package com.proccaptald.proffesionalestic.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.proccaptald.proffesionalestic.game.LibGDXGame
import com.proccaptald.proffesionalestic.game.actors.AButton
import com.proccaptald.proffesionalestic.game.utils.TIME_ANIM
import com.proccaptald.proffesionalestic.game.utils.actor.animHide
import com.proccaptald.proffesionalestic.game.utils.actor.animShow
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedStage
import com.proccaptald.proffesionalestic.game.utils.region

class PPScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgPanel = Image(game.all.PANEL)
    private val btnBack  = AButton(this, AButton.Static.Type.Back)
    private val imgTitle = Image(game.all.pp)
    private val imgText  = Image(game.all.PP_TEXT).apply { setSize(474f,1974f) }
    private val scroll   = ScrollPane(imgText)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgPanel()
        addImgTitle()
        addImgText()
        addBtns()
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(50f,54f,558f,1134f)
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(70f,1220f,517f,104f)
    }

    private fun AdvancedStage.addImgText() {
        addActor(scroll)
        scroll.setBounds(92f,121f,474f,1012f)
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnBack)

        btnBack.apply {
            setBounds(34f,1276f,70f,76f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

}