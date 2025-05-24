package com.vectorvesta.bronfinteh.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGroup
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedScreen
import com.vectorvesta.bronfinteh.game.utils.gdxGame

class AItemSimple(
    override val screen: AdvancedScreen,
    val title: String,
    val text : String,

    ls38_gray : Label.LabelStyle,
    ls38_black: Label.LabelStyle,
): AdvancedGroup() {

    private val lblTitle = Label(title, ls38_gray)
    private val lblText  = Label(text, ls38_black)
    private val imgLine  = Image(gdxGame.assetsAll.line)

    override fun addActorsOnGroup() {
        addActors(imgLine, lblTitle, lblText)

        imgLine.setSize(width, 2.5f)
        lblTitle.setBounds(0f, 91f, 191f, 49f)
        lblText.setBounds(0f, 24f, 188f, 34f)
    }

}