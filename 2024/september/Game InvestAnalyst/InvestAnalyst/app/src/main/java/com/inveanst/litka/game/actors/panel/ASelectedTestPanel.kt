package com.inveanst.litka.game.actors.panel

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.inveanst.litka.game.actors.AButton
import com.inveanst.litka.game.actors.layout.AVerticalGroup
import com.inveanst.litka.game.actors.layout.Layout
import com.inveanst.litka.game.utils.FONT_SCALE
import com.inveanst.litka.game.utils.GColor
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.font.FontParameter

class ASelectedTestPanel(
    override val screen: AdvancedScreen,
    val regionIcon : TextureRegion,
    val title      : String,
    val description: String
) : AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL)
    private val font20        = screen.fontGenerator_Raleway_Regular.generateFont(fontParameter.setSize(20 * FONT_SCALE))

    private val ls20 = LabelStyle(font20, GColor.text)

    private val imgPanel = Image(screen.game.all.panel_9)

    private val verticalGroup = AVerticalGroup(screen,20f, startGap = 40f, endGap = 40f,
        alignmentH = Layout.AlignmentHorizontal.CENTER, isWrap = true
    )

    override fun getPrefHeight(): Float {
        return verticalGroup.height
    }

    private val header    = AHeader(screen, title)
    private val imgIcon   = Image(regionIcon)
    private val btnGoTest = AButton(screen, AButton.Static.Type.GOTEST)
    private val lblDescription = Label(description, ls20)

    var blockGoTest = {}
    var blockBack   = {}

    override fun addActorsOnGroup() {
        addImgPanel()
        addVerticalGroup()
    }

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.width = width
    }

    private fun addVerticalGroup() {
        verticalGroup.width = width
        addActor(verticalGroup)
        verticalGroup.apply {
            addHeader()
            addImgIcon()
            addBtnGoTest()
            addLblDescription()
        }
        imgPanel.height = verticalGroup.height - 40
    }

    private fun AVerticalGroup.addHeader() {
        header.width = width
        addActor(header)
        header.blockBack = { blockBack() }
    }

    private fun AVerticalGroup.addImgIcon() {
        imgIcon.setSize(regionIcon.regionWidth / 2f, regionIcon.regionHeight / 2f)
        addActor(imgIcon)
    }

    private fun AVerticalGroup.addBtnGoTest() {
        btnGoTest.setSize(220f,43f)
        addActor(btnGoTest)
        btnGoTest.setOnClickListener { blockGoTest() }
    }

    private fun AVerticalGroup.addLblDescription() {
        lblDescription.setAlignment(Align.left)
        lblDescription.wrap = true
        lblDescription.width = 270f
        lblDescription.setFontScale(1f / FONT_SCALE)
        lblDescription.invalidate()
        lblDescription.height = lblDescription.prefHeight
        addActor(lblDescription)
    }


}