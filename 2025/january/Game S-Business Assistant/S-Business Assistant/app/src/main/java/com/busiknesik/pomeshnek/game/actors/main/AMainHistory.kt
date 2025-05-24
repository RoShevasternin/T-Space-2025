package com.busiknesik.pomeshnek.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.busiknesik.pomeshnek.game.actors.AMenu
import com.busiknesik.pomeshnek.game.actors.ATovar
import com.busiknesik.pomeshnek.game.actors.AScrollPane
import com.busiknesik.pomeshnek.game.actors.autoLayout.AVerticalGroup
import com.busiknesik.pomeshnek.game.screens.HistoryScreen
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

class AMainHistory(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontB_58      = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(58))
    private val fontR_38      = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(38))

    private val lsB_58  = Label.LabelStyle(fontB_58, Color.WHITE)
    private val lsR_80  = Label.LabelStyle(fontR_38, GameColor.gray_95)
    private val lsBG_58 = Label.LabelStyle(fontB_58, GameColor.green)

    private val listInvestData = gdxGame.ds_Tovar.flow.value.reversed()

    private val imgSeeAll = Image(gdxGame.assetsAll.tovars)
    private val vertical    = AVerticalGroup(screen, 63f, isWrap = true)
    private val scroll      = AScrollPane(vertical)
    private val listAInvest = List(listInvestData.size) { ATovar(
        screen, listInvestData[it],
        lsB_58,
        lsBG_58,
        lsR_80,
    ) }

    private val imgBrand  = Image(gdxGame.assetsAll.brand)
    private val aMenu     = AMenu(screen, gdxGame.assetsAll.histore)


    override fun addActorsOnGroup() {
        color.a = 0f

        addImgBrand()
        addSeeAll()
        addScroll()
        addAMenu()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgBrand() {
        addActor(imgBrand)
        imgBrand.setBounds(55f, 2149f, 1081f, 383f)
    }

    private fun addSeeAll() {
        addActor(imgSeeAll)
        imgSeeAll.setBounds(71f, 1961f, 1049f, 141f)
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(71f, 0f, 1049f, 1898f)

        vertical.setSize(1049f, 1898f)
        listAInvest.onEach { invest ->
            invest.setSize(1048f, 832f)
            vertical.addActor(invest)
        }
    }

    private fun addAMenu() {
        addActor(aMenu)
        aMenu.setBounds(71f, 69f, 1049f, 252f)

        aMenu.apply {
            blockHome = {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
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