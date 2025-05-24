package com.padrestoranom.easypaydonalds.game.actors.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.padrestoranom.easypaydonalds.game.actors.AItem
import com.padrestoranom.easypaydonalds.game.actors.AMenu
import com.padrestoranom.easypaydonalds.game.actors.button.AButton
import com.padrestoranom.easypaydonalds.game.actors.button.ATextButton
import com.padrestoranom.easypaydonalds.game.screens.AddScreen
import com.padrestoranom.easypaydonalds.game.screens.EditScreen
import com.padrestoranom.easypaydonalds.game.screens.HistoryScreen
import com.padrestoranom.easypaydonalds.game.screens.MenuScreen
import com.padrestoranom.easypaydonalds.game.utils.*
import com.padrestoranom.easypaydonalds.game.utils.actor.animDelay
import com.padrestoranom.easypaydonalds.game.utils.actor.animHide
import com.padrestoranom.easypaydonalds.game.utils.actor.animShow
import com.padrestoranom.easypaydonalds.game.utils.actor.setOnClickListener
import com.padrestoranom.easypaydonalds.game.utils.advanced.AdvancedMainGroup
import com.padrestoranom.easypaydonalds.game.utils.font.FontParameter

class AMainEdit(
    override val screen: EditScreen,
    val aMenu: AMenu,
): AdvancedMainGroup() {

    private val valueIncome  = gdxGame.ds_ItemData.flow.value.filter { it.isIncome }.sumOf { it.summa }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font31        = screen.fontGenerator_Medium.generateFont(fontParameter.setSize(31))
    private val font53r       = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(53))

    private val ls31   = Label.LabelStyle(font31, GameColor.black_1B)
    private val ls31BLU   = Label.LabelStyle(font31, GameColor.blue_02)
    private val ls53rw = Label.LabelStyle(font53r, Color.WHITE)

    private val imgPanel = Image(gdxGame.assetsAll.FRAME)
    private val btnHome  = ATextButton(screen, "Dışarı çık.", ls53rw, AButton.Type.Blue)
    private val aItem    = AItem(screen, EditScreen.EDIT_ITEM!!, ls31)
    private val lblSumma = Label(valueIncome.toSeparateWithSymbol(' ') + " ₺", ls31BLU)
    private val lblDate  = Label(EditScreen.EDIT_ITEM!!.date, ls31BLU)

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addPanel()
        addBtnHome()

        animShowMain()

        handlerAMenu()
    }

    override fun dispose() {
        Gdx.input.setOnscreenKeyboardVisible(false)
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(33f, 1023f, 767f, 570f)

        addActor(aItem)
        aItem.setBounds(66f, 1414f, 700f, 144f)

        addActor(lblSumma)
        lblSumma.setBounds(621f, 1334f, 143f, 36f)
        lblSumma.setAlignment(Align.right)

        addActor(lblDate)
        lblDate.setBounds(66f, 1195f, 134f, 45f)

        val aDelete = Actor()
        val aEdit   = Actor()

        addActors(aDelete, aEdit)
        aDelete.setBounds(44f, 1057f, 226f, 99f)
        aEdit.setBounds(475f, 1057f, 313f, 99f)

        aDelete.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.ds_ItemData.update {
                val mList = it.toMutableList()
                mList.remove(EditScreen.EDIT_ITEM)
                mList
            }
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
        aEdit.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.ds_ItemData.update {
                val mList = it.toMutableList()
                mList.remove(EditScreen.EDIT_ITEM)
                mList
            }
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AddScreen::class.java.name)
            }
        }
    }

    private fun addBtnHome() {
        addActor(btnHome)
        btnHome.setBounds(44f, 255f, 744f, 111f)

        btnHome.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic -----------------------------------------------

    private fun handlerAMenu() {
        aMenu.blockHome = {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
        aMenu.blockPlus = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AddScreen::class.java.name)
            }
        }
        aMenu.blockHistory = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name)
            }
        }
    }

}