package com.sburbanaizer.verginiafalesska.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.sburbanaizer.verginiafalesska.game.actors.APanel
import com.sburbanaizer.verginiafalesska.game.screens.MenuScreen
import com.sburbanaizer.verginiafalesska.game.screens.TutorialScreen
import com.sburbanaizer.verginiafalesska.game.utils.Acts
import com.sburbanaizer.verginiafalesska.game.utils.Block
import com.sburbanaizer.verginiafalesska.game.utils.GameColor
import com.sburbanaizer.verginiafalesska.game.utils.TIME_ANIM_SCREEN
import com.sburbanaizer.verginiafalesska.game.utils.actor.animHide
import com.sburbanaizer.verginiafalesska.game.utils.actor.animShow
import com.sburbanaizer.verginiafalesska.game.utils.actor.disable
import com.sburbanaizer.verginiafalesska.game.utils.actor.enable
import com.sburbanaizer.verginiafalesska.game.utils.actor.setBounds
import com.sburbanaizer.verginiafalesska.game.utils.actor.setOnClickListener
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedGroup
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedScreen
import com.sburbanaizer.verginiafalesska.game.utils.font.FontParameter
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame

class AMainTutorial(
    override val screen: TutorialScreen,
): AdvancedGroup() {

    private val img = Image(gdxGame.assetsAll.Tutorial_1)

    override fun addActorsOnGroup() {
        color.a = 0f

        addAndFillActor(img)

        val a1 = Actor()
        val a2 = Actor()
        addActors(a1, a2)

        a1.apply {
            setBounds(261f, 693f, 190f, 64f)
            setOnClickListener(gdxGame.soundUtil) {
                a1.disable()
                a2.enable()
                this.animHide {
                    img.drawable = TextureRegionDrawable(gdxGame.assetsAll.Tutorial_2)
                    this.animShow()
                }
            }
        }
        a2.apply {
            setBounds(261f, 647f, 190f, 64f)
            disable()
            setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }

        animShowMain()
    }

    // Anim Main ------------------------------------------------

    private fun animShowMain() {
        animShow(TIME_ANIM_SCREEN)
    }

    fun animHideMain(block: Block = Block {}) {
        animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

}