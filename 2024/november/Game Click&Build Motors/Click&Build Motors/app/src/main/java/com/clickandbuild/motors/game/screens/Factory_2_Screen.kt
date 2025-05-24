package com.clickandbuild.motors.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.clickandbuild.motors.game.LibGDXGame
import com.clickandbuild.motors.game.actors.AButton
import com.clickandbuild.motors.game.actors.AFactoryProgress
import com.clickandbuild.motors.game.actors.AOrder
import com.clickandbuild.motors.game.screens.Factory_0_Screen.Companion.BODY_INDEX
import com.clickandbuild.motors.game.screens.Factory_0_Screen.Companion.INTERIOR_INDEX
import com.clickandbuild.motors.game.utils.TIME_ANIM
import com.clickandbuild.motors.game.utils.actor.animHide
import com.clickandbuild.motors.game.utils.actor.animShow
import com.clickandbuild.motors.game.utils.advanced.AdvancedScreen
import com.clickandbuild.motors.game.utils.advanced.AdvancedStage
import com.clickandbuild.motors.game.utils.font.FontParameter
import com.clickandbuild.motors.game.utils.region
import com.clickandbuild.motors.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Factory_2_Screen(override val game: LibGDXGame) : AdvancedScreen() {

    private var TAP_COUNT  = 3
    private val percentTap = 100f / TAP_COUNT

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(42)
    private val font          = fontGenerator_Pusia.generateFont(fontParameter)
    private val ls            = LabelStyle(font, Color.WHITE)

    private val lblTap = Label("Tap $TAP_COUNT times", ls)

    private val aProgress  = AFactoryProgress(this)
    private val aOrder     = AOrder(this, INTERIOR_INDEX, BODY_INDEX)
    private val imgFactory = Image(game.all.listT_2[Factory_1_Screen.INTERIOR_INDEX])
    private val btnCreate  = AButton(this, AButton.Type.Create)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loader.background_1.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(aProgress, aOrder, imgFactory, btnCreate, lblTap)
        aProgress.setBounds(158f,1728f,560f,125f)
        aOrder.setBounds(147f,1158f,583f,450f)
        imgFactory.setBounds(29f,515f,819f,507f)
        btnCreate.setBounds(157f,148f,563f,128f)

        btnCreate.setOnClickListener(null) {
            game.soundUtil.apply { play(game.soundUtil.listS.random()) }

            btnCreate.disable()
            aProgress.progressPercentFlow.value += percentTap
            TAP_COUNT--
            lblTap.setText("Tap $TAP_COUNT times")

            coroutine?.launch {
                delay(400)
                runGDX {
                    if (TAP_COUNT <= 0) {
                        game.soundUtil.apply { play(success, 0.2f) }

                        root.animHide(TIME_ANIM) {
                            game.navigationManager.navigate(Factory_3_Screen::class.java.name)
                        }
                    } else btnCreate.enable()
                }
            }
        }

        lblTap.apply {
            setBounds(320f, 58f, 236f, 29f)
            setAlignment(Align.center)
        }

    }

}