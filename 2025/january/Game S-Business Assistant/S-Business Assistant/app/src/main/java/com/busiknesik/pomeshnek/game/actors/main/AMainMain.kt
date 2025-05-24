package com.busiknesik.pomeshnek.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.busiknesik.pomeshnek.game.actors.AMenu
import com.busiknesik.pomeshnek.game.actors.APanel
import com.busiknesik.pomeshnek.game.actors.ATovar
import com.busiknesik.pomeshnek.game.screens.HistoryScreen
import com.busiknesik.pomeshnek.game.screens.AddScreen
import com.busiknesik.pomeshnek.game.utils.Block
import com.busiknesik.pomeshnek.game.utils.GameColor
import com.busiknesik.pomeshnek.game.utils.TIME_ANIM_SCREEN
import com.busiknesik.pomeshnek.game.utils.actor.animHide
import com.busiknesik.pomeshnek.game.utils.actor.animShow
import com.busiknesik.pomeshnek.game.utils.actor.setOnClickListener
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedGroup
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.font.FontParameter
import com.busiknesik.pomeshnek.game.utils.gdxGame

class AMainMain(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val imgBrand  = Image(gdxGame.assetsAll.brand)
    private val aPanel    = APanel(screen)
    private val btnAdd    = Actor()
    private val btnSeeAll = Actor()
    private val aMenu     = AMenu(screen, gdxGame.assetsAll.home)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontB_58      = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(58))
    private val fontR_38      = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(38))

    private val lsB_58  = Label.LabelStyle(fontB_58, Color.WHITE)
    private val lsR_80  = Label.LabelStyle(fontR_38, GameColor.gray_95)
    private val lsBG_58 = Label.LabelStyle(fontB_58, GameColor.green)

    private val tovarData = gdxGame.ds_Tovar.flow.value.lastOrNull()

    private val aTovar = if (tovarData != null) ATovar(
        screen, tovarData,
        lsB_58,
        lsBG_58,
        lsR_80,
    ) else null

    override fun addActorsOnGroup() {
        color.a = 0f

        addATovar()
        addImgBrand()
        addPanel()
        addBtnAdd()
        addBtnSeeAll()
        addAMenu()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgBrand() {
        addActor(imgBrand)
        imgBrand.setBounds(55f, 2149f, 1081f, 383f)
    }

    private fun addPanel() {
        addActor(aPanel)
        aPanel.setBounds(71f, 896f, 1049f, 1206f)
    }

    private fun addBtnAdd() {
        addActor(btnAdd)
        btnAdd.setBounds(71f, 1183f, 1048f, 177f)

        btnAdd.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(AddScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addBtnSeeAll() {
        addActor(btnSeeAll)
        btnSeeAll.setBounds(275f, 1070f, 641f, 97f)
        btnSeeAll.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAMenu() {
        addActor(aMenu)
        aMenu.setBounds(71f, 69f, 1049f, 252f)

        aMenu.apply {
            blockHistory = {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(HistoryScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }

    private fun addATovar() {
        if (aTovar != null) {
            addActor(aTovar)
            aTovar.setBounds(71f, 1f, 1048f, 831f)
        }
    }

    // Anim Main ------------------------------------------------

    private fun animShowMain() {
        animShow(TIME_ANIM_SCREEN)
    }

    fun animHideMain(block: Block = Block {}) {
        animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

}