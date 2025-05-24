package com.sukapital.saepital.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
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

class BlogScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var SELECTED_BLOG_INDEX = 0
            private set
    }

    // Actors
    private val aHeader  = AHeader(this)
    private val aBottom  = ABottom(this)

    private val tmpGroup = TmpGroup(this)
    private val imgBlog  = Image(game.all.BLOG_LIST)
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
            addImgBlog()
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

    private fun AdvancedGroup.addImgBlog() {
        addActor(imgBlog)
        imgBlog.setBounds(49f,391f, 730f,1117f)

        val aBack = Actor()
        addActor(aBack)
        aBack.apply {
            setBounds(80f,1394f,91f,91f)
            setOnClickListener(game.soundUtil) { tmpGroup.animHide(TIME_ANIM) { game.navigationManager.back() } }
        }

        val listBtn = List(5) { Actor() }
        var ny = 1154f
        listBtn.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(49f, ny,728f,124f)
            ny -= 66 + 124

            actor.setOnClickListener(game.soundUtil) {
                SELECTED_BLOG_INDEX = index
                tmpGroup.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(InfoScreen::class.java.name, BlogScreen::class.java.name)
                }
            }
        }
    }

}