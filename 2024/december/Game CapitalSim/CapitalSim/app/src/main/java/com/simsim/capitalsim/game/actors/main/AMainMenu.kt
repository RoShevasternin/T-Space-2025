package com.simsim.capitalsim.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.simsim.capitalsim.game.actors.button.AButtonOut
import com.simsim.capitalsim.game.actors.checkbox.ACheckBox
import com.simsim.capitalsim.game.actors.checkbox.ACheckBoxGroup
import com.simsim.capitalsim.game.screens.MenuScreen
import com.simsim.capitalsim.game.screens.SimScreen
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

class AMainMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var CATEGORY_INDEX = 0
            private set
    }

    private val listTypeBox = listOf(
        ACheckBox.Type.K1,
        ACheckBox.Type.K2,
        ACheckBox.Type.K3,
        ACheckBox.Type.K4,
    )

    private val imgLogo      = Image(gdxGame.assetsAll.logo)
    private val imgTitle     = Image(gdxGame.assetsAll.title)
    private val listBoxK     = List(4) { ACheckBox(screen, listTypeBox[it]) }
    private val btnStartTest = AButtonOut(screen, AButtonOut.Type.StartTest)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgLogo()
                addImgTitle()
                addListBtnK()
                addBtnStartTest()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(267f, 1882f, 471f, 107f)
    }

    private fun addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(53f, 1623f, 899f, 185f)
    }

    private fun addListBtnK() {
        val listY = listOf(1375f, 1133f, 891f, 649f)
        val cbg   = ACheckBoxGroup()

        listBoxK.onEachIndexed { index, box ->
            box.checkBoxGroup = cbg

            if (index == CATEGORY_INDEX) box.check(false)

            addActor(box)
            val nh = if (index == 0) 183f else 222f
            box.setBounds(53f, listY[index], 899f, nh)

            box.setOnCheckListener { isCheck -> if (isCheck) CATEGORY_INDEX = index }
        }
    }

    private fun addBtnStartTest() {
        addActor(btnStartTest)
        btnStartTest.apply {
            setBounds(53f, 392f, 898f, 193f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(SimScreen::class.java.name, MenuScreen::class.java.name)
                }
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