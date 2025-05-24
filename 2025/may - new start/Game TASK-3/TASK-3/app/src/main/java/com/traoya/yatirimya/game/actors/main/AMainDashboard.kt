package com.traoya.yatirimya.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.traoya.yatirimya.game.actors.button.AButton
import com.traoya.yatirimya.game.screens.DashboardScreen
import com.traoya.yatirimya.game.screens.MenuScreen
import com.traoya.yatirimya.game.screens.TutorialScreen
import com.traoya.yatirimya.game.utils.*
import com.traoya.yatirimya.game.utils.actor.animDelay
import com.traoya.yatirimya.game.utils.actor.animHide
import com.traoya.yatirimya.game.utils.actor.animShow
import com.traoya.yatirimya.game.utils.actor.setOnClickListener
import com.traoya.yatirimya.game.utils.advanced.AdvancedMainGroup
import kotlinx.coroutines.flow.update

class AMainDashboard(override val screen: DashboardScreen): AdvancedMainGroup() {

    private val listDash = listOf(
        gdxGame.assetsAll.T1,
        gdxGame.assetsAll.T2,
    )

    private var currentIndex = 0

    private val btnNext        = AButton(screen, AButton.Type.Def)
    private val imgDashboard   = Image(listDash[currentIndex])

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgDashboard()
        addBtnNext()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgDashboard() {
        addActor(imgDashboard)
        imgDashboard.setBounds(83f, 163f, 875f, 828f)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(110f, 218f, 820f, 154f)
        btnNext.setOnClickListener {
            currentIndex++
            if (currentIndex >= 2) {
                 screen.hideScreen {
                     if (gdxGame.ds_IsTutorial.flow.value) {
                         gdxGame.ds_IsTutorial.update { false }
                         gdxGame.navigationManager.navigate(TutorialScreen::class.java.name)
                     } else {
                         gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                     }
                 }
            } else next()
        }
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
        imgDashboard.drawable = TextureRegionDrawable(listDash[currentIndex])
    }

}