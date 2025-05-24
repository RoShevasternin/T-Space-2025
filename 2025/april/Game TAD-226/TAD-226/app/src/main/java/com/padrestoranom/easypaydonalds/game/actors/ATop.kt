package com.padrestoranom.easypaydonalds.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.padrestoranom.easypaydonalds.game.actors.progress.AProgress
import com.padrestoranom.easypaydonalds.game.utils.GameColor
import com.padrestoranom.easypaydonalds.game.utils.SizeScaler
import com.padrestoranom.easypaydonalds.game.utils.WIDTH_UI
import com.padrestoranom.easypaydonalds.game.utils.actor.setBoundsScaled
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedGroup
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedScreen
import com.padrestoranom.easypaydonalds.game.utils.font.FontParameter
import com.padrestoranom.easypaydonalds.game.utils.gdxGame
import com.padrestoranom.easypaydonalds.game.utils.toSeparateWithSymbol
import com.padrestoranom.easypaydonalds.util.log
import kotlin.math.exp

class ATop(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font35        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(35))

    private val ls35 = Label.LabelStyle(font35, GameColor.black_1B)

    private val lblTitleGelir      = Label("Gelir", ls35)
    private val lblTitleHarcamalar = Label("Harcamalar", ls35)

    private val lblSummaGelir      = Label("0 ₺", ls35)
    private val lblSummaHarcamalar = Label("0 ₺", ls35)

    private val imgGelir      = Image(gdxGame.assetsAll.grau)
    private val imgHarcamalar = Image(gdxGame.assetsAll.grau)

    private val progressGelir      = AProgress(screen, gdxGame.assetsAll.blur)
    private val progressHarcamalar = AProgress(screen, gdxGame.assetsAll.red)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(Color.WHITE)))

        addActors(lblTitleGelir, lblTitleHarcamalar)
        lblTitleGelir.setBoundsScaled(sizeScaler, 33f, 353f, 73f, 42f)
        lblTitleHarcamalar.setBoundsScaled(sizeScaler, 33f, 158f, 73f, 42f)

        addActors(lblSummaGelir, lblSummaHarcamalar)
        lblSummaGelir.setBoundsScaled(sizeScaler, 33f, 235f, 73f, 42f)
        lblSummaHarcamalar.setBoundsScaled(sizeScaler, 33f, 40f, 73f, 42f)

        addActors(imgGelir, imgHarcamalar)
        imgGelir.setBoundsScaled(sizeScaler, 33f, 295f, 767f, 40f)
        imgHarcamalar.setBoundsScaled(sizeScaler, 33f, 100f, 767f, 40f)

        addActors(progressGelir, progressHarcamalar)
        progressGelir.setBoundsScaled(sizeScaler, 33f, 295f, 767f, 40f)
        progressHarcamalar.setBoundsScaled(sizeScaler, 33f, 100f, 767f, 40f)

        //progressGelir.progressPercentFlow.value = 0f
        //progressHarcamalar.progressPercentFlow.value = 0f
    }

    fun updateBalance(all: Int, income: Int, expense: Int) {
        lblSummaGelir.setText(income.toSeparateWithSymbol(' ') + " ₺")
        lblSummaHarcamalar.setText(expense.toSeparateWithSymbol(' ') + " ₺")

        log("d = ${((income.toFloat() / all.toFloat()) * 100f)} $income $all $expense")

        progressGelir.progressPercentFlow.value = (income.toFloat() / all.toFloat()) * 100f
        progressHarcamalar.progressPercentFlow.value = (expense.toFloat() / all.toFloat()) * 100f
    }

}