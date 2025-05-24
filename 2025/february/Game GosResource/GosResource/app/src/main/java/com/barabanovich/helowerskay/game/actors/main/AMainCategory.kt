package com.barabanovich.helowerskay.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.barabanovich.helowerskay.game.actors.button.AButton
import com.barabanovich.helowerskay.game.screens.CategoryScreen
import com.barabanovich.helowerskay.game.screens.GameScreen
import com.barabanovich.helowerskay.game.utils.Block
import com.barabanovich.helowerskay.game.utils.TIME_ANIM_SCREEN
import com.barabanovich.helowerskay.game.utils.actor.animDelay
import com.barabanovich.helowerskay.game.utils.actor.animHide
import com.barabanovich.helowerskay.game.utils.actor.animShow
import com.barabanovich.helowerskay.game.utils.actor.setOnTouchListener
import com.barabanovich.helowerskay.game.utils.advanced.AdvancedMainGroup
import com.barabanovich.helowerskay.game.utils.gdxGame

class AMainCategory(
    override val screen: CategoryScreen,
): AdvancedMainGroup() {

    private val imgTitle  = Image(gdxGame.assetsAll.theme)
    private val listItems = List(4) { AButton(screen, AButton.Type.listType[it]) }

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgCatg()
        addListItems()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgCatg() {
        addActor(imgTitle)
        imgTitle.setBounds(67f, 1933f, 879f, 94f)
    }

    private fun addListItems() {
        var ny = 1463f
        listItems.onEachIndexed { index, img ->
            addActor(img)
            img.setBounds(67f, ny, 879f, 404f)

            ny -= 53 + 404

            img.setOnTouchListener {
                CategoryScreen.CURRENT_QUIZ_INDEX = index

                screen.hideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name, screen::class.java.name)
                }
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