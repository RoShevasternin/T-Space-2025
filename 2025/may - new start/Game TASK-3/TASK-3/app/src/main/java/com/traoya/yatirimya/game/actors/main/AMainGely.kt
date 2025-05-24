package com.traoya.yatirimya.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.traoya.yatirimya.game.actors.ABalance
import com.traoya.yatirimya.game.actors.button.AButton
import com.traoya.yatirimya.game.screens.GelyScreen
import com.traoya.yatirimya.game.screens.MenuScreen
import com.traoya.yatirimya.game.screens.TutorialScreen
import com.traoya.yatirimya.game.utils.Block
import com.traoya.yatirimya.game.utils.GameColor
import com.traoya.yatirimya.game.utils.TIME_ANIM_SCREEN
import com.traoya.yatirimya.game.utils.WIDTH_UI
import com.traoya.yatirimya.game.utils.actor.animDelay
import com.traoya.yatirimya.game.utils.actor.animHide
import com.traoya.yatirimya.game.utils.actor.animShow
import com.traoya.yatirimya.game.utils.actor.disable
import com.traoya.yatirimya.game.utils.actor.setOnClickListenerWithBlock
import com.traoya.yatirimya.game.utils.actor.setPosition
import com.traoya.yatirimya.game.utils.advanced.AdvancedMainGroup
import com.traoya.yatirimya.game.utils.font.FontParameter
import com.traoya.yatirimya.game.utils.gdxGame
import com.traoya.yatirimya.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.get

class AMainGely(override val screen: GelyScreen): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "-LVL")
    private val font66        = screen.fontGenerator_SemiBold.generateFont(fontParameter.setSize(66))

    private val ls66 = Label.LabelStyle(font66, GameColor.red)

    private val btnRD          = AButton(screen, AButton.Type.RD)

    private val aBalance = ABalance(screen)

    private val imgLvl = Image(gdxGame.assetsAll.wht)
    private val lblLvl = Label("${gdxGame.ds_Level.flow.value.inc()}-LVL", ls66)

    private val listClick         = List(75) { Image(gdxGame.assetsAll.barel) }
    private var currentClickIndex = 0

    override fun addActorsOnGroup() {
        color.a = 0f

        addAndFillActor(Image(gdxGame.assetsAll.listDashboard[gdxGame.ds_Level.flow.value]).apply { this.disable() })
        listClick.onEach {
            addActor(it)
            it.setPosition(WIDTH_UI + 100f, 0f)
        }

        addABalance()
        addLvl()
        addBtnRD()

        animShowMain()

        this.setOnClickListenerWithBlock(
            touchDownBlock = { x, y ->
                gdxGame.soundUtil.apply { play(touch) }

                gdxGame.ds_Gel.update { it + 1 }

                if (currentClickIndex == listClick.lastIndex) currentClickIndex = 0

                listClick[currentClickIndex++].apply {
                    this.setPosition(x - 110f, y - 95f)
                    launchGaz()
                }
            }
        )
    }

    // Actors ------------------------------------------------------------------------

    private fun addABalance() {
        addActor(aBalance)
        aBalance.setBounds(0f, 2030f, 1040f, 125f)
    }

    private fun addLvl() {
        addActor(imgLvl)
        imgLvl.setBounds(83f, 102f, 278f, 168f)

        addActor(lblLvl)
        lblLvl.setBounds(135f, 152f, 172f, 67f)
    }

    private fun addBtnRD() {
        addActor(btnRD)
        btnRD.setBounds(679f, 102f, 279f, 168f)

        btnRD.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ----------------------------------------------------------------------

    private fun Image.launchGaz() {
        coroutine?.launch {
            delay(500)
            runGDX {
                animHide(0.5f) {
                    setPosition(WIDTH_UI+100f, 0f)
                    animShow()
                }
            }
        }
    }

}