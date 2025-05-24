package com.frusune.abvger.game.actors.dataPanel

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Styles.LabelStyle
import com.github.tommyettinger.textra.TypingLabel
import com.frusune.abvger.game.utils.GColor
import com.frusune.abvger.game.utils.actor.setOnClickListener
import com.frusune.abvger.game.utils.advanced.AdvancedGroup
import com.frusune.abvger.game.utils.advanced.AdvancedScreen
import com.frusune.abvger.game.utils.toBalance
import kotlin.math.roundToInt

class ATopData(
    override val screen: AdvancedScreen,
    val title  : String,
    val summa  : Int,
    val period : Int,
    val percent: Int,
    val ls37   : Label.LabelStyle,
) : AdvancedGroup() {

    private val dohod = (summa * (percent / 100f)).roundToInt()

    private val textSumma   = "${summa.toBalance} [#${GColor.grayWhite}]{SIZE=%95}₽"
    private val textPeriod  = "$period [#${GColor.grayWhite}]{SIZE=%95}${getMonth()}"
    private val textPercent = "$percent[#${GColor.grayWhite}]{SIZE=%95}%"
    private val textDohod   = "${dohod.toBalance} [#${GColor.grayWhite}]{SIZE=%95}₽"

    private val lsTyping37 = LabelStyle(ls37)

    private val aRemove    = Actor()
    private val aEdit      = Actor()
    private val lblTitle   = Label(title, ls37)
    private val lblSumma   = TypingLabel(textSumma, lsTyping37)
    private val lblPeriod  = TypingLabel(textPeriod, lsTyping37)
    private val lblPercent = TypingLabel(textPercent, lsTyping37)
    private val lblDohod   = TypingLabel(textDohod, lsTyping37)

    var blockRemove: () -> Unit = {}
    var blockEdit  : () -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.all.panel_data))
        addLbls()
        addBtns()
    }

    private fun addLbls() {
        addActors(lblTitle, lblSumma, lblPeriod, lblPercent, lblDohod)
        lblTitle.apply {
            setBounds(181f,451f,300f,26f)
            setAlignment(Align.center)
        }
        lblSumma.setBounds(120f,299f,175f,26f)
        lblPeriod.setBounds(364f,299f,168f,26f)
        lblPercent.setBounds(120f,157f,64f,26f)
        lblDohod.setBounds(364f,157f,152f,26f)
    }

    private fun addBtns() {
        addActors(aRemove, aEdit)
        aRemove.apply {
            setBounds(95f,68f,186f,71f)
            setOnClickListener(screen.game.soundUtil) {
                blockRemove()
            }
        }
        aEdit.apply {
            setBounds(313f,68f,240f,71f)
            setOnClickListener(screen.game.soundUtil) {
                blockEdit()
            }
        }
    }

    // Logic -------------------------------------------------------------------

    private fun getMonth() = when {
        period == 1            -> "месяц"
        period in (2..4) -> "месяца"
        else                   -> "месяцев"
    }

}