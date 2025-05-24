package com.pulser.purlembager.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pulser.purlembager.game.utils.actor.PosSize
import com.pulser.purlembager.game.utils.actor.disable
import com.pulser.purlembager.game.utils.actor.setBounds
import com.pulser.purlembager.game.utils.actor.setOnClickListener
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame

class ADate(
    override val screen: AdvancedScreen,
    ls37: Label.LabelStyle
): AdvancedGroup() {

    private val imgYellow = Image(gdxGame.assetsAll.selected)
    private val lbl1      = Label("ДЕНЬ", ls37)
    private val lbl2      = Label("НЕДЕЛЯ", ls37)
    private val lbl3      = Label("МЕСЯЦ", ls37)
    private val lbl4      = Label("ГОД", ls37)

    private val listPosSize = listOf(
        PosSize(0f, 0f, 199f, 119f),
        PosSize(247f, 0f, 225f, 119f),
        PosSize(528f, 0f, 225f, 119f),
        PosSize(780f, 0f, 199f, 119f),
    )

    var blockSelect: (String) -> Unit = {}

    override fun addActorsOnGroup() {
        addImgYellow()
        addLbls()
    }

    // Actors ----------------------------------------------------------------------

    private fun addImgYellow() {
        addActor(imgYellow)
        imgYellow.setBounds(listPosSize.first())
        imgYellow.disable()
    }

    private fun addLbls() {
        addActors(lbl1, lbl2, lbl3, lbl4)
        lbl1.apply {
            setBounds(listPosSize[0])
            setAlignment(Align.center)
            setOnClickListener {
                animYellow(listPosSize[0])
                blockSelect("день")
            }
        }
        lbl2.apply {
            setBounds(listPosSize[1])
            setAlignment(Align.center)
            setOnClickListener {
                animYellow(listPosSize[1])
                blockSelect("неделя")
            }
        }
        lbl3.apply {
            setBounds(listPosSize[2])
            setAlignment(Align.center)
            setOnClickListener {
                animYellow(listPosSize[2])
                blockSelect("месяц")
            }
        }
        lbl4.apply {
            setBounds(listPosSize[3])
            setAlignment(Align.center)
            setOnClickListener {
                animYellow(listPosSize[3])
                blockSelect("год")
            }
        }
    }

    // Logic ----------------------------------------------------------------------------

    private fun animYellow(posSize: PosSize) {
        imgYellow.clearActions()
        imgYellow.addAction(Actions.parallel(
            Actions.moveTo(posSize.x, posSize.y, 0.4f, Interpolation.sine),
            Actions.sizeTo(posSize.w, posSize.h, 0.4f, Interpolation.sine)
        ))
    }

}