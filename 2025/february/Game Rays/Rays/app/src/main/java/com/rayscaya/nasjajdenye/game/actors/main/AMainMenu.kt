package com.rayscaya.nasjajdenye.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.rayscaya.nasjajdenye.game.actors.APaneltem
import com.rayscaya.nasjajdenye.game.actors.ARectItem
import com.rayscaya.nasjajdenye.game.actors.autoLayout.AHorizontalGroup
import com.rayscaya.nasjajdenye.game.actors.button.AButton
import com.rayscaya.nasjajdenye.game.screens.MenuScreen
import com.rayscaya.nasjajdenye.game.utils.Block
import com.rayscaya.nasjajdenye.game.utils.GameColor
import com.rayscaya.nasjajdenye.game.utils.TIME_ANIM_SCREEN
import com.rayscaya.nasjajdenye.game.utils.actor.animDelay
import com.rayscaya.nasjajdenye.game.utils.actor.animHide
import com.rayscaya.nasjajdenye.game.utils.actor.animShow
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedMainGroup
import com.rayscaya.nasjajdenye.game.utils.font.FontParameter
import com.rayscaya.nasjajdenye.game.utils.gdxGame
import com.rayscaya.nasjajdenye.game.actors.AScrollPane
import com.rayscaya.nasjajdenye.game.actors.autoLayout.AVerticalGroup
import com.rayscaya.nasjajdenye.game.screens.EditScreen
import com.rayscaya.nasjajdenye.game.utils.actor.animMoveTo
import com.rayscaya.nasjajdenye.game.utils.actor.disable
import com.rayscaya.nasjajdenye.game.utils.actor.enable
import com.rayscaya.nasjajdenye.game.utils.actor.setOnClickListener

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

    private val listDataInput = gdxGame.ds_DataInput.flow.value.shuffled()

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val font54 = screen.fontGenerator_Regular.generateFont(parameter.setSize(54))
    private val font39 = screen.fontGenerator_Regular.generateFont(parameter.setSize(39))
    private val font29 = screen.fontGenerator_Regular.generateFont(parameter.setSize(29))

    private val ls54 = Label.LabelStyle(font54, GameColor.black_2E)
    private val ls39 = Label.LabelStyle(font39, GameColor.green_42)
    private val ls29 = Label.LabelStyle(font29, GameColor.green_43)

    private val btnMiniEdit = AButton(screen, AButton.Type.MiniEdit)
    private val lblTitle    = Label("Активные инвестиции", ls54)

    private val horizontalGroup = AHorizontalGroup(screen, 36f, isWrapHorizontal = true)
    private val scroll          = AScrollPane(horizontalGroup)
    private val listARectItem   = List(listDataInput.size) { ARectItem(screen, listDataInput[it], ls39) }

    private val btnBack = AButton(screen, AButton.Type.Back)

    private val verticalGroup   = AVerticalGroup(screen, 49f, isWrap = true)
    private val scrollV         = AScrollPane(verticalGroup)
    private val listAPanelItem  = List(listDataInput.size) { APaneltem(screen, listDataInput[it], ls29, ls39) }

    override fun addActorsOnGroup() {
        screen.topStageBack.root.color.a = 0f
        color.a = 0f

        addBtnMiniEdit()
        addLblTitle()
        addScroll()

        addBtnBack()
        addScrollV()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnMiniEdit() {
        addActor(btnMiniEdit)
        btnMiniEdit.setBounds(816f, 1214f, 88f, 88f)

        btnMiniEdit.setOnClickListener {
            animShowBigInvest()
        }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(50f, 1238f, 601f, 38f)
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 788f, 928f, 402f)

        horizontalGroup.apply {
            setSize(928f, 402f)
            listARectItem.onEach { aRectItem ->
                aRectItem.setSize(402f, 402f)
                addActor(aRectItem)
            }
        }
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(49f, 1219f, 75f, 75f)
        btnBack.color.a = 0f
        btnBack.disable()

        btnBack.setOnClickListener { animHideBigInvest() }
    }

    private fun addScrollV() {
        addActor(scrollV)
        scrollV.setBounds(48f, 0f, 831f, 1153f)
        scrollV.color.a = 0f
        scrollV.disable()
        scrollV.setScrollingDisabled(true, false)

        verticalGroup.apply {
            setSize(831f, 1153f)
            listAPanelItem.onEach { aPanelItem ->
                aPanelItem.setSize(830f, 411f)
                addActor(aPanelItem)

                aPanelItem.blockDelete = { dataInput ->
                    gdxGame.ds_DataInput.update { list ->
                        val mList = list.toMutableList()
                        mList.remove(dataInput)
                        mList
                    }

                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
                aPanelItem.blockEdit = { dataInput ->
                    gdxGame.ds_DataInput.update { list ->
                        val mList = list.toMutableList()
                        mList.remove(dataInput)
                        mList
                    }

                    AMainEdit.GLOBAL_editDataInput = dataInput

                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(EditScreen::class.java.name, screen::class.java.name)
                    }
                }
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

    private fun animShowBigInvest() {
        btnBack.enable()
        btnBack.animShow(TIME_ANIM_SCREEN)

        btnMiniEdit.disable()
        btnMiniEdit.animHide(TIME_ANIM_SCREEN)

        lblTitle.animMoveTo(148f, 1238f, TIME_ANIM_SCREEN)

        scrollV.enable()
        scrollV.animShow(TIME_ANIM_SCREEN)

        scroll.disable()
        scroll.animHide(TIME_ANIM_SCREEN)
    }

    private fun animHideBigInvest() {
        btnBack.disable()
        btnBack.animHide(TIME_ANIM_SCREEN)

        btnMiniEdit.enable()
        btnMiniEdit.animShow(TIME_ANIM_SCREEN)

        lblTitle.animMoveTo(49f, 1238f, TIME_ANIM_SCREEN)

        scroll.enable()
        scroll.animShow(TIME_ANIM_SCREEN)

        scrollV.disable()
        scrollV.animHide(TIME_ANIM_SCREEN)
    }

}