package com.pulser.purlembager.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.pulser.purlembager.game.actors.ABack
import com.pulser.purlembager.game.actors.ADialogMenu
import com.pulser.purlembager.game.actors.APanel
import com.pulser.purlembager.game.actors.AScrollPane
import com.pulser.purlembager.game.actors.autoLayout.AVerticalGroup
import com.pulser.purlembager.game.screens.AddScreen
import com.pulser.purlembager.game.screens.BlogScreen
import com.pulser.purlembager.game.screens.MenuScreen
import com.pulser.purlembager.game.utils.Block
import com.pulser.purlembager.game.utils.TIME_ANIM_SCREEN
import com.pulser.purlembager.game.utils.actor.*
import com.pulser.purlembager.game.utils.advanced.AdvancedGroup
import com.pulser.purlembager.game.utils.advanced.AdvancedScreen
import com.pulser.purlembager.game.utils.gdxGame
import com.pulser.purlembager.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainBlog(
    override val screen: AdvancedScreen,
    val dialogMenu: ADialogMenu,
    val aPanel    : APanel,
): AdvancedGroup() {

    private val screenBlog = screen as BlogScreen

    private val aBack   = ABack(screen, "Блог", ABack.Type.Black)
    private val imgBtns = Image(gdxGame.assetsAll.blog_button)

    private val verticalGroup = AVerticalGroup(screen, isWrap = true, paddingBottom = 300f)
    private val scroll        = AScrollPane(verticalGroup)
    private val imgBlog       = Image()

    private var isBlogOpen = false

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addABack()
                addBlog()
                addScroll()

                handleAPanel()
                handleDialogMenu()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addABack() {
        addActor(aBack)
        aBack.setBounds(0f, 2367f, 1176f, 63f)
        aBack.blockBack = {
            if (isBlogOpen) {
                isBlogOpen = false
                scroll.animHide(0.25f)
                scroll.disable()
                imgBtns.animShow(0.25f)
            } else {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
    }

    private fun addBlog() {
        addActor(imgBtns)
        imgBtns.setBounds(0f, 454f, 1176f, 1647f)

        var ny = 1834f

        repeat(5) { index ->
            val a = Actor()
            addActor(a)
            a.setBounds(62f, ny, 1050f, 203f)
            ny -= 125 + 203

            a.setOnClickListener(gdxGame.soundUtil) {
                showBlog(index)
            }
        }
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 0f, 1176f, 2307f)
        scroll.disable()

        imgBlog.setSize(1176f, 10f)
        verticalGroup.addActor(imgBlog)
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
            blockHome = {
                screen.hideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
            blockAdd = {
                showDialogMenu()
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

    private fun showDialogMenu() {
        screenBlog.showBackground()
        dialogMenu.animShow(TIME_ANIM_SCREEN) {
            dialogMenu.enable()
        }
    }

    private fun hideDialogMenu() {
        screenBlog.hideBackground()
        dialogMenu.animHide(TIME_ANIM_SCREEN) {
            dialogMenu.disable()
        }
    }

    private fun showBlog(index: Int) {
        isBlogOpen = true

        imgBlog.drawable = TextureRegionDrawable(gdxGame.assetsAll.listBlog[index])
        imgBlog.height   = listOf(3180f, 2270f, 2270f, 2619f, 2637f)[index]
        verticalGroup.height = imgBlog.height + 300f

        scroll.apply {
            animShow(0.25f)
            enable()
        }
        imgBtns.animHide(0.25f)
    }

}