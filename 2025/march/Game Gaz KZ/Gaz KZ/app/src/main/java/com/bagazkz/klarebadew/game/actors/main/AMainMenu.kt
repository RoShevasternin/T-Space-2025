package com.bagazkz.klarebadew.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.bagazkz.klarebadew.game.actors.APlusGaz
import com.bagazkz.klarebadew.game.actors.APlusGold
import com.bagazkz.klarebadew.game.actors.button.AButton
import com.bagazkz.klarebadew.game.actors.progress.AProgressGame
import com.bagazkz.klarebadew.game.screens.MagazScreen
import com.bagazkz.klarebadew.game.screens.MenuScreen
import com.bagazkz.klarebadew.game.utils.Acts
import com.bagazkz.klarebadew.game.utils.Block
import com.bagazkz.klarebadew.game.utils.HEIGHT_UI
import com.bagazkz.klarebadew.game.utils.TIME_ANIM_SCREEN
import com.bagazkz.klarebadew.game.utils.actor.animDelay
import com.bagazkz.klarebadew.game.utils.actor.animHide
import com.bagazkz.klarebadew.game.utils.actor.animShow
import com.bagazkz.klarebadew.game.utils.actor.setBounds
import com.bagazkz.klarebadew.game.utils.actor.setOrigin
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedMainGroup
import com.bagazkz.klarebadew.game.utils.font.FontParameter
import com.bagazkz.klarebadew.game.utils.gdxGame
import com.bagazkz.klarebadew.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

    private val valueXP   = gdxGame.ds_XP.flow.value
    private val valueGold = gdxGame.ds_Gold.flow.value
    private val valueGaz  = gdxGame.ds_Gaz.flow.value

    private val levelIndex: Int = when {
        (valueXP / 150) >= 6 -> 6
        (valueXP / 150) <= 0 -> 0
        else -> valueXP / 150
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(51))

    private val ls51 = Label.LabelStyle(font51, Color.WHITE)

    private val imgTop     = Image(gdxGame.assetsAll.BALANCE)
    private val btnMagaz   = AButton(screen, AButton.Type.Magaz)
    private val btnProd    = AButton(screen, AButton.Type.Prod)
    private val btnDob     = AButton(screen, AButton.Type.Dob)
    private val aPlusGold  = APlusGold(screen)
    private val aPlusGaz   = APlusGaz(screen)
    private val imgZavod   = Image(gdxGame.assetsAll.listZavods[levelIndex])
    private val progress   = AProgressGame(screen)

    private val lblGold = Label(valueGold.toString(), ls51)
    private val lblGaz  = Label(valueGaz.toString(), ls51)
    private val lblXp   = Label(valueXP.toString(), ls51)

    override fun addActorsOnGroup() {
        color.a = 0f

        addTop()
        addBtns()
        addAPlusGold()
        addAPlusGaz()
        addLbls()
        addImgZavod()
        addProgress()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addTop() {
        addActor(imgTop)
        imgTop.setBounds(31f, 1666f, 895f, 356f)
    }

    private fun addAPlusGold() {
        addActor(aPlusGold)
        aPlusGold.setBounds(24f, 717f, 916f, 235f)
    }

    private fun addAPlusGaz() {
        addActor(aPlusGaz)
        aPlusGaz.setBounds(16f, 329f, 914f, 198f)
    }

    private fun addBtns() {
        addActor(btnMagaz)
        btnMagaz.setBounds(367f, 48f, 223f, 213f)

        btnMagaz.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(MagazScreen::class.java.name, this::class.java.name)
            }
        }

        addActor(btnProd)
        btnProd.setBounds(180f, 665f, 613f, 140f)

        btnProd.setOnClickListener {
            aPlusGold.startAnim()
            if (gdxGame.ds_Gaz.flow.value - 10 >= 0) {
                gdxGame.ds_Gaz.update { it - 10 }
                gdxGame.ds_Gold.update { it + 10 }
            }
        }

        addActor(btnDob)
        btnDob.setBounds(180f, 481f, 613f, 140f)

        btnDob.setOnClickListener {
            aPlusGaz.startAnim()
            gdxGame.ds_Gaz.update { it + 10 }
        }
    }

    private fun addLbls() {
        addActors(lblGold, lblGaz, lblXp)
        lblGold.setBounds(141f, 1776f, 84f, 35f)
        lblGaz.setBounds(433f, 1776f, 84f, 35f)
        lblXp.setBounds(678f, 1776f, 124f, 35f)
        lblXp.setAlignment(Align.right)

        coroutine?.launch {
            launch {
                gdxGame.ds_Gaz.flow.collect {
                    runGDX {
                        lblGaz.setText(it)
                    }
                }
            }
            launch {
                gdxGame.ds_Gold.flow.collect {
                    runGDX {
                        lblGold.setText(it)
                    }
                }
            }
        }
    }

    private fun addImgZavod() {
        addActor(imgZavod)
        imgZavod.setBounds(28f, 1018f, 901f, 581f)

        imgZavod.setOrigin(Align.center)
        imgZavod.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.95f, 0.95f, 1.85f),
            Acts.scaleTo(1f, 1f, 1.85f),
        )))
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(83f, 1705f, 791f, 12f)
        progress.progressPercentFlow.value = (levelIndex / 6f) * 100f
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

}