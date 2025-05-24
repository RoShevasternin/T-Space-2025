package com.startegfin.financester.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.startegfin.financester.game.LibGDXGame
import com.startegfin.financester.game.actors.AButton
import com.startegfin.financester.game.actors.ACubeRoshod
import com.startegfin.financester.game.actors.TmpGroup
import com.startegfin.financester.game.actors.layout.AVerticalGroup
import com.startegfin.financester.game.actors.layout.Layout
import com.startegfin.financester.game.actors.shader.AStatisticCircle
import com.startegfin.financester.game.global.Balance
import com.startegfin.financester.game.global.toBalance
import com.startegfin.financester.game.utils.GColor
import com.startegfin.financester.game.utils.TIME_ANIM
import com.startegfin.financester.game.utils.WIDTH_UI
import com.startegfin.financester.game.utils.actor.animHide
import com.startegfin.financester.game.utils.actor.animShow
import com.startegfin.financester.game.utils.actor.setOnClickListener
import com.startegfin.financester.game.utils.advanced.AdvancedGroup
import com.startegfin.financester.game.utils.advanced.AdvancedScreen
import com.startegfin.financester.game.utils.advanced.AdvancedStage
import com.startegfin.financester.game.utils.font.FontParameter

class RoshodScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter   = FontParameter()
    private val fontExtraBold64 = fontGenerator_NunitoSans_10pt_ExtraBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "₽").setSize(64))
    private val fontExtraBold32 = fontGenerator_NunitoSans_10pt_ExtraBold.generateFont(fontParameter.setCharacters("Потрачено").setSize(32))
    private val fontSemiBold32  = fontGenerator_NunitoSans_10pt_SemiBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC_ALL.chars + "₽").setSize(32))
    private val fontSemiBold24  = fontGenerator_NunitoSans_10pt_SemiBold.generateFont(fontParameter.setCharacters("сентябрь 2024").setSize(24))
    private val fontBold36      = fontGenerator_NunitoSans_10pt_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "₽").setSize(36))

    private val lsExtraBold64 = Label.LabelStyle(fontExtraBold64, GColor.text)
    private val lsExtraBold32 = Label.LabelStyle(fontExtraBold32, GColor.text)
    private val lsSemiBold24  = Label.LabelStyle(fontSemiBold24, GColor.text.cpy().apply { a = 0.5f })
    private val lsSemiBold32  = Label.LabelStyle(fontSemiBold32, GColor.text)
    private val lsBold36      = Label.LabelStyle(fontBold36, GColor.red)

    // Actors

    private val imgLogo  = Image(game.all.logo)
    private val tmpGroup = TmpGroup(this)

    private val imgMinusPanel = Image(game.all.MINUS_PANEL)
    private val imgEllips     = Image(game.all.ellips)
    private val imgSeptember  = Image(game.all.september).apply { color.a = 0f }
    private val imgBox        = Image(game.all.roshod)
    private val btnBack       = AButton(this, AButton.Static.Type.Back)
    private val aDohod        = Actor()

    private val lblBalance    = Label(Balance.balanceUsed.toBalance + " ₽", lsExtraBold64)
    private val lblBalanceRed = Label(Balance.balanceUsed.toBalance + " ₽", lsBold36)
    private val lblPotracheno = Label("Потрачено", lsExtraBold32)
    private val lblMesac      = Label("сентябрь 2024", lsSemiBold24)

    private val onePercentBalanceUsed = Balance.balanceUsed / 100f
    private val listColorIndex        = game.dataStoreRoshodUtil.roshodListFlow.value.map { it.categoryIndex }.distinct().sorted().take(6)
    private val listPercents          = List<Float>(listColorIndex.size) { index -> (game.dataStoreRoshodUtil.roshodListFlow.value.filter { it.categoryIndex == listColorIndex[index] }.sumOf { it.suma } / onePercentBalanceUsed) }

    private val listCubeImage = List(listColorIndex.size) { index -> ACubeRoshod(
        this,
        game.all.listColors[index],
        game.listCategoryRoshod[listColorIndex[index]],
        listPercents[index].toInt(),
        game.dataStoreRoshodUtil.roshodListFlow.value.filter { it.categoryIndex == listColorIndex[index] }.sumOf { it.suma },
        lsSemiBold32
    ) }

    private val verticalGroup = AVerticalGroup(this,40f,
        alignmentH = Layout.AlignmentHorizontal.CENTER
    )

    private val statisticColors   = GColor.statisticRoshodColors.take(listColorIndex.size)
    private val statisticPercents = listPercents.toFloatArray()
    private val statisticCircle = AStatisticCircle(this, statisticColors, statisticPercents)

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgLogo)
        imgLogo.setBounds(40f,1439f,321f,81f)

        addAndFillActors(tmpGroup)
        tmpGroup.apply {
            color.a = 0f

            addStatisticCircle()
            addImgEllips()

            addImgMinusPanel()
            addImgSeptember()
            addImgBox()
            addBtnBack()
            addADohod()

            addLblBalanceAndLbls()

            addVerticalGroup()

            animShow(TIME_ANIM)
        }
    }

    private fun AdvancedGroup.addStatisticCircle() {
        addActor(statisticCircle)
        statisticCircle.setBounds(156f,857f,442f,442f)
    }

    private fun AdvancedGroup.addImgEllips() {
        addActor(imgEllips)
        imgEllips.setBounds(185f,886f,385f,385f)
    }

    private fun AdvancedGroup.addImgMinusPanel() {
        addActor(imgMinusPanel)
        imgMinusPanel.setBounds(32f,75f,690f,623f)
    }

    private fun AdvancedGroup.addImgSeptember() {
        addActor(imgSeptember)
        imgSeptember.setBounds(156f,768f,443f,49f)
    }

    private fun AdvancedGroup.addImgBox() {
        addActor(imgBox)
        imgBox.setBounds(199f,1333f,357f,81f)
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

    private fun AdvancedGroup.addADohod() {
        addActor(aDohod)
        aDohod.apply {
            setBounds(386f,1344f,166f,59f)
            setOnClickListener(game.soundUtil) {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(DohodScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedGroup.addLblBalanceAndLbls() {
        addActor(lblBalance)
        lblBalance.setAlignment(Align.center)
        lblBalance.setBounds(225f,1032f,306f,77f)

        addActors(lblPotracheno, lblMesac, lblBalanceRed)
        lblPotracheno.apply {
            setAlignment(Align.center)
            setBounds(225f,1109f,306f,39f)
        }
        lblMesac.apply {
            setAlignment(Align.center)
            setBounds(225f,1003f,306f,29f)
        }
        lblBalanceRed.apply {
            setAlignment(Align.right)
            setBounds(500f,605f,172f,43f)
        }
    }

    private fun AdvancedGroup.addVerticalGroup() {
        addActor(verticalGroup)
        verticalGroup.setBounds(80f,122f,593f,443f)
        verticalGroup.addListCubeImage()
    }

    private fun AVerticalGroup.addListCubeImage() {
        listCubeImage.onEach { cube ->
            cube.setSize(592f,40f)
            addActor(cube)
        }
    }

}