package com.ogonechek.putinvestor.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.ogonechek.putinvestor.game.actors.ALogo
import com.ogonechek.putinvestor.game.actors.shader.AFireGroup
import com.ogonechek.putinvestor.game.screens.MenuScreen
import com.ogonechek.putinvestor.game.utils.Acts
import com.ogonechek.putinvestor.game.utils.Block
import com.ogonechek.putinvestor.game.utils.TIME_ANIM_SCREEN
import com.ogonechek.putinvestor.game.utils.actor.animHide
import com.ogonechek.putinvestor.game.utils.actor.animShow
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedGroup
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.font.FontParameter
import com.ogonechek.putinvestor.game.utils.gdxGame

class AMainOnboard(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listText = listOf(
        "Соберите все свои инвестиции в одном месте и управляйте ими, сохраняя полный контроль",
        "Укажите в калькуляторе срок, сумму и процент — и программа наглядно рассчитает для вас потенциальную прибыль."
    )

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font80        = screen.fontGenerator_Regular.generateFont(fontParameter.setSize(80))

    private val ls80 = Label.LabelStyle(font80, Color.WHITE)

    private val ogon  = Image(gdxGame.assetsLoader.ogon)
    private val aLogo = ALogo(screen)
    private val aFire = AFireGroup(screen)

    private val imgPanel = Image(gdxGame.assetsAll.blue)
    private val lblText  = Label(listText[0], ls80)

    override fun addActorsOnGroup() {
        addOgon()
        addLogo()
        addPanel()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addOgon() {
        addActor(aFire)
        aFire.setBounds(0f, 118f, 1362f, 2718f)
        aFire.addAndFillActor(ogon)
    }

    private fun addLogo() {
        addActor(aLogo)
        aLogo.setBounds(192f, 1634f, 960f, 306f)
    }

    private fun addPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(119f, 890f, 1127f, 660f)
        imgPanel.color.a = 0f

        addActor(lblText)
        lblText.setBounds(192f, 999f, 980f, 442f)
        lblText.setAlignment(Align.center)
        lblText.color.a = 0f
        lblText.wrap    = true
    }

    // Anim Main ------------------------------------------------

    private fun animShowMain() {
        //animShow(TIME_ANIM_SCREEN)

        imgPanel.animShow(0.5f)

        lblText.addAction(Acts.sequence(
            Acts.delay(0.25f),
            Acts.fadeIn(0.45f),
            Acts.delay(1.75f),
            Acts.fadeOut(0.45f),
            Acts.run { lblText.setText(listText[1]) },
            Acts.fadeIn(0.45f),
            Acts.delay(1.75f),
            Acts.run {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        ))
    }

    fun animHideMain(block: Block = Block {}) {
        animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

}