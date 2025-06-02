package com.rugosbon.pybonesrus.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.rugosbon.pybonesrus.game.actors.AItem
import com.rugosbon.pybonesrus.game.actors.AScrollPane
import com.rugosbon.pybonesrus.game.actors.ATmpGroup
import com.rugosbon.pybonesrus.game.actors.autoLayout.AVerticalGroup
import com.rugosbon.pybonesrus.game.actors.button.AButton
import com.rugosbon.pybonesrus.game.screens.MenuScreen
import com.rugosbon.pybonesrus.game.screens.NewItemScreen
import com.rugosbon.pybonesrus.game.screens.StatisticScreen
import com.rugosbon.pybonesrus.game.screens.TestSelectScreen
import com.rugosbon.pybonesrus.game.utils.Acts
import com.rugosbon.pybonesrus.game.utils.Block
import com.rugosbon.pybonesrus.game.utils.GameColor
import com.rugosbon.pybonesrus.game.utils.TIME_ANIM_SCREEN
import com.rugosbon.pybonesrus.game.utils.actor.animDelay
import com.rugosbon.pybonesrus.game.utils.actor.animHide
import com.rugosbon.pybonesrus.game.utils.actor.animShow
import com.rugosbon.pybonesrus.game.utils.actor.setBounds
import com.rugosbon.pybonesrus.game.utils.actor.setOnClickListener
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedMainGroup
import com.rugosbon.pybonesrus.game.utils.font.FontParameter
import com.rugosbon.pybonesrus.game.utils.gdxGame
import com.rugosbon.pybonesrus.game.utils.toSeparateWithSymbol

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

    private val valueIncome  = gdxGame.ds_ItemData.flow.value.filter { it.isDohod }.sumOf { it.summa }
    private val valueExpense = gdxGame.ds_ItemData.flow.value.filter { it.isDohod.not() }.sumOf { it.summa }
    private val valueBalance = valueIncome - valueExpense

    private val listDataItem = gdxGame.ds_ItemData.flow.value.reversed()

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "₽-")
    private val font33        = screen.fontGenerator_Ruberoid_ExtraBold.generateFont(fontParameter.setSize(33))
    private val font77        = screen.fontGenerator_Ruberoid_ExtraBold.generateFont(fontParameter.setSize(77))
    private val font51        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(51))
    private val font41_Item   = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(41))
    private val font33_Item   = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(33))

    private val ls33 = Label.LabelStyle(font33, Color.WHITE)
    private val ls77 = Label.LabelStyle(font77, Color.WHITE)
    private val ls51 = Label.LabelStyle(font51, GameColor.black_21)
    private val ls41_Item = Label.LabelStyle(font41_Item, GameColor.black_21)
    private val ls33_Item = Label.LabelStyle(font33_Item, GameColor.gray_92)

    private val imgLoga     = Image(gdxGame.assetsAll.loga)
    private val imgBalanceg = Image(gdxGame.assetsAll.BALANCEG)
    private val btnPlus     = AButton(screen, AButton.Type.Plus)

    private val lblBalance = Label((valueBalance).toSeparateWithSymbol(' ') + " ₽", ls77)
    private val lblExpense = Label((valueExpense).toSeparateWithSymbol(' ') + " ₽", ls33)
    private val lblIncome  = Label((valueIncome).toSeparateWithSymbol(' ') + " ₽", ls33)

    private val lblTitle  = Label("Последние транзакции", ls51)

    private val aStatistic = Actor()
    private val aTest      = Actor()

    // Group DataItem
    private val tmpGroup        = ATmpGroup(screen)
    private val listAItem       = List(listDataItem.size) { AItem(screen, listDataItem[it], ls41_Item, ls33_Item) }
    private val aVerticalGroup  = AVerticalGroup(screen, 20f, isWrap = true)
    private val aScroll         = AScrollPane(aVerticalGroup)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgLoga()
        addBalanceg()
        addItem()
        addBtnPlus()
        addList()

        animShowMain()

        addActors(aStatistic, aTest)
        aStatistic.apply {
            setBounds(45f, 1091f, 879f, 119f)
            setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(StatisticScreen::class.java.name, screen::class.java.name)
                }
            }
        }
        aTest.apply {
            setBounds(45f, 908f, 879f, 119f)
            setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(TestSelectScreen::class.java.name, screen::class.java.name)
                }
            }
        }

    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoga() {
        addActor(imgLoga)
        imgLoga.setBounds(65f, 1911f, 354f, 129f)
    }

    private fun addBalanceg() {
        addActor(imgBalanceg)
        imgBalanceg.setBounds(0f, 892f, 970f, 971f)

        addActors(lblBalance, lblExpense, lblIncome)
        lblBalance.setBounds(314f, 1530f, 336f, 108f)
        lblExpense.setBounds(237f, 1335f, 120f, 46f)
        lblIncome.setBounds(657f, 1335f, 128f, 46f)

        lblBalance.setAlignment(Align.center)
        lblExpense.setAlignment(Align.center)
        lblIncome.setAlignment(Align.center)
    }

    private fun addBtnPlus() {
        addActor(btnPlus)
        btnPlus.setBounds(752f, 61f, 151f, 162f)

        btnPlus.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(NewItemScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addList() {
        addActor(lblTitle)
        lblTitle.setBounds(45f, 765f, 641f, 78f)
    }

    private fun addItem() {
        addActor(tmpGroup)
        tmpGroup.setBounds(45f, 0f, 880f, 722f)
        tmpGroup.zIndex = 0

        tmpGroup.addAndFillActor(aScroll)

        aVerticalGroup.setSize(880f, 722f)

        listAItem.forEachIndexed { index, item ->
            item.setSize(879f, 221f)
            aVerticalGroup.addActor(item)
        }

    }


    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}