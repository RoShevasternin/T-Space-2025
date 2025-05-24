package com.simsim.capitalsim.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.simsim.capitalsim.game.actors.button.AButtonOut
import com.simsim.capitalsim.game.screens.MenuScreen
import com.simsim.capitalsim.game.utils.Block
import com.simsim.capitalsim.game.utils.TIME_ANIM_SCREEN
import com.simsim.capitalsim.game.utils.actor.animHideSuspend
import com.simsim.capitalsim.game.utils.actor.animShowSuspend
import com.simsim.capitalsim.game.utils.advanced.AdvancedGroup
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.gdxGame
import com.simsim.capitalsim.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainOnboarding(override val screen: AdvancedScreen): AdvancedGroup() {

    private var currentIndex = 0

    private val img = Image(gdxGame.assetsAll.listO[currentIndex])
    private val btn = AButtonOut(screen, AButtonOut.Type.p1)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImg()
                addBtn()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImg() {
        addAndFillActor(img)
    }

    private fun addBtn() {
        addActor(btn)
        btn.setBounds(53f, 144f, 898f, 193f)
        btn.setOnClickListener {
            if ((currentIndex + 1) <= 2) {
                currentIndex++
                img.drawable = TextureRegionDrawable(gdxGame.assetsAll.listO[currentIndex])

                if (currentIndex == 1) btn.setType(AButtonOut.Type.p2)
            } else screen.hideScreen {
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        animShowSuspend(TIME_ANIM_SCREEN)
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Anim ------------------------------------------------


}