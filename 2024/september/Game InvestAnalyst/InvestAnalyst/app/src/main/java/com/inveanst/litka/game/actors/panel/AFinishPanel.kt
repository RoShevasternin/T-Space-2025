package com.inveanst.litka.game.actors.panel

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.TypingLabel
import com.inveanst.litka.game.actors.layout.AVerticalGroup
import com.inveanst.litka.game.actors.layout.Layout
import com.inveanst.litka.game.utils.*
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.font.FontParameter

class AFinishPanel(
    override val screen: AdvancedScreen,
    val title     : String,
    val regionIcon: TextureRegion,
    val result    : Int,
    val text1     : String,
    val text2     : String,
) : AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL)
    private val font17        = screen.fontGenerator_Raleway_Regular.generateFont(fontParameter.setSize(17 * FONT_SCALE))
    private val font22        = screen.fontGenerator_Raleway_SemiBold.generateFont(fontParameter.setSize(22 * FONT_SCALE))
    private val font26        = screen.fontGenerator_Raleway_SemiBold.generateFont(fontParameter.setSize(26 * FONT_SCALE))

    private val ls22 = LabelStyle(font22, GColor.text)
    private val ls17 = LabelStyle(font17, GColor.answerM)

    private val imgPanel = Image(screen.game.all.panel_9)

    private val verticalGroup1 = AVerticalGroup(screen,30f,
        alignmentH = Layout.AlignmentHorizontal.CENTER,
    )
    private val verticalGroup2 = AVerticalGroup(screen,10f,
        alignmentH = Layout.AlignmentHorizontal.CENTER,
    )

    private val header  = AHeader(screen, title,0f)
    private val imgIcon = Image(regionIcon)

    private val lblCounter = TypingLabel("{COLOR=#${GColor.green}}$result{COLOR=#${GColor.text}}/5", Font(font26))
    private val lblText1   = Label(text1, ls22)
    private val lblText2   = Label(text2, ls17)

    var blockBack  = {}

    override fun addActorsOnGroup() {
        addImgPanel()
        addVerticalGroup1()
    }

    private fun addImgPanel() {
        addAndFillActor(imgPanel)
    }

    // VerticalGroup 1 ------------------------------------------------------

    private fun addVerticalGroup1() {
        addAndFillActor(verticalGroup1)
        verticalGroup1.apply {
            addHeader()
            addImgIcon()
            addVerticalGroup2()
        }
    }

    private fun AVerticalGroup.addHeader() {
        header.width = width
        addActor(header)
        header.blockBack = { this@AFinishPanel.blockBack() }
    }

    private fun AVerticalGroup.addImgIcon() {
        imgIcon.setSize(regionIcon.regionWidth / 2f, regionIcon.regionHeight / 2f)
        addActor(imgIcon)
    }

    // VerticalGroup 2 ------------------------------------------------------

    private fun AVerticalGroup.addVerticalGroup2() {
        verticalGroup2.apply {
            addLblCounter()
            addLblText1()
            addLblText2()
        }
        addActor(verticalGroup2)
    }

    private fun AVerticalGroup.addLblCounter() {
        lblCounter.skipToTheEnd(true)
        lblCounter.alignment = Align.center
        lblCounter.font.scale(1f / FONT_SCALE)
        lblCounter.setSize(44f,34f)
        addActor(lblCounter)
    }

    private fun AVerticalGroup.addLblText1() {
        lblText1.setAlignment(Align.center)
        lblText1.wrap = true
        lblText1.width = 270f
        lblText1.setFontScale(1f / FONT_SCALE)
        lblText1.invalidate()
        lblText1.height = lblText1.prefHeight
        addActor(lblText1)
    }

    private fun AVerticalGroup.addLblText2() {
        lblText2.setAlignment(Align.center)
        lblText2.wrap = true
        lblText2.width = 270f
        lblText2.setFontScale(1f / FONT_SCALE)
        lblText2.invalidate()
        lblText2.height = lblText2.prefHeight
        addActor(lblText2)
    }

}