package com.rayscaya.nasjajdenye.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.rayscaya.nasjajdenye.game.actors.button.AButton
import com.rayscaya.nasjajdenye.game.screens.EditScreen
import com.rayscaya.nasjajdenye.game.utils.GameColor
import com.rayscaya.nasjajdenye.game.utils.SizeScaler
import com.rayscaya.nasjajdenye.game.utils.actor.setBoundsScaled
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedGroup
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedScreen
import com.rayscaya.nasjajdenye.game.utils.font.FontParameter
import com.rayscaya.nasjajdenye.game.utils.gdxGame
import com.rayscaya.nasjajdenye.game.utils.toSeparateWithSymbol

class ATop(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 928f)

    private val listDataInput = gdxGame.ds_DataInput.flow.value
    private val summaAll      = listDataInput.sumOf { it.summa }
    private val countAct      = listDataInput.size

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "₽,")

    private val font99  = screen.fontGenerator_Regular.generateFont(parameter.setSize(99))
    private val font74  = screen.fontGenerator_Regular.generateFont(parameter.setSize(74))

    private val ls99   = Label.LabelStyle(font99, Color.WHITE)
    private val ls74_W = Label.LabelStyle(font74, Color.WHITE)
    private val ls74_G = Label.LabelStyle(font74, GameColor.green_43)

    private val imgPanel = Image(gdxGame.assetsAll.TOP)
    private val btnPlus  = AButton(screen, AButton.Type.Plus)
    private val lblAll   = Label("₽" + summaAll.toSeparateWithSymbol(','), ls99)
    private val lblLast  = Label("₽" + summaAll.toSeparateWithSymbol(','), ls74_W)
    private val lblAct   = Label(countAct.toString(), ls74_G)
    private val lblNoAct = Label("0", ls74_G)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        addLbls()
        addBtn()
    }

    // Actors ---------------------------------------------------------

    private fun addBtn() {
        addActor(btnPlus)
        btnPlus.setBoundsScaled(sizeScaler, 789f, 475f, 89f, 89f)
        btnPlus.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(EditScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addLbls() {
        addActors(lblAll, lblLast, lblAct, lblNoAct)
        lblAll.setBoundsScaled(sizeScaler, 49f, 433f, 440f, 126f)
        lblLast.setBoundsScaled(sizeScaler, 49f, 266f, 287f, 95f)
        lblAct.setBoundsScaled(sizeScaler, 200f, 74f, 40f, 95f)
        lblNoAct.setBoundsScaled(sizeScaler, 639f, 74f, 40f, 95f)
    }

}