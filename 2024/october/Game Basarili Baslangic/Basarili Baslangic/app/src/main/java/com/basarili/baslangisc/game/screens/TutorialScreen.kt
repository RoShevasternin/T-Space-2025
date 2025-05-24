package com.basarili.baslangisc.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.basarili.baslangisc.game.LibGDXGame
import com.basarili.baslangisc.game.actors.*
import com.basarili.baslangisc.game.utils.*
import com.basarili.baslangisc.game.utils.actor.*
import com.basarili.baslangisc.game.utils.advanced.AdvancedScreen
import com.basarili.baslangisc.game.utils.advanced.AdvancedStage
import com.basarili.baslangisc.game.utils.font.FontParameter
import com.basarili.baslangisc.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TutorialScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font66        = fontGenerator_AlbertSans.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "+").setSize(66))

    private val ls66 = LabelStyle(font66, Color.WHITE)

    private val listClickGaz = List(1) { AClickGaz(this, 1, ls66) }

    private val panelBalance = APanelBalance(this)
    private val imgBuilding  = ABuild_1(this)
    private val imgPop1      = Image(game.all.pop_1)
    private val imgPop2      = Image(game.all.pop_2)
    private val imgPop3      = Image(game.all.pop_3)
    private val imgHand1     = Image(game.all.hand_big)
    private val imgHand2     = Image(game.all.hand_mini)
    private val btnDukan     = AButton(this, AButton.Type.Dukan)
    private val panelLVL     = APanelLVL(this, 1)

    private var isPopUp2 = false

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.background_game.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)

        game.dataStore.updateItemCountByIndex(0) { 1 }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelBalance()
        addPanelLVL()
        addImgBuilding()
        addImgPopUp1()
        addImgPopUp2()
        addBtnDukan()
        addImgHand1()
        addClickGaz()

        uiBackgroundImage.setOnClickListenerWithPos { _, pos ->
            if (isPopUp2) {
                isPopUp2 = false
                game.soundUtil.apply { play(listBubble.random(), 0.05f) }
                panelBalance.updateGaz { it + 1 }

                listClickGaz.first().apply {
                    setPosition(pos.sub(95f, 56f))
                    launchGaz()
                }

                imgHand1.animHide(TIME_ANIM)
                imgPop2.animHide(TIME_ANIM) {
                    addActorsToTopStage()
                }
            }
        }
    }

    // Add Actors ------------------------------------------------------------------

    private fun AdvancedStage.addPanelBalance() {
        addActor(panelBalance)
        panelBalance.setBounds(82f, 2035f, 879f, 126f)
    }

    private fun AdvancedStage.addImgBuilding() {
        addActor(imgBuilding)
        imgBuilding.setBounds(387f, 972f, 349f, 389f)
    }

    private fun AdvancedStage.addPanelLVL() {
        addActor(panelLVL)
        panelLVL.setBounds(0f, 55f, 279f, 168f)
    }

    private fun AdvancedStage.addImgPopUp1() {
        addActor(imgPop1)
        imgPop1.disable()
        imgPop1.setBounds(105f, 789f, 835f, 997f)

        val aBasla = Actor()
        addActor(aBasla)
        aBasla.apply {
            setBounds(369f, 1011f, 305f, 114f)
            setOnClickListener(game.soundUtil) {
                disable()
                imgPop1.animHide(TIME_ANIM)
                imgPop2.animShow(TIME_ANIM)
                imgHand1.animShow(TIME_ANIM)
                isPopUp2 = true
            }
        }
    }

    private fun AdvancedStage.addImgPopUp2() {
        addActor(imgPop2)
        imgPop2.disable()
        imgPop2.animHide()
        imgPop2.setBounds(83f, 610f, 877f, 280f)
    }

    private fun AdvancedStage.addImgHand1() {
        addActor(imgHand1)
        imgHand1.disable()
        imgHand1.animHide()
        imgHand1.setBounds(47f, 961f, 401f, 447f)
        imgHand1.setOrigin(108f, 332f)
        imgHand1.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleBy(-0.3f, -0.3f, 0.3f),
                Actions.scaleBy(0.3f, 0.3f, 0.3f),
            )
        ))
    }

    private fun AdvancedStage.addBtnDukan() {
        addActor(btnDukan)
        btnDukan.setBounds(709f, 55f, 279f, 279f)
        btnDukan.disable(false)

        btnDukan.setOnClickListener {
            topStageBack.root.animHide(TIME_ANIM)
            topStageUI.root.animHide(TIME_ANIM)
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(MarketScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addClickGaz() {
        listClickGaz.onEach { gaz ->
            addActor(gaz)
            gaz.setBounds(WIDTH_UI+100, 0f, 190f, 112f)
        }
    }

    // Add to Top ------------------------------------------------------------------

    private fun addActorsToTopStage() {
        stageUI.root.disable()
        topStageBack.addAndFillActor(Image(drawerUtil.getRegion(GColor.dark_1A_70)).apply {
            color.a = 0f
            animShow(TIME_ANIM)
        })
        topStageUI.apply {
            addImgBuilding()
            addBtnDukan()
            addPopUp3()
            addImgHand2()

            imgHand2.animShow(TIME_ANIM) {
                btnDukan.enable()
            }
        }
    }

    private fun AdvancedStage.addPopUp3() {
        addActor(imgPop3)
        imgPop3.setBounds(61f, 564f, 924f, 332f)
    }

    private fun AdvancedStage.addImgHand2() {
        addActor(imgHand2)
        imgHand2.disable()
        imgHand2.animHide()
        imgHand2.setBounds(471f, -15f, 374f, 353f)
        imgHand2.setOrigin(244f, 262f)
        imgHand2.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleBy(-0.3f, -0.3f, 0.3f),
                Actions.scaleBy(0.3f, 0.3f, 0.3f),
            )
        ))
    }

    // Logic ----------------------------------------------------------------------

    private fun AClickGaz.launchGaz() {
        coroutine?.launch {
            delay(500)
            runGDX {
                animHide(TIME_ANIM) {
                    setPosition(WIDTH_UI+100f, 0f)
                }
            }
        }
    }

}