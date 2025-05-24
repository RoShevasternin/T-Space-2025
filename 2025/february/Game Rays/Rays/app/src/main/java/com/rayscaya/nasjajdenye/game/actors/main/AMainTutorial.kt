package com.rayscaya.nasjajdenye.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.rayscaya.nasjajdenye.game.screens.MenuScreen
import com.rayscaya.nasjajdenye.game.screens.TutorialScreen
import com.rayscaya.nasjajdenye.game.utils.Acts
import com.rayscaya.nasjajdenye.game.utils.Block
import com.rayscaya.nasjajdenye.game.utils.TIME_ANIM_SCREEN
import com.rayscaya.nasjajdenye.game.utils.actor.animDelay
import com.rayscaya.nasjajdenye.game.utils.actor.animHide
import com.rayscaya.nasjajdenye.game.utils.actor.animShow
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedMainGroup
import com.rayscaya.nasjajdenye.game.utils.gdxGame

class AMainTutorial(
    override val screen: TutorialScreen,
): AdvancedMainGroup() {

    private val imgMain = Image(gdxGame.assetsAll.TXT1)

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addImgMain()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMain() {
        addActor(imgMain)
        imgMain.setBounds(84f, 761f, 760f, 422f)

        imgMain.addAction(Acts.sequence(
            Acts.delay(2f),
            Acts.run {
                imgMain.animHide(TIME_ANIM_SCREEN) {
                    imgMain.drawable = TextureRegionDrawable(gdxGame.assetsAll.TXT2)
                    imgMain.animShow(TIME_ANIM_SCREEN)
                }
            },
            Acts.delay(2f),
            Acts.run {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        ))
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}