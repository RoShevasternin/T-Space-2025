package com.baleru.gamanecpidec.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.baleru.gamanecpidec.game.actors.progress.AProgress
import com.baleru.gamanecpidec.game.utils.GameColor
import com.baleru.gamanecpidec.game.utils.SizeScaler
import com.baleru.gamanecpidec.game.utils.WIDTH_UI
import com.baleru.gamanecpidec.game.utils.actor.setBoundsScaled
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedGroup
import com.baleru.gamanecpidec.game.utils.advanced.AdvancedScreen
import com.baleru.gamanecpidec.game.utils.font.FontParameter
import com.baleru.gamanecpidec.game.utils.gdxGame
import com.baleru.gamanecpidec.game.utils.toSeparateWithSymbol
import com.baleru.gamanecpidec.util.log

class ATop(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font113       = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(105))
    private val font33        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(33))

    private val ls113 = Label.LabelStyle(font113, Color.WHITE)
    private val ls33  = Label.LabelStyle(font33, Color.WHITE)

    private val lblBalance = Label("50 000₽", ls113)
    private val lblDesc    = Label("Потрачено 50 000/100 000 за апрель", ls33)

    private val imgBalance = Image(gdxGame.assetsAll.bnc)
    private val progress   = AProgress(screen)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(GameColor.blue_06)))

        addActors(lblBalance, lblDesc)
        lblBalance.setBoundsScaled(sizeScaler, 35f, 260f, 467f, 79f)
        lblDesc.setBoundsScaled(sizeScaler, 35f, 137f, 613f, 23f)

        addActor(imgBalance)
        imgBalance.setBoundsScaled(sizeScaler, 687f, 268f, 163f, 72f)

        addActor(progress)
        progress.setBoundsScaled(sizeScaler, 35f, 188f, 815f, 44f)
    }

    fun updateBalance(all: Int, income: Int, expense: Int) {
//        lblSummaGelir.setText(income.toSeparateWithSymbol(' ') + " ₺")
//        lblSummaHarcamalar.setText(expense.toSeparateWithSymbol(' ') + " ₺")
//
//        log("d = ${((income.toFloat() / all.toFloat()) * 100f)} $income $all $expense")
//
//        progressGelir.progressPercentFlow.value = (income.toFloat() / all.toFloat()) * 100f
//        progressHarcamalar.progressPercentFlow.value = (expense.toFloat() / all.toFloat()) * 100f
    }

}