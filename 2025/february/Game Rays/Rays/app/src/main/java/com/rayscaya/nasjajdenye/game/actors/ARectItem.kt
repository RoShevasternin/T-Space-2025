package com.rayscaya.nasjajdenye.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.rayscaya.nasjajdenye.game.data.DataInput
import com.rayscaya.nasjajdenye.game.utils.actor.disable
import com.rayscaya.nasjajdenye.game.utils.actor.setBounds
import com.rayscaya.nasjajdenye.game.utils.actor.setOnClickListener
import com.rayscaya.nasjajdenye.game.utils.addMonthsToToday
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedGroup
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedScreen
import com.rayscaya.nasjajdenye.game.utils.gdxGame
import com.rayscaya.nasjajdenye.game.utils.toSeparateWithSymbol
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class ARectItem(
    override val screen: AdvancedScreen,
    val dataInput: DataInput,
    val ls39: Label.LabelStyle
): AdvancedGroup() {

    private val summaPlus: Int = (dataInput.summa * (dataInput.percent / 100f)).roundToInt()

    private val imgMain    = Image(gdxGame.assetsAll.RECT)
    private val lblTitle   = Label(dataInput.title, ls39)
    private val lblSumma   = Label(dataInput.summa.toSeparateWithSymbol(' ') + " ₽", ls39)
    private val lblDo      = Label(addMonthsToToday(dataInput.period), ls39)
    private val lblPercent = Label("${dataInput.percent}%", ls39)
    private val lblPlus    = Label(summaPlus.toSeparateWithSymbol(' ') + " ₽", ls39)

    //var blockClick: (DataInput) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgMain)
        addLbls()

        children.onEach { it.disable() }
        //setOnClickListener(gdxGame.soundUtil) { blockClick(dataInput) }
    }

    // Actors --------------------------------------------------

    private fun addLbls() {
        addActors(lblTitle, lblSumma, lblDo, lblPercent, lblPlus)
        lblTitle.apply {
            setBounds(24f, 326f, 351f, 50f)
            setAlignment(Align.center)
        }
        lblSumma.apply {
            setBounds(191f, 250f, 185f, 50f)
            setAlignment(Align.right)
        }
        lblDo.apply {
            setBounds(203f, 175f, 173f, 50f)
            setAlignment(Align.right)
        }
        lblPercent.apply {
            setBounds(310f, 100f, 66f, 50f)
            setAlignment(Align.right)
        }
        lblPlus.apply {
            setBounds(211f, 24f, 165f, 50f)
            setAlignment(Align.right)
        }
    }

}