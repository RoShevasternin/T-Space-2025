package com.ogonechek.putinvestor.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Styles
import com.ogonechek.putinvestor.game.data.InvestData
import com.ogonechek.putinvestor.game.dataStore.DS_Invest
import com.ogonechek.putinvestor.game.screens.PlusScreen
import com.ogonechek.putinvestor.game.utils.actor.setBounds
import com.ogonechek.putinvestor.game.utils.actor.setOnClickListener
import com.ogonechek.putinvestor.game.utils.addMonthsToDate
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedGroup
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.gdxGame
import com.ogonechek.putinvestor.game.utils.getMonthWord
import com.ogonechek.putinvestor.game.utils.toSeparate
import com.ogonechek.putinvestor.util.log

class AInvest(
    override val screen: AdvancedScreen,
    val invest: InvestData,
    val lsB_58: Label.LabelStyle,
    val lsR_80: Label.LabelStyle,
    val lsR_58: Label.LabelStyle,
    val lsR_43: Label.LabelStyle,
): AdvancedGroup() {

    private val dohod: Int = (invest.summa * (invest.percent / 100f) / invest.period).toInt()

    private val main       = Image(gdxGame.assetsAll.inest)
    private val lblName    = Label(invest.dName, lsB_58)
    private val lblPercent = Label("${invest.percent}%", lsR_80)
    private val lblDohod   = Label("${dohod.toSeparate()} ₽", lsR_80)
    private val lblSumma   = Label("${invest.summa.toSeparate()} ₽", lsR_80)
    private val lblSrok    = Label("${invest.period} ${getMonthWord(invest.period)}", lsR_58)
    private val lblDo      = Label("До ${addMonthsToDate(invest.period)}", lsR_43)

    private val aDelete = Actor()
    private val aEdit   = Actor()

    override fun addActorsOnGroup() {
        addAndFillActor(main)
        addLbls()
        addBtns()
    }

    // Actors ----------------------------------------------------------------

    private fun addLbls() {
        addActors(lblName, lblPercent, lblDohod, lblSumma, lblSrok, lblDo)
        lblName.apply {
            setBounds(474f, 470f, 305f, 42f)
            setAlignment(Align.center)
        }
        lblPercent.apply {
            setBounds(87f, 265f, 152f, 58f)
            setAlignment(Align.center)
        }
        lblDohod.apply {
            setBounds(341f, 255f, 347f, 58f)
            setAlignment(Align.left)
        }
        lblSumma.apply {
            setBounds(269f, 112f, 387f, 58f)
            setAlignment(Align.left)
        }
        lblSrok.apply {
            setBounds(850f, 264f, 299f, 42f)
            setAlignment(Align.left)
        }
        lblDo.apply {
            setBounds(856f, 125f, 302f, 32f)
            setAlignment(Align.left)
        }
    }

    private fun addBtns() {
        addActors(aDelete, aEdit)
        aDelete.apply {
            setBounds(22f, 433f, 128f, 116f)
            setOnClickListener(gdxGame.soundUtil) {
                gdxGame.ds_Invest.update {
                    val mList = it.toMutableList()
                    mList.remove(invest)
                    mList
                }
                gdxGame.navigationManager.navigate(screen::class.java.name)
            }
        }
        aEdit.apply {
            setBounds(1096f, 433f, 128f, 116f)
            setOnClickListener(gdxGame.soundUtil) {
                gdxGame.ds_Invest.update {
                    val mList = it.toMutableList()
                    mList.remove(invest)
                    mList
                }
                gdxGame.navigationManager.navigate(PlusScreen::class.java.name, screen::class.java.name)
            }
        }
    }

}