package com.inveanst.litka.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.inveanst.litka.game.actors.AButton
import com.inveanst.litka.game.actors.layout.AHorizontalGroup
import com.inveanst.litka.game.actors.layout.AVerticalGroup
import com.inveanst.litka.game.actors.layout.Layout
import com.inveanst.litka.game.utils.FONT_SCALE
import com.inveanst.litka.game.utils.GColor
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.font.FontParameter

class AHeader(override val screen: AdvancedScreen, title: String, endGAP: Float = 30f) : AVerticalGroup(
    screen, gap = 15f, startGap = 20f, endGap = endGAP,
    alignmentH = Layout.AlignmentHorizontal.CENTER, isWrap = true
) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL)
    private val font18        = screen.fontGenerator_Raleway_SemiBold.generateFont(fontParameter.setSize(18 * FONT_SCALE))

    private val ls18 = LabelStyle(font18, GColor.text)

    private val horizontalGroup = AHorizontalGroup(screen,10f, startGap = 20f, endGap = 20f,
        alignmentV = Layout.AlignmentVertical.CENTER,
        alignmentH = Layout.AlignmentHorizontal.CENTER,
        isWrapV = true
    )

    private val imgLine  = Image(screen.drawerUtil.getRegion(GColor.green))
    private val btnArr   = AButton(screen, AButton.Static.Type.ARR)
    private val tmpArr   = Actor()
    private val lblTitle = Label(title, ls18)

    var blockBack = {}

    override fun addActorsOnGroup() {
        addHorizontalGroup()
        addImgLine()
    }

    private fun AdvancedGroup.addHorizontalGroup() {
        addActor(horizontalGroup)
        horizontalGroup.apply {
            addBtnArr()
            addLblTitle()
            addTmpArr()
        }
    }

    private fun AdvancedGroup.addImgLine() {
        imgLine.setSize(width-10, 2f)
        addActor(imgLine)
    }

    private fun AdvancedGroup.addBtnArr() {
        btnArr.setSize(28f,28f)
        addActor(btnArr)
        btnArr.setOnClickListener { blockBack() }
    }

    private fun AdvancedGroup.addTmpArr() {
        tmpArr.setSize(28f,28f)
        addActor(tmpArr)
    }

    private fun AdvancedGroup.addLblTitle() {
        lblTitle.setAlignment(Align.center)
        lblTitle.wrap = true
        lblTitle.width = 194f
        lblTitle.setFontScale(1f / FONT_SCALE)
        lblTitle.invalidate()
        lblTitle.height = lblTitle.prefHeight
        addActor(lblTitle)
    }

}