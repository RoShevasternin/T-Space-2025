package com.rugosbon.pybonesrus.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.rugosbon.pybonesrus.game.actors.button.AButton
import com.rugosbon.pybonesrus.game.screens.MenuScreen
import com.rugosbon.pybonesrus.game.screens.PreviewScreen
import com.rugosbon.pybonesrus.game.utils.Block
import com.rugosbon.pybonesrus.game.utils.TIME_ANIM_SCREEN
import com.rugosbon.pybonesrus.game.utils.actor.animDelay
import com.rugosbon.pybonesrus.game.utils.actor.animHide
import com.rugosbon.pybonesrus.game.utils.actor.animShow
import com.rugosbon.pybonesrus.game.utils.actor.setBounds
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedMainGroup
import com.rugosbon.pybonesrus.game.utils.gdxGame

class AMainPreview(
    override val screen: PreviewScreen,
): AdvancedMainGroup() {

    private var currentIndex = 0

    private val listTextureArrow = gdxGame.assetsAll.listS
    private val listTextureText  = gdxGame.assetsAll.listB

    private val imgArrow = Image(listTextureArrow[currentIndex])
    private val imgText  = Image(listTextureText[currentIndex])
    private val btnNext  = AButton(screen, AButton.Type.Next)

    override fun addActorsOnGroup() {
        color.a = 0f

        addListAT()
        addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListAT() {
        addActors(imgArrow, imgText)
        imgArrow.setBounds(0f, 915f, 970f, 1190f)
        imgText.setBounds(44f, 416f, 881f, 474f)
    }

    private fun addBtns() {
        addActor(btnNext)
        btnNext.setBounds(44f, 152f, 881f, 141f)

        btnNext.setOnClickListener {
            currentIndex++
            if (currentIndex >= 3) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            } else {
                imgArrow.drawable = TextureRegionDrawable(listTextureArrow[currentIndex])
                imgText.drawable  = TextureRegionDrawable(listTextureText[currentIndex])
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}