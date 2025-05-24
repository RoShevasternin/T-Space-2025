package com.startegfin.financester.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.TypingLabel
import com.startegfin.financester.game.LibGDXGame
import com.startegfin.financester.game.actors.AButton
import com.startegfin.financester.game.actors.ATransaction
import com.startegfin.financester.game.actors.TmpGroup
import com.startegfin.financester.game.actors.layout.AVerticalGroup
import com.startegfin.financester.game.actors.layout.Layout
import com.startegfin.financester.game.actors.progress.AGreenProgress
import com.startegfin.financester.game.global.Balance
import com.startegfin.financester.game.global.toBalance
import com.startegfin.financester.game.utils.GColor
import com.startegfin.financester.game.utils.TIME_ANIM
import com.startegfin.financester.game.utils.actor.animHide
import com.startegfin.financester.game.utils.actor.animShow
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen
import com.startegfin.financester.game.utils.advanced.AdvancedStage
import com.startegfin.financester.game.utils.disable
import com.startegfin.financester.game.utils.font.FontParameter

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirstShow = true
    }

    private val fontParameter   = FontParameter()
    private val fontExtraBold88 = fontGenerator_NunitoSans_10pt_ExtraBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "-₽").setSize(88))
    private val fontMedium24    = fontGenerator_NunitoSans_10pt_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(24))
    private val fontBold24      = fontGenerator_NunitoSans_10pt_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "₽").setSize(24))
    private val fontSemiBold32  = fontGenerator_NunitoSans_10pt_SemiBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC_ALL.chars + "₽").setSize(32))
    private val fontSemiBold24  = fontGenerator_NunitoSans_10pt_SemiBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + ".").setSize(24))

    private val lsExtraBold88 = Label.LabelStyle(fontExtraBold88, Color.WHITE)
    private val lsSemiBold32  = Label.LabelStyle(fontSemiBold32, GColor.text)
    private val lsSemiBold24  = Label.LabelStyle(fontSemiBold24, GColor.text.cpy().apply { a = 0.5f })

    private val nameFontLight = "Light"
    private val nameFontBold  = "ExtraBold"

    private val fontMediumBold = Font().setFamily(Font.FontFamily(arrayOf(
        Font(fontMedium24).setName(nameFontLight),
        Font(fontBold24).setName(nameFontBold),
    )))

    private val textUsed = "[@$nameFontLight]Использовано: [@$nameFontBold]${Balance.balanceUsed.toBalance} ₽ [@$nameFontLight]из[@$nameFontBold] ${Balance.balance.toBalance} ₽"

    // Actors

    private val imgLogo  = Image(game.all.logo)
    private val tmpGroup = TmpGroup(this)

    private val imgMainPanel  = Image(game.all.MAIN_PANELS)
    private val lblBalance    = Label((Balance.balance - Balance.balanceUsed).toBalance + " ₽", lsExtraBold88)
    private val lblUsed       = TypingLabel(textUsed, fontMediumBold, GColor.text)
    private val progressGreen = AGreenProgress(this)

    private val btnRAndD = AButton(this, AButton.Static.Type.Ros_and_Doh)
    private val btnAllTr = AButton(this, AButton.Static.Type.AllTrans)
    private val btnPlus  = AButton(this, AButton.Static.Type.Plus)

    private val verticalGroup = AVerticalGroup(this,20f,
        alignmentH = Layout.AlignmentHorizontal.CENTER
    )

    private val allListRoshodAndDohod = (game.dataStoreRoshodUtil.roshodListFlow.value + game.dataStoreDohodUtil.dohodListFlow.value).sortedBy { it.uid }
    private val last_3_Transaction    = allListRoshodAndDohod.takeLast(3).reversed()
    private val listTransaction       = List(last_3_Transaction.size) { ATransaction(
        this,
        last_3_Transaction[it],
        last_3_Transaction[it].getType(),
        lsSemiBold32, lsSemiBold24
    ) }

    override fun show() {
        if (isFirstShow) imgLogo.color.a = 0f
        super.show()
    }

    override fun dispose() {
        super.dispose()
        fontMediumBold.dispose()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgLogo)
        imgLogo.setBounds(40f,1439f,321f,81f)
        if (isFirstShow) {
            isFirstShow = false
            imgLogo.animShow(TIME_ANIM)
        }

        addAndFillActors(tmpGroup)
        tmpGroup.apply {
            color.a = 0f

            addImgMainPanel()
            addLblBalance()
            addLblUsed()
            addProgressGreen()
            addBtnRosAndDoh()
            addBtnAllTrans()
            addBtnPlus()

            addVerticalGroup()

            animShow(TIME_ANIM)
        }
    }

    private fun AdvancedGroup.addImgMainPanel() {
        addActor(imgMainPanel)
        imgMainPanel.setBounds(32f,252f,690f,1166f)
    }

    private fun AdvancedGroup.addLblBalance() {
        addActor(lblBalance)
        lblBalance.setBounds(110f,1128f,533f,88f)
    }

    private fun AdvancedGroup.addLblUsed() {
        addActor(lblUsed)
        lblUsed.setBounds(80f,979f,444f,29f)
    }

    private fun AdvancedGroup.addProgressGreen() {
        addActor(progressGreen)
        progressGreen.setBounds(80f,1029f,592f,20f)
        progressGreen.disable()

        val onePercentDohod = Balance.balance / 100f
        val percentRoshod   = (Balance.balanceUsed / onePercentDohod)
        progressGreen.animToProgress(percentRoshod)
    }

    private fun AdvancedGroup.addBtnRosAndDoh() {
        addActor(btnRAndD)
        btnRAndD.apply {
            setBounds(80f,864f,593f,75f)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(RoshodScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedGroup.addBtnAllTrans() {
        addActor(btnAllTr)
        btnAllTr.apply {
            setBounds(80f,602f,593f,75f)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AllTransScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedGroup.addBtnPlus() {
        addActor(btnPlus)
        btnPlus.apply {
            setBounds(592f,92f,122f,122f)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AddRoshodScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

    // Last Transactions ---------------------------------------------------------------------

    private fun AdvancedGroup.addVerticalGroup() {
        addActor(verticalGroup)
        verticalGroup.setBounds(80f, 300f, 593f,282f)
        verticalGroup.addLastTransactions()
    }

    private fun AVerticalGroup.addLastTransactions() {
        listTransaction.onEach { aTransaction ->
            aTransaction.setSize(592f,80f)
            addActor(aTransaction)
        }
    }

}