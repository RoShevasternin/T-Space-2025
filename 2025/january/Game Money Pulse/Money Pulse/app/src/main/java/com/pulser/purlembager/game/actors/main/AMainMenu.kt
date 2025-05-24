package com.pulser.purlembager.game.actors.main

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.pulser.purlembager.game.actors.AAllDesire
import com.pulser.purlembager.game.actors.AAllList
import com.pulser.purlembager.game.actors.ADialogMenu
import com.pulser.purlembager.game.actors.AHomeList
import com.pulser.purlembager.game.actors.AMiniMenu
import com.pulser.purlembager.game.actors.APanel
import com.pulser.purlembager.game.actors.AStatistic
import com.pulser.purlembager.game.dataStore.DataTransactionType
import com.pulser.purlembager.game.screens.AddScreen
import com.pulser.purlembager.game.screens.BlogScreen
import com.pulser.purlembager.game.screens.MenuScreen
import com.pulser.purlembager.game.utils.Block
import com.pulser.purlembager.game.utils.GameColor
import com.pulser.purlembager.game.utils.TIME_ANIM_SCREEN
import com.pulser.purlembager.game.utils.actor.animHide
import com.pulser.purlembager.game.utils.actor.animHideSuspend
import com.pulser.purlembager.game.utils.actor.animShow
import com.pulser.purlembager.game.utils.actor.animShowSuspend
import com.pulser.purlembager.game.utils.actor.disable
import com.pulser.purlembager.game.utils.actor.enable
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.font.FontParameter
import com.pulser.purlembager.game.utils.gdxGame
import com.pulser.purlembager.game.utils.runGDX
import com.pulser.purlembager.game.utils.toSeparate
import com.pulser.purlembager.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainMenu(
    override val screen: AdvancedScreen,
    val dialogMenu: ADialogMenu,
    val aPanel    : APanel,
    val aAllList  : AAllList,
): AdvancedGroup() {

    private val screenMenu = screen as MenuScreen

    private val allIncome  = gdxGame.ds_Transaction.flow.value.filter { it.type == DataTransactionType.Income }
    private val allExpense = gdxGame.ds_Transaction.flow.value.filter { it.type == DataTransactionType.Expense }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font81        = screen.fontGenerator_SemiBold.generateFont(fontParameter.setSize(81))

    private val ls81 = Label.LabelStyle(font81, GameColor.black_31)

    private val lblTitle   = Label("Структура расходов", ls81)
    private val aMiniMenu  = AMiniMenu(screen)
    private val aHomeList  = AHomeList(screen)
    private val aAllDesire = AAllDesire(screen)
    private val aStatistic = AStatistic(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addLbl()
                addStatistic()
                addMiniMenu()
                addHomeList()
                addAAllDesire()

                handleAPanel()
                handleDialogMenu()
                handleAAllList()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addLbl() {
        addActor(lblTitle)
        lblTitle.setBounds(143f, 2302f, 889f, 104f)
    }

    private fun addStatistic() {
        addActor(aStatistic)
        aStatistic.setBounds(255f, 1507f, 665f, 665f)
    }

    private fun addMiniMenu() {
        addActor(aMiniMenu)
        aMiniMenu.setBounds(297f, 1250f, 798f, 95f)

        aMiniMenu.apply {
            blockMain = {
                hideAAllDesire()
            }
            blockDesire = {
                showAAllDesire()
            }
            blockAll = {
                showAAllList()
            }
        }
    }

    private fun addHomeList() {
        addActor(aHomeList)
        aHomeList.setBounds(0f, 0f, 1176f, 1251f)

        aHomeList.apply {
            if (gdxGame.ds_Transaction.flow.value.isNotEmpty()) {
                val randomCoff = (10..85).random() / 100f

                lblY.setText("₽ " + allIncome.sumOf { it.tSumma }.toSeparate())
                lblG.setText("₽ " + allExpense.sumOf { it.tSumma }.toSeparate())
                lblB.setText("₽ " + (allExpense.sumOf { it.tSumma } * randomCoff).toInt().toSeparate())

                progY.progressPercentFlow.value = (50..100).random().toFloat()
                progG.progressPercentFlow.value = (0..(100 - progY.progressPercentFlow.value.toInt())).random().toFloat()
                progB.progressPercentFlow.value = 100 - (progY.progressPercentFlow.value + progG.progressPercentFlow.value)

                aStatistic.update(
                    floatArrayOf(
                        progY.progressPercentFlow.value,
                        progG.progressPercentFlow.value,
                        progB.progressPercentFlow.value,
                    )
                )
            } else {
                lblY.setText("₽ 0")
                lblG.setText("₽ 0")
                lblB.setText("₽ 0")

                progY.progressPercentFlow.value = 0f
                progG.progressPercentFlow.value = 0f
                progB.progressPercentFlow.value = 0f

                aStatistic.update(floatArrayOf(50f, 25f, 25f))
            }

        }
    }

    private fun addAAllDesire() {
        addActor(aAllDesire)
        aAllDesire.setBounds(1176f, 328f, 1277f, 901f)
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        animShowSuspend(TIME_ANIM_SCREEN)
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Logic ----------------------------------------------------------

    private fun handleAPanel() {
        aPanel.apply {
            blockAdd = {
                showDialogMenu()
            }
            blockBlog = {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(BlogScreen::class.java.name, AddScreen::class.java.name)
                }
            }
        }
    }

    private fun handleDialogMenu() {
        dialogMenu.apply {
            blockPlus = {
                hideDialogMenu()
            }
            blockDesire = {
                AddScreen.ADD_TYPE = AddScreen.AddType.Desire
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(AddScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
            blockIncome = {
                AddScreen.ADD_TYPE = AddScreen.AddType.Income
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(AddScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
            blockExpense = {
                AddScreen.ADD_TYPE = AddScreen.AddType.Expense
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(AddScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun handleAAllList() {
        aAllList.blockBack = { hideAAllList() }
    }

    private fun showDialogMenu() {
        screenMenu.showBackground()
        dialogMenu.animShow(TIME_ANIM_SCREEN) {
            dialogMenu.enable()
        }
    }

    private fun hideDialogMenu() {
        screenMenu.hideBackground()
        dialogMenu.animHide(TIME_ANIM_SCREEN) {
            dialogMenu.disable()
        }
    }

    private fun showAAllList() {
        screenMenu.showBackground()
        aAllList.animShow(TIME_ANIM_SCREEN) {
            aAllList.enable()
        }
        screen.stageUI.root.color.a = 0.25f
    }

    private fun hideAAllList() {
        screenMenu.hideBackground()
        aAllList.animHide(TIME_ANIM_SCREEN) {
            aAllList.disable()
        }
        screen.stageUI.root.color.a = 1f
    }

    private fun showAAllDesire() {
        aHomeList.addAction(Actions.moveTo(-1180f, 0f, TIME_ANIM_SCREEN))
        aAllDesire.addAction(Actions.moveTo(62f, 328f, TIME_ANIM_SCREEN))
    }

    private fun hideAAllDesire() {
        aHomeList.addAction(Actions.moveTo(0f, 0f, TIME_ANIM_SCREEN))
        aAllDesire.addAction(Actions.moveTo(1180f, 328f, TIME_ANIM_SCREEN))
    }

}