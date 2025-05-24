package com.romander.navfenixgater.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.romander.navfenixgater.game.actors.button.AButton
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.SizeScaler
import com.romander.navfenixgater.game.utils.actor.setBoundsScaled
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.gdxGame

class ABottom(
    override val screen: AdvancedScreen,
    val default: DefType,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 692f)

    private val btnGlav = AButton(screen, AButton.Type.Glav)
    private val btnCalc = AButton(screen, AButton.Type.Calc)
    private val btnHist = AButton(screen, AButton.Type.Hist)

    var blockGlav = {}
    var blockCalc = {}
    var blockHist = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(GameColor.gray)))
        addActors(btnGlav, btnCalc, btnHist)
        btnGlav.setBoundsScaled(sizeScaler, 0f, 36f, 242f, 92f)
        btnCalc.setBoundsScaled(sizeScaler, 225f, 36f, 242f, 92f)
        btnHist.setBoundsScaled(sizeScaler, 450f, 36f, 242f, 92f)

        when(default) {
            DefType.Glav -> btnGlav.disable()
            DefType.Calc -> btnCalc.disable()
            DefType.Hist -> btnHist.disable()
        }

        btnGlav.setOnClickListener {
            btnGlav.disable()
            blockGlav()
        }
        btnCalc.setOnClickListener {
            btnCalc.disable()
            blockCalc()
        }
        btnHist.setOnClickListener {
            btnHist.disable()
            blockHist()
        }
    }

    enum class DefType {
        Glav, Calc, Hist
    }

}