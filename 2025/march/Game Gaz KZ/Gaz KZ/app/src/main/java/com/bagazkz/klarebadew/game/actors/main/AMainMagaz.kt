package com.bagazkz.klarebadew.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bagazkz.klarebadew.game.actors.button.AButton
import com.bagazkz.klarebadew.game.screens.MagazScreen
import com.bagazkz.klarebadew.game.screens.MenuScreen
import com.bagazkz.klarebadew.game.utils.Block
import com.bagazkz.klarebadew.game.utils.TIME_ANIM_SCREEN
import com.bagazkz.klarebadew.game.utils.actor.animDelay
import com.bagazkz.klarebadew.game.utils.actor.animHide
import com.bagazkz.klarebadew.game.utils.actor.animShow
import com.bagazkz.klarebadew.game.utils.actor.setOnClickListener
import com.bagazkz.klarebadew.game.utils.advanced.AdvancedMainGroup
import com.bagazkz.klarebadew.game.utils.gdxGame

class AMainMagaz(
    override val screen: MagazScreen,
): AdvancedMainGroup() {

    private val imgDialog = Image(gdxGame.assetsAll.MAGAZIK)
    private val btnX      = AButton(screen, AButton.Type.None)

    override fun addActorsOnGroup() {
        color.a = 0f

        addDialog()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addDialog() {
        addActor(imgDialog)
        imgDialog.setBounds(0f, 103f, 958f, 1871f)

        addActor(btnX)
        btnX.setBounds(753f, 1821f, 204f, 153f)
        btnX.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }

        val btnBuy1 = Actor()
        val btnBuy2 = Actor()
        val btnBuy3 = Actor()
        val btnBuy4 = Actor()
        val btnBuy5 = Actor()
        val btnBuy6 = Actor()
        addActors(btnBuy1, btnBuy2, btnBuy3, btnBuy4, btnBuy5, btnBuy6)
        btnBuy1.setBounds(712f, 1636f, 193f, 102f)
        btnBuy2.setBounds(712f, 1317f, 193f, 102f)
        btnBuy3.setBounds(712f, 994f, 193f, 102f)
        btnBuy4.setBounds(712f, 713f, 193f, 102f)
        btnBuy5.setBounds(712f, 448f, 193f, 102f)
        btnBuy6.setBounds(712f, 208f, 193f, 102f)

        btnBuy1.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                if (gdxGame.ds_Gold.flow.value - 150 >= 0) {
                    gdxGame.ds_Gold.update { it - 150 }
                    gdxGame.ds_XP.update { it + 150 }
                }
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
        btnBuy2.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                if (gdxGame.ds_Gold.flow.value - 150 >= 0) {
                    gdxGame.ds_Gold.update { it - 150 }
                    gdxGame.ds_XP.update { it + 150 }
                }
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
        btnBuy3.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                if (gdxGame.ds_Gold.flow.value - 150 >= 0) {
                    gdxGame.ds_Gold.update { it - 150 }
                    gdxGame.ds_XP.update { it + 150 }
                }
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
        btnBuy4.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                if (gdxGame.ds_Gold.flow.value - 150 >= 0) {
                    gdxGame.ds_Gold.update { it - 150 }
                    gdxGame.ds_XP.update { it + 150 }
                }
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
        btnBuy5.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                if (gdxGame.ds_Gold.flow.value - 150 >= 0) {
                    gdxGame.ds_Gold.update { it - 150 }
                    gdxGame.ds_XP.update { it + 150 }
                }
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
        btnBuy6.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                if (gdxGame.ds_Gold.flow.value - 150 >= 0) {
                    gdxGame.ds_Gold.update { it - 150 }
                    gdxGame.ds_XP.update { it + 150 }
                }
                gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}