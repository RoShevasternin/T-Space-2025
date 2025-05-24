package com.startegfin.financester.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Styles.LabelStyle
import com.startegfin.financester.game.data.Transaction
import com.startegfin.financester.game.data.TransactionType
import com.startegfin.financester.game.global.toBalance
import com.startegfin.financester.game.utils.GColor
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen

class ACubeRoshod(
    override val screen: AdvancedScreen,
    val region: TextureRegion,
    val title: String,
    val percent: Int,
    val suma: Int,
    lsSemiBold32: Label.LabelStyle,
) : AdvancedGroup() {

    private val imgIcon  = Image(region)
    private val lblTitle = Label("$title: $percent%", lsSemiBold32)
    private val lblSuma  = Label("-${suma.toBalance} ₽", lsSemiBold32)

    override fun addActorsOnGroup() {
        addActors(imgIcon, lblTitle, lblSuma)
        imgIcon.setBounds(0f,0f,40f,40f)
        lblTitle.setBounds(60f,0f,230f,39f)
        lblSuma.setBounds(420f,0f,172f,39f)

        lblSuma.apply {
            setAlignment(Align.right)
            style = Label.LabelStyle(style.font, GColor.red)
        }
    }

}