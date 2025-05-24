package com.jobzone.cobzone.game.actors.main

import com.jobzone.cobzone.game.actors.AScrollPane
import com.jobzone.cobzone.game.actors.listZavod.AZavodGroup
import com.jobzone.cobzone.game.screens.VacanciesScreen
import com.jobzone.cobzone.game.screens.VacancyScreen
import com.jobzone.cobzone.game.utils.Block
import com.jobzone.cobzone.game.utils.TIME_ANIM_SCREEN
import com.jobzone.cobzone.game.utils.actor.animHideSuspend
import com.jobzone.cobzone.game.utils.actor.animShowSuspend
import com.jobzone.cobzone.game.utils.advanced.AdvancedGroup
import com.jobzone.cobzone.game.utils.advanced.AdvancedScreen
import com.jobzone.cobzone.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainVacancies(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var ZAVOD_ID = 0
            private set
    }

    private val zavodGroup = AZavodGroup(screen)
    private val scroll     = AScrollPane(zavodGroup)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f
                addScroll()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 0f, 731f, 1400f)
        zavodGroup.setSize(731f, 4652f)

        zavodGroup.blockClick = {
            ZAVOD_ID = it
            screen.hideScreen {
                screen.game.navigationManager.navigate(VacancyScreen::class.java.name, VacanciesScreen::class.java.name)
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