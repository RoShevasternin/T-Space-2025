package com.padrestoranom.easypaydonalds.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.padrestoranom.easypaydonalds.game.actors.checkbox.ACheckBoxGroup
import com.padrestoranom.easypaydonalds.game.utils.GLOBAL_listCategoryName_Expense
import com.padrestoranom.easypaydonalds.game.utils.GameColor
import com.padrestoranom.easypaydonalds.game.utils.actor.setOnClickListener
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedGroup
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedScreen
import com.padrestoranom.easypaydonalds.game.utils.font.FontParameter
import com.padrestoranom.easypaydonalds.game.utils.gdxGame

class AHarcamalar(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font26        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(26))

    private val ls26 = Label.LabelStyle(font26, GameColor.black_42)

    private val cbg = ACheckBoxGroup()

    private val listItem = List(9) { ACategoryItem(screen, cbg, gdxGame.assetsAll.listEXPENSE[it], GLOBAL_listCategoryName_Expense[it], ls26) }

    var blockIndex: (Int) -> Unit= {}

    override fun addActorsOnGroup() {
        var nx = 0f
        var ny = 373f

        listItem.forEachIndexed { index, item ->
            addActor(item)
            item.setBounds(nx, ny, 192f, 154f)

            nx += 32 + 192
            if (index.inc() % 3 == 0) {
                nx = 0f
                ny -= 33 + 154
            }

            item.setOnClickListener {
                blockIndex(index)
            }
        }
    }

}