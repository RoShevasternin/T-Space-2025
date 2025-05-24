package com.pulser.purlembager.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.pulser.purlembager.game.actors.autoLayout.AHorizontalGroup
import com.pulser.purlembager.game.utils.GameColor
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.font.FontParameter
import com.pulser.purlembager.game.utils.gdxGame

class AAllDesire(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val listDesire = gdxGame.ds_Desire.flow.value.reversed()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font50        = screen.fontGenerator_SemiBold.generateFont(fontParameter.setSize(50))
    private val font37        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(37))

    private val ls50 = Label.LabelStyle(font50, GameColor.black_31)
    private val ls37 = Label.LabelStyle(font37, GameColor.black_31)

    private val listItem = List(listDesire.size) {
        AItemDesire(screen,
            listDesire[it],
            ls50,
            ls37
        )
    }

    private val hGroup1 = AHorizontalGroup(screen, 50f, isWrapHorizontal = true)
    private val hGroup2 = AHorizontalGroup(screen, 50f, isWrapHorizontal = true)
    private val scroll1 = AScrollPane(hGroup1)
    private val scroll2 = AScrollPane(hGroup2)

    override fun addActorsOnGroup() {
        addScroll()
    }

    // Actors -----------------------------------------------------

    private fun addScroll() {
        addActors(scroll1, scroll2)
        scroll1.setBounds(0f, 492f, 1109f, 409f)
        scroll2.setBounds(0f, 0f, 1109f, 409f)

        listItem.onEachIndexed { index, item ->
            item.setSize(392f, 408f)
            if (index < 3) {
                hGroup1.addActor(item)
            } else if (index < 6){
                hGroup2.addActor(item)
            } else {
                if (index % 2 == 0) hGroup2.addActor(item) else hGroup1.addActor(item)
            }
        }
    }

}