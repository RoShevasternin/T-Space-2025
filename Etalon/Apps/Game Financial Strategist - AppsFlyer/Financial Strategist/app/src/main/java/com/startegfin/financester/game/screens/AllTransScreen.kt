package com.startegfin.financester.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.startegfin.financester.game.LibGDXGame
import com.startegfin.financester.game.actors.AButton
import com.startegfin.financester.game.actors.ATransaction
import com.startegfin.financester.game.actors.TmpGroup
import com.startegfin.financester.game.actors.layout.AVerticalGroup
import com.startegfin.financester.game.actors.layout.Layout
import com.startegfin.financester.game.utils.GColor
import com.startegfin.financester.game.utils.TIME_ANIM
import com.startegfin.financester.game.utils.actor.animHide
import com.startegfin.financester.game.utils.actor.animShow
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen
import com.startegfin.financester.game.utils.advanced.AdvancedStage
import com.startegfin.financester.game.utils.font.FontParameter

class AllTransScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter   = FontParameter()
    private val fontExtraBold32 = fontGenerator_NunitoSans_10pt_ExtraBold.generateFont(fontParameter.setCharacters("Все транзакции").setSize(32))
    private val fontSemiBold32  = fontGenerator_NunitoSans_10pt_SemiBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC_ALL.chars + "₽").setSize(32))
    private val fontSemiBold24  = fontGenerator_NunitoSans_10pt_SemiBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + ".").setSize(24))

    private val lsExtraBold32     = Label.LabelStyle(fontExtraBold32, GColor.text)
    private val lsSemiBold32      = Label.LabelStyle(fontSemiBold32, GColor.text)
    private val lsSemiBold24      = Label.LabelStyle(fontSemiBold24, GColor.text.cpy().apply { a = 0.5f })

    // Actors

    private val imgLogo  = Image(game.all.logo)
    private val tmpGroup = TmpGroup(this)

    private val imgMainPanel = Image(game.all.ALL_ITEMS_PANEL)
    private val lblAllTrans  = Label("Все транзакции", lsExtraBold32)
    private val btnBack      = AButton(this, AButton.Static.Type.Back)


    private val verticalGroup = AVerticalGroup(this,20f, startGap = 40f, endGap = 40f,
        alignmentH = Layout.AlignmentHorizontal.CENTER, isWrap = true
    )
    private val scroll = ScrollPane(verticalGroup)

    private val allListRoshodAndDohod = (game.dataStoreRoshodUtil.roshodListFlow.value + game.dataStoreDohodUtil.dohodListFlow.value).sortedBy { it.uid }
    private val listTransaction       = List(allListRoshodAndDohod.size) { ATransaction(
        this,
        allListRoshodAndDohod[it],
        allListRoshodAndDohod[it].getType(),
        lsSemiBold32, lsSemiBold24
    ) }.reversed()

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgLogo)
        imgLogo.setBounds(40f,1439f,321f,81f)

        addAndFillActors(tmpGroup)
        tmpGroup.apply {
            color.a = 0f

            addImgMainPanel()
            addLblBalance()
            addBtnBack()

            addScroll()

            animShow(TIME_ANIM)
        }
    }

    override fun dispose() {
        super.dispose()
        verticalGroup.dispose()
    }

    private fun AdvancedGroup.addImgMainPanel() {
        addActor(imgMainPanel)
        imgMainPanel.setBounds(32f,26f,690f,1282f)
    }

    private fun AdvancedGroup.addLblBalance() {
        addActor(lblAllTrans)
        lblAllTrans.setBounds(249f,1355f,254f,39f)
    }

    private fun AdvancedGroup.addBtnBack() {
        addActor(btnBack)
        btnBack.apply {
            setBounds(36f,1334f,79f,80f)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedGroup.addScroll() {
        addActor(scroll)
        scroll.setBounds(80f,40f,593f,1259f)
        verticalGroup.width  = 593f
        verticalGroup.height = 1259f
        verticalGroup.addLastTransactions()
    }

    private fun AVerticalGroup.addLastTransactions() {
        listTransaction.onEach { aTransaction ->
            aTransaction.setSize(592f,80f)
            addActor(aTransaction)
        }
    }

}