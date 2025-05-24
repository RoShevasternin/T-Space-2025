package com.cryonetpoint.ecsporush.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.cryonetpoint.ecsporush.game.actors.AAnswer
import com.cryonetpoint.ecsporush.game.actors.button.AButton
import com.cryonetpoint.ecsporush.game.screens.DashboardScreen
import com.cryonetpoint.ecsporush.game.screens.MenuScreen
import com.cryonetpoint.ecsporush.game.screens.QuizScreen
import com.cryonetpoint.ecsporush.game.utils.*
import com.cryonetpoint.ecsporush.game.utils.actor.animDelay
import com.cryonetpoint.ecsporush.game.utils.actor.animHide
import com.cryonetpoint.ecsporush.game.utils.actor.animShow
import com.cryonetpoint.ecsporush.game.utils.actor.setOnClickListener
import com.cryonetpoint.ecsporush.game.utils.advanced.AdvancedMainGroup

class AMainDashboard(override val screen: DashboardScreen): AdvancedMainGroup() {

    private var currentIndex = 0

    private val btnNext        = AButton(screen, AButton.Type.Next)
    private val imgDashboard   = Image(gdxGame.assetsAll.listDashboard[currentIndex])
    private val imgLogo        = Image(gdxGame.assetsLoader.logo)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgLogo()
        addBtnNext()
        addImgDashboard()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(276f, 1487f, 515f, 207f)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(45f, 624f, 978f, 161f)
        btnNext.setOnClickListener {
            currentIndex++
            if (currentIndex >= 3) {
                 screen.hideScreen {
                     gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                 }
            } else next()
        }
    }

    private fun addImgDashboard() {
        addActor(imgDashboard)
        imgDashboard.setBounds(35f, 870f, 998f, 505f)
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ----------------------------------------------

    private fun next() {
        imgDashboard.drawable = TextureRegionDrawable(gdxGame.assetsAll.listDashboard[currentIndex])
    }

}