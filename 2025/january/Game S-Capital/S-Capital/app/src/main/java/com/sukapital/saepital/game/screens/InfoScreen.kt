package com.sukapital.saepital.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.sukapital.saepital.game.LibGDXGame
import com.sukapital.saepital.game.actors.TmpGroup
import com.sukapital.saepital.game.actors.systemPanel.AIncomeExpenseDialog
import com.sukapital.saepital.game.actors.systemUI.ABottom
import com.sukapital.saepital.game.actors.systemUI.AHeader
import com.sukapital.saepital.game.utils.TIME_ANIM
import com.sukapital.saepital.game.utils.WIDTH_UI
import com.sukapital.saepital.game.utils.actor.animHide
import com.sukapital.saepital.game.utils.actor.animShow
import com.sukapital.saepital.game.utils.actor.setOnClickListener
import com.sukapital.saepital.game.utils.advanced.AdvancedGroup
import com.sukapital.saepital.game.utils.advanced.AdvancedScreen
import com.sukapital.saepital.game.utils.advanced.AdvancedStage

class InfoScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val currentIndexBlog = BlogScreen.SELECTED_BLOG_INDEX

    private val currentHeight = listOf(1757f, 1625f, 1577f, 1621f, 1581f)[currentIndexBlog]
    private val currentBackY  = listOf(1631f, 1499f, 1475f, 1519f, 1455f)[currentIndexBlog]

    // Actors
    private val aHeader  = AHeader(this)
    private val aBottom  = ABottom(this)

    private val tmpGroup = TmpGroup(this)

    private val tmpGroupBlog = TmpGroup(this).apply { setSize(729f, currentHeight) }
    private val imgBlog      = Image(game.all.listBlog[currentIndexBlog])
    private val scroll       = ScrollPane(tmpGroupBlog)
    private val aIncomeExpensePanel = AIncomeExpenseDialog(this)

    override fun show() {
        tmpGroup.animHide()
        super.show()
        tmpGroup.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAndFillActors(tmpGroup)
        addSystemUI()
        aBottom.checkBlog()

        tmpGroup.apply {
            addScroll()
        }

        topStageUI.apply {
            addPanels()
        }
    }

    private fun AdvancedStage.addPanels() {
        addAndFillActors(aIncomeExpensePanel)

        aIncomeExpensePanel.apply {
            blockRemove = {
                hideDialog()

            }
            blockDone = {
                hideDialog()
            }
        }
    }

    private fun AdvancedStage.addSystemUI() {
        addActor(aHeader)
        aHeader.setBounds(0f,1552f, WIDTH_UI,240f)
        addActor(aBottom)
        aBottom.setBounds(0f,0f, WIDTH_UI,187f)

        aBottom.blockNavTo = {
            if (it == ABottom.ClickType.Main) tmpGroup.animHide(TIME_ANIM) {
                game.navigationManager.clearBackStack()
                game.navigationManager.navigate(MainScreen::class.java.name)
            }
            if (it == ABottom.ClickType.Plus) {
                aIncomeExpensePanel.showDialog()
            }
        }
    }

    private fun AdvancedGroup.addScroll() {
        addActor(scroll)
        scroll.setBounds(49f,186f, 729f,1321f)

        tmpGroupBlog.apply {
            addAndFillActor(imgBlog)

            val aBack = Actor()
            addActor(aBack)
            aBack.apply {
                setBounds(44f, currentBackY,61f,61f)
                setOnClickListener(game.soundUtil) { tmpGroupBlog.animHide(TIME_ANIM) { game.navigationManager.back() } }
            }
        }

    }

}