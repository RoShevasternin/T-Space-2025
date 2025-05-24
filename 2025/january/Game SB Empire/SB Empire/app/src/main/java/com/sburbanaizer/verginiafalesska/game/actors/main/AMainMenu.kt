package com.sburbanaizer.verginiafalesska.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sburbanaizer.verginiafalesska.game.actors.APlusBlack
import com.sburbanaizer.verginiafalesska.game.actors.button.AButton
import com.sburbanaizer.verginiafalesska.game.screens.ImproveScreen
import com.sburbanaizer.verginiafalesska.game.screens.InvestScreen
import com.sburbanaizer.verginiafalesska.game.screens.MenuScreen
import com.sburbanaizer.verginiafalesska.game.screens.ProdajaScreen
import com.sburbanaizer.verginiafalesska.game.utils.Block
import com.sburbanaizer.verginiafalesska.game.utils.TIME_ANIM_SCREEN
import com.sburbanaizer.verginiafalesska.game.utils.actor.animHide
import com.sburbanaizer.verginiafalesska.game.utils.actor.animShow
import com.sburbanaizer.verginiafalesska.game.utils.advanced.AdvancedGroup
import com.sburbanaizer.verginiafalesska.game.utils.gdxGame
import kotlin.math.cos

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedGroup() {

    private val levelIndex = gdxGame.ds_Level.flow.value.lvlDobicha - 1

    private val imgMap     = Image(gdxGame.assetsAll.listDobicha[levelIndex])
    private val btnDob     = AButton(screen, AButton.Type.DOB)
    private val btnInv     = AButton(screen, AButton.Type.INV)
    private val btnImp     = AButton(screen, AButton.Type.IMPROVE)
    private val btnGo      = AButton(screen, AButton.Type.GOtoPE)
    private val aPlusBlack = APlusBlack(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMap()
        addBtns()
        addAPlusGold()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMap() {
        addActor(imgMap)
        imgMap.setBounds(0f, 362f, 714f, 1183f)
    }

    private fun addBtns() {
        val cost = when(gdxGame.ds_Level.flow.value.lvlDobicha) {
            1 -> 10
            2 -> 15
            3 -> 20
            4 -> 25
            else -> 10
        }

        addActors(btnDob, btnInv, btnImp, btnGo)
        btnDob.apply {
            setBounds(134f, 455f, 458f, 105f)
            setOnClickListener(gdxGame.soundUtil.drop) {
                aPlusBlack.startAnim()
                gdxGame.ds_Maslo.update { it + cost }
            }
        }
        btnInv.apply {
            setBounds(71f, 250f, 246f, 138f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(InvestScreen::class.java.name, screen::class.java.name)
                }
            }
        }
        btnImp.apply {
            setBounds(408f, 250f, 235f, 136f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ImproveScreen::class.java.name, screen::class.java.name)
                }
            }
        }
        btnGo.apply {
            setBounds(140f, 80f, 433f, 80f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ProdajaScreen::class.java.name)
                }
            }
        }
    }

    private fun addAPlusGold() {
        addActor(aPlusBlack)
        aPlusBlack.setBounds(16f, 487f, 682f, 179f)
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