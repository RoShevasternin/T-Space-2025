package com.vectorvesta.bronfinteh.game.actors.resultItem

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.vectorvesta.bronfinteh.game.actors.AItemSimple
import com.vectorvesta.bronfinteh.game.actors.autoLayout.AVerticalGroup
import com.vectorvesta.bronfinteh.game.actors.main.delete.AMainDeleteLeasing
import com.vectorvesta.bronfinteh.game.dataStore.DataItems
import com.vectorvesta.bronfinteh.game.screens.delete.DeleteLeasingScreen
import com.vectorvesta.bronfinteh.game.utils.actor.disable
import com.vectorvesta.bronfinteh.game.utils.actor.setOnTouchListener
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedGroup
import com.vectorvesta.bronfinteh.game.utils.advanced.AdvancedScreen
import com.vectorvesta.bronfinteh.game.utils.gdxGame

class AItemLeasing(
    val dataItems: DataItems,
    override val screen: AdvancedScreen,
    val listResultText : List<String>,

    ls43      : Label.LabelStyle,
    ls38_gray : Label.LabelStyle,
    ls38_black: Label.LabelStyle,
): AdvancedGroup() {

    private val lblTitle        = Label(listResultText.first(), ls43)
    private val listHistoryItem = List(5) {
        AItemSimple(screen,
            GLOBAL_listTitleLeasing[it],
            listResultText[it + 1],
            ls38_gray,
            ls38_black,
        )
    }
    private val verticalGroup = AVerticalGroup(screen, 48f)

    override fun addActorsOnGroup() {
        addActor(lblTitle)
        lblTitle.setBounds(297f, 1094f, 211f, 44f)
        lblTitle.setAlignment(Align.center)

        addActor(verticalGroup)
        verticalGroup.also {
            it.setBounds(0f, 0f, 805f, 1015f)

            listHistoryItem.onEach { item ->
                item.setSize(805f, 165f)
                it.addActor(item)
            }
        }

        children.onEach { it.disable() }
        this.setOnTouchListener(gdxGame.soundUtil) {
            AMainDeleteLeasing.dataItems = this.dataItems

            screen.hideScreen {
                gdxGame.navigationManager.navigate(DeleteLeasingScreen::class.java.name)
            }
        }
    }

}