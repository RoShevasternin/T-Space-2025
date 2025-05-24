package com.liberator.wisoliter.game.actors.tutorial

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.liberator.wisoliter.game.actors.button.AButton
import com.liberator.wisoliter.game.utils.GameColor
import com.liberator.wisoliter.game.utils.ITEM_COUNT
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.font.FontParameter
import com.liberator.wisoliter.game.utils.gdxGame

class ATutorial_2(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font41        = screen.fontGenerator_PusiaBold.generateFont(fontParameter.setSize(41))

    private val ls41 = LabelStyle(font41, GameColor.white)

    private val listItem  = List(ITEM_COUNT) { Image(gdxGame.assetsAll.listItem[it]) }
    private val listXP    = List(ITEM_COUNT) { Label("+${gdxGame.listItemXP[it]} xp", ls41) }
    private val listSTO   = List(ITEM_COUNT) { AButton(screen, AButton.Type.STO) }

    override fun addActorsOnGroup() {
        addListItem()
        addListXP()
        addListSTO()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListItem() {
        var ny = 1385f
        listItem.onEachIndexed { index, img ->
            addActor(img)
            img.setBounds(41f, ny, 300f, 165f)
            ny -= (58 + 165)
        }
    }

    private fun addListXP() {
        var ny = 1453f
        listXP.onEachIndexed { index, lbl ->
            addActor(lbl)
            lbl.setBounds(345f, ny, 160f, 29f)
            lbl.setAlignment(Align.right)
            ny -= (194 + 29)
        }
    }

    private fun addListSTO() {
        var ny = 1426f
        listSTO.onEachIndexed { index, lbl ->
            addActor(lbl)
            lbl.setBounds(576f, ny, 157f, 82f)
            ny -= (140 + 82)
        }
    }

}