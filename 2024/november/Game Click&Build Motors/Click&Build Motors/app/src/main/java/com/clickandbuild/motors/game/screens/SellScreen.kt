package com.clickandbuild.motors.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
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
import com.clickandbuild.motors.game.utils.region
import com.clickandbuild.motors.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SellScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val aProgress  = AFactoryProgress(this)
    private val aOrder     = AOrder(this, INTERIOR_INDEX, BODY_INDEX)
    private val imgFactory = Image(game.all.listT_9[Factory_4_Screen.BODY_INDEX][Factory_1_Screen.INTERIOR_INDEX])
    private val btnSell    = AButton(this, AButton.Type.Sell)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.background_3.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(aProgress, aOrder, imgFactory, btnSell)
        aProgress.setBounds(158f,1728f,560f,125f)
        aOrder.setBounds(147f,1158f,583f,450f)
        imgFactory.setBounds(29f,515f,819f,285f)
        btnSell.setBounds(157f,148f,563f,128f)

        btnSell.setOnClickListener {
            btnSell.disable()
            aProgress.progressPercentFlow.value += 100

            coroutine?.launch {
                delay(400)
                runGDX {
                    if (
                        Factory_1_Screen.INTERIOR_INDEX == INTERIOR_INDEX &&
                        Factory_4_Screen.BODY_INDEX     == BODY_INDEX
                    ) {
                        root.animHide(TIME_ANIM) {
                            game.soundUtil.apply { play(sell) }
                            game.navigationManager.navigate(Factory_0_Screen::class.java.name)
                        }
                    } else {
                        root.animHide(TIME_ANIM) {
                            game.navigationManager.navigate(AttentionScreen::class.java.name)
                        }
                    }
                }
            }
        }

    }

}