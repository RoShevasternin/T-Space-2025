package com.gazprombiznes.pygazprobiznes.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gazprombiznes.pygazprobiznes.game.screens.DashboardScreen1
import com.gazprombiznes.pygazprobiznes.game.screens.DashboardScreen2
import com.gazprombiznes.pygazprobiznes.game.utils.*
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animDelay
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animHide
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animShow
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedMainGroup

class AMainDashboard1(override val screen: DashboardScreen1): AdvancedMainGroup() {

    private val imgDed = Image(gdxGame.assetsAll.DED_LEFT)
    private val imgDialog = Image(gdxGame.assetsAll.DEROP)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgDed()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgDed() {
        addActor(imgDed)
        imgDed.setBounds(0f, 296f, 375f, 791f)

        addActor(imgDialog)
        imgDialog.setBounds(48f, 162f, 512f, 379f)

        animDelay(2.25f) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(DashboardScreen2::class.java.name)
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}