package com.sukapital.saepital.game.screens

import android.view.WindowManager
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.sukapital.saepital.R
import com.sukapital.saepital.game.LibGDXGame
import com.sukapital.saepital.game.actors.AButton
import com.sukapital.saepital.game.actors.ADesireList
import com.sukapital.saepital.game.actors.AStatistic
import com.sukapital.saepital.game.actors.TmpGroup
import com.sukapital.saepital.game.actors.systemPanel.ADesireDialog
import com.sukapital.saepital.game.actors.systemPanel.AIncomeExpenseDialog
import com.sukapital.saepital.game.actors.systemUI.ABottom
import com.sukapital.saepital.game.actors.systemUI.AHeader
import com.sukapital.saepital.game.utils.Block
import com.sukapital.saepital.game.utils.GColor
import com.sukapital.saepital.game.utils.TIME_ANIM
import com.sukapital.saepital.game.utils.WIDTH_UI
import com.sukapital.saepital.game.utils.actor.animHide
import com.sukapital.saepital.game.utils.actor.animShow
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirstShow = true
    }

    private val summaIncome  get() = game.incomeUtil.incomeListFlow.value.sumOf { it.summa }
    private val summaExpense get() = game.expenseUtil.expenseListFlow.value.sumOf { it.summa }

    // Actors
    private val aHeader  = AHeader(this)
    private val aBottom  = ABottom(this)

    private val tmpGroup = TmpGroup(this)

    private val btnAddIncomeExpense = AButton(this, AButton.Type.AddIncomeExpense)
    private val btnSeeAll           = AButton(this, AButton.Type.SeeAll)
    private val aStatistic          = AStatistic(this, summaIncome, summaExpense)
    private val aDesirePanel        = ADesireDialog(this)
    private val aIncomeExpensePanel = AIncomeExpenseDialog(this)
    private val aDesireList         = ADesireList(this)

    override fun show() {
        coroutine?.launch(Dispatchers.Main) {
            game.activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }

        if (isFirstShow) {
            isFirstShow = false
            game.activity.apply {
                setBackgroundColor(R.color.white_e)
                game.backgroundColor = GColor.white_e
                setNavBarColor(R.color.dark)
                setStatusBarColor(R.color.green)
            }
        }

        tmpGroup.animHide()
        super.show()
        tmpGroup.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAndFillActors(tmpGroup)
        addSystemUI()
        aBottom.checkHome()

        tmpGroup.apply {
            addBtnAddIncomeExpense()
            addBtnSeeAll()
            addAStatistic()
            addDesireList()
        }

        topStageUI.apply {
            addPanels()
        }
    }

    private fun AdvancedStage.addSystemUI() {
        addActor(aHeader)
        aHeader.setBounds(0f,1552f, WIDTH_UI,240f)
        addActor(aBottom)
        aBottom.setBounds(0f,0f, WIDTH_UI,187f)

        aBottom.blockNavTo = {
            if (it == ABottom.ClickType.Blog) tmpGroup.animHide(TIME_ANIM) {
                game.navigationManager.navigate(BlogScreen::class.java.name, MainScreen::class.java.name)
            }
            if (it == ABottom.ClickType.Plus) {
                aDesirePanel.showDialog()
            }
        }
    }

    private fun AdvancedGroup.addAStatistic() {
        addActor(aStatistic)
        aStatistic.setBounds(71f,1035f,685f,473f)
    }

    private fun AdvancedGroup.addBtnAddIncomeExpense() {
        addActor(btnAddIncomeExpense)

        btnAddIncomeExpense.apply {
            setBounds(71f,823f,685f,125f)
            setOnClickListener {
                aIncomeExpensePanel.showDialog()
            }
        }
    }

    private fun AdvancedGroup.addBtnSeeAll() {
        addActor(btnSeeAll)

        val area = Actor()
        addActor(area)
        area.setBounds(161f,737f,507f,76f)

        btnSeeAll.apply {
            setBounds(295f,759f,236f,33f)
            setArea(area,false)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(HistoryScreen::class.java.name, MainScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedGroup.addDesireList() {
        addActor(aDesireList)
        aDesireList.setBounds(71f,187f,685f,492f)
        aDesireList.blockEdit = Block {
            aDesirePanel.showDialog()
        }
    }



    private fun AdvancedStage.addPanels() {
        addAndFillActors(aDesirePanel, aIncomeExpensePanel)

        aDesirePanel.apply {
            blockRemove = {
                hideDialog()

            }
            blockDone = {
                hideDialog()
                stageUI.root.addAction(Actions.sequence(
                    Actions.delay(0.1f),
                    Actions.run { aDesireList.update() }
                ))
            }
        }
        aIncomeExpensePanel.apply {
            blockRemove = {
                hideDialog()
                stageUI.root.addAction(Actions.sequence(
                    Actions.delay(0.1f),
                    Actions.run {
                        aStatistic.update(summaIncome, summaExpense)
                    }
                ))
            }
            blockDone = {
                hideDialog()
                stageUI.root.addAction(Actions.sequence(
                    Actions.delay(0.1f),
                    Actions.run {
                        aStatistic.update(summaIncome, summaExpense)
                    }
                ))
            }
        }
    }

}