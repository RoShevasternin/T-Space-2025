package com.simsim.capitalsim.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.simsim.capitalsim.game.utils.*
import com.simsim.capitalsim.game.utils.actor.animHideSuspend
import com.simsim.capitalsim.game.utils.actor.animShowSuspend
import com.simsim.capitalsim.game.utils.advanced.AdvancedGroup
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.font.FontParameter
import kotlinx.coroutines.*

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters("Идет загрузка.").setSize(43)
    private val font          = screen.fontGenerator_InterBold.generateFont(fontParameter)

    private val ls = LabelStyle(font, GameColor.black_0B)

    private val imgMain   = Image(gdxGame.assetsLoader.main)
    private val imgCircle = Image(gdxGame.assetsLoader.circle)
    private val lblLoading = Label("", ls)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addLogo()
                addLblLoading()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addLogo() {
        addActors(imgMain, imgCircle)
        imgMain.setBounds(341f, 1002f, 323f, 322f)
        imgCircle.setBounds(396f, 1146f, 82f, 81f)
    }

    private fun addLblLoading() {
        addActor(lblLoading)
        lblLoading.setBounds(334f, 852f, 337f, 57f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain(block: Block = Block { }) {
        withContext(Dispatchers.Default) {
            animShowSuspend(TIME_ANIM_SCREEN)
            animForever_Logo()
            launch { animForever_LblLoading() }
        }
        block.invoke()
    }

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Anim ------------------------------------------------

    private fun animForever_Logo() {
        runGDX {
            imgCircle.addAction(Actions.forever(Actions.sequence(
                Actions.moveTo(433f, 1109f, 0.25f, Interpolation.sineOut),
                Actions.moveTo(396f, 1146f, 0.25f, Interpolation.sineOut),
            )))
        }
    }

    private suspend fun animForever_LblLoading() = CompletableDeferred<Unit>().also {
        val delayTime = 250L
        while (it.isActive) {
            runGDX { lblLoading.setText("Идет загрузка") }
            delay(delayTime)
            runGDX { lblLoading.setText("Идет загрузка.") }
            delay(delayTime)
            runGDX { lblLoading.setText("Идет загрузка..") }
            delay(delayTime)
            runGDX { lblLoading.setText("Идет загрузка...") }
            delay(delayTime)
        }
    }

}