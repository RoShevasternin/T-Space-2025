package com.axmeron.investnaveratep.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.axmeron.investnaveratep.game.utils.CountdownTimer
import com.axmeron.investnaveratep.game.utils.GameColor
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.font.FontParameter
import com.axmeron.investnaveratep.game.utils.gdxGame

class ATimer(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val fontR_35 = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(35))

    private val lsR_35 = Label.LabelStyle(fontR_35, Color.WHITE)

    private val imgPanel = Image(gdxGame.assetsAll.timer)
    private val lblTime  = Label("", lsR_35)

    var blockFinish = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)

        addActor(lblTime)
        lblTime.apply {
            setBounds(73f, 25f, 96f, 24f)
            setAlignment(Align.center)

            coroutine?.let {
                CountdownTimer(it, 240,
                    onTick = {
                        setText(it)
                    },
                    onFinish = {
                        blockFinish()
                    }
                ).start()
            }
        }
    }

}