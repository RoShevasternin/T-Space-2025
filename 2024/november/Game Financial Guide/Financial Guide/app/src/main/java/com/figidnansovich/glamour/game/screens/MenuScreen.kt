package com.figidnansovich.glamour.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.figidnansovich.glamour.game.LibGDXGame
import com.figidnansovich.glamour.game.screens.main.*
import com.figidnansovich.glamour.game.utils.TIME_ANIM
import com.figidnansovich.glamour.game.utils.actor.animHide
import com.figidnansovich.glamour.game.utils.actor.animShow
import com.figidnansovich.glamour.game.utils.actor.setOnClickListener
import com.figidnansovich.glamour.game.utils.advanced.AdvancedScreen
import com.figidnansovich.glamour.game.utils.advanced.AdvancedStage
import com.figidnansovich.glamour.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirstShow = true
    }

    private val imgLogo = Image(game.loader.logo)
    private val imgMenu = Image(game.all.manu_main)
    private val listA   = List(5) { Actor() }

    override fun show() {
        if (isFirstShow) {
            isFirstShow = false

            game.musicUtil.also { mu ->
                mu.music = mu.financer.apply { isLooping = true }
                mu.coff = 0.1f
            }
        }

        stageUI.root.animHide()
        setBackBackground(game.loader.B_Loader.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgLogo, imgMenu)
        imgLogo.setBounds(178f,1893f,657f,180f)
        imgMenu.setBounds(86f,255f,844f,1483f)

        val listScreenName = listOf(
            AMainScreen::class.java.name,
            BMainScreen::class.java.name,
            CMainScreen::class.java.name,
            DMainScreen::class.java.name,
            EMainScreen::class.java.name,
        )

        var ny = 1413f
        listA.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(86f, ny, 843f, 229f)
            ny -= 59 + 229

            actor.setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(listScreenName[index], MenuScreen::class.java.name)
                }
            }
        }

    }

}