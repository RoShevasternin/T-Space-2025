package com.terniopel.antilateka.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.terniopel.antilateka.game.utils.*
import com.terniopel.antilateka.game.utils.actor.animHideSuspend
import com.terniopel.antilateka.game.utils.actor.animShowSuspend
import com.terniopel.antilateka.game.utils.advanced.AdvancedGroup
import com.terniopel.antilateka.game.utils.advanced.AdvancedScreen
import com.terniopel.antilateka.game.utils.font.FontParameter
import kotlinx.coroutines.*

class AMainLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters("Идет загрузка.").setSize(43)
    private val font          = screen.fontGenerator_InterBold.generateFont(fontParameter)

    private val ls = LabelStyle(font, GameColor.black_0B)

    private val imgMain   = Image(gdxGame.assetsLoader.main)
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
        addActors(imgMain)
        imgMain.setBounds(234f, 1002f, 537f, 168f)
    }

    private fun addLblLoading() {
        addActor(lblLoading)
        lblLoading.setBounds(334f, 852f, 337f, 57f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain(block: Block = Block { }) {
        withContext(Dispatchers.Default) {
            animShowSuspend(TIME_ANIM_SCREEN)
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