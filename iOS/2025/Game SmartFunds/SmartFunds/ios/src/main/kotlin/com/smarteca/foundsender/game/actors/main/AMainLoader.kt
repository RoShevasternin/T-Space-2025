package com.smarteca.foundsender.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.smarteca.foundsender.game.actors.progress.AProgress
import com.smarteca.foundsender.game.screens.LoaderScreen
import com.smarteca.foundsender.game.utils.Acts
import com.smarteca.foundsender.game.utils.advanced.AdvancedGroup
import com.smarteca.foundsender.game.utils.advanced.AdvancedScreen
import com.smarteca.foundsender.game.utils.gdxGame

open class AMainLoader(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val imgLoader = Image(gdxGame.assetsLoader.loader)
    private val imgWallet = Image(gdxGame.assetsLoader.wallet)
    private val progress  = AProgress(screen)

    override fun addActorsOnGroup() {
        //addAndFillActor(Image(screen.drawerUtil.getTexture(Color.RED)))
        addImgLoader()
        addImgWallet()
        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoader() {
        addActor(imgLoader)
        imgLoader.setBounds(136f, 1058f, 907f, 263f)
    }

    private fun addImgWallet() {
        addActor(imgWallet)
        imgWallet.setBounds(526f, 1341f, 127f, 140f)
        //imgWallet.setBounds(0f, HEIGHT_UI - 140f, 127f, 140f)

        imgWallet.apply {
            setOrigin(Align.center)
            addAction(Acts.forever(Acts.sequence(
                Acts.scaleTo(0.9f, 0.9f, 0.3f),
                Acts.scaleTo(1f, 1f, 0.3f),
            )))
        }
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(136f, 1142f, 907f, 15f)
        //progress.setBounds(136f, 0f, 907f, 12f)
    }

    // Logic --------------------------------------------------------

    fun setPercent(percent: Float) {
        progress.progressPercentFlow.value = percent
    }

}
