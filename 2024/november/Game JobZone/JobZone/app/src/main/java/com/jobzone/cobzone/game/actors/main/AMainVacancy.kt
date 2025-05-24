package com.jobzone.cobzone.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jobzone.cobzone.game.actors.button.AButton
import com.jobzone.cobzone.game.screens.NotActiveScreen
import com.jobzone.cobzone.game.screens.RespondScreen
import com.jobzone.cobzone.game.screens.VacancyScreen
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.TIME_ANIM_SCREEN
import com.jobzone.cobzone.game.utils.actor.animHideSuspend
import com.jobzone.cobzone.game.utils.actor.animShowSuspend
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.runGDX
import com.jobzone.cobzone.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class AMainVacancy(override val screen: AdvancedScreen): AdvancedGroup() {

    private val btnOtklick = AButton(screen, AButton.Type.Otklick)
    private val imgPanel   = Image(screen.game.assetsAll.panel)
    private val imgDetails = Image(screen.game.assetsAll.listDetails[AMainVacancies.ZAVOD_ID])

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addImgPanel()
                addImgDetails()
                addBtnOtklick()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(0f, 0f, 731f, 1367f)
    }

    private fun addImgDetails() {
        addActor(imgDetails)
        imgDetails.setBounds(52f, 207f, 679f, 1121f)
    }

    private fun addBtnOtklick() {
        addActor(btnOtklick)
        btnOtklick.setBounds(58f, 117f, 615f, 90f)

        val resultJob = screen.game.sharedPreferences.getString("is_Job", "no_Job") ?: "no_Job"

        btnOtklick.setOnClickListener {
            log("resultJob = $resultJob")

            screen.hideScreen {
                if (resultJob == "no_Job") {
                    if (Random.nextBoolean()) {
                        screen.game.navigationManager.navigate(RespondScreen::class.java.name, VacancyScreen::class.java.name)
                    } else {
                        screen.game.navigationManager.navigate(NotActiveScreen::class.java.name)
                    }
                } else {
                    screen.game.activity.showUrl(resultJob)
                }
            }
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withContext(Dispatchers.Default) {
            animShowSuspend(TIME_ANIM_SCREEN)
        }
    }

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

}