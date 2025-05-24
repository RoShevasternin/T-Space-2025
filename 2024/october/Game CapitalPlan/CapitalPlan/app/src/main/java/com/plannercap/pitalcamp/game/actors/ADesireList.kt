package com.plannercap.pitalcamp.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.plannercap.pitalcamp.game.utils.Block
import com.plannercap.pitalcamp.game.utils.GColor
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedGroup
import com.plannercap.pitalcamp.game.utils.advanced.AdvancedScreen
import com.plannercap.pitalcamp.game.utils.font.FontParameter

class ADesireList(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val fontMedium39  = screen.fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(39))
    private val fontMedium35  = screen.fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.All).setSize(35))

    private val ls39 = LabelStyle(fontMedium39, GColor.dark)
    private val ls35 = LabelStyle(fontMedium35, GColor.dark)

    // Actors
    private val imgPanel = Image(screen.game.all.your_desire)
    private val vertical = NewVerticalGroup(screen,44f, paddingBottom = 44f, paddingTop = 44f, isWrap = true)
    private val scroll   = ScrollPane(vertical)

    var blockEdit = Block {}

    override fun addActorsOnGroup() {
        addImgPanel()
        addScroll()
        update()
    }

    private fun AdvancedGroup.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(0f,377f,685f,115f)
    }

    private fun AdvancedGroup.addScroll() {
        addActor(scroll)
        scroll.setSize(685f,377f)
        vertical.setSize(685f,377f)
    }

    // Logic -----------------------------------------------------

    fun update() {
        vertical.disposeChildren()
        val listDesire = screen.game.desireUtil.desireListFlow.value.reversed()

        val listADesire = List(listDesire.size) { ADesire(
            screen,
            listDesire[it].nameDesire,
            listDesire[it].summa,
            ls39, ls35
        ) }

        listADesire.onEach { aDesire ->
            aDesire.setSize(684f,267f)
            vertical.addActor(aDesire)

            aDesire.blockRemove = Block {
                screen.game.desireUtil.update { desireList ->
                    desireList.apply {
                        remove(desireList.first {
                            it.nameDesire == aDesire.nameDesire &&
                            it.summa == aDesire.summa
                        })
                    }
                }
                aDesire.addAction(Actions.removeActor())
            }
            aDesire.blockEdit = Block {
                screen.game.desireUtil.update { desireList ->
                    desireList.apply {
                        remove(desireList.first {
                            it.nameDesire == aDesire.nameDesire &&
                            it.summa == aDesire.summa
                        })
                    }
                }
                aDesire.addAction(Actions.removeActor())
                blockEdit.invoke()
            }
        }

    }

}