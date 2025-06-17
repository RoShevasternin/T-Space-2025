package com.gazprombiznes.pygazprobiznes.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gazprombiznes.pygazprobiznes.game.actors.ABalance
import com.gazprombiznes.pygazprobiznes.game.actors.button.AButton
import com.gazprombiznes.pygazprobiznes.game.screens.DashboardScreen2
import com.gazprombiznes.pygazprobiznes.game.screens.GameScreen
import com.gazprombiznes.pygazprobiznes.game.utils.*
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animDelay
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animHide
import com.gazprombiznes.pygazprobiznes.game.utils.actor.animShow
import com.gazprombiznes.pygazprobiznes.game.utils.advanced.AdvancedMainGroup

class AMainDashboard2(override val screen: DashboardScreen2): AdvancedMainGroup() {

    private val btnNext = AButton(screen, AButton.Type.Start)
    private val imgDed  = Image(gdxGame.assetsAll.DED_RIGHT)
    private val imgDialog  = Image(gdxGame.assetsAll.PERRDO)
    private val imgBur     = Image(gdxGame.assetsAll.listItemeser.first())

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgDed()
        addBtnNext()

        val balance = ABalance(screen)
        addActor(balance)
        balance.setBounds(48f, 1184f, 512f, 73f)

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgDed() {

        addAndFillActor(imgBur)

        addActor(imgDed)
        imgDed.setBounds(271f, 310f, 336f, 854f)

        addActor(imgDialog)
        imgDialog.setBounds(48f, 649f, 512f, 142f)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(48f, 283f, 512f, 91f)
        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name)
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