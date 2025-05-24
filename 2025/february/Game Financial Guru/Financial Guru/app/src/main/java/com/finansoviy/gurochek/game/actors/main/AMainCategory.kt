package com.finansoviy.gurochek.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.finansoviy.gurochek.game.actors.ATmpGroup
import com.finansoviy.gurochek.game.screens.CategoryScreen
import com.finansoviy.gurochek.game.screens.GameScreen
import com.finansoviy.gurochek.game.utils.Block
import com.finansoviy.gurochek.game.utils.GameColor
import com.finansoviy.gurochek.game.utils.TIME_ANIM_SCREEN
import com.finansoviy.gurochek.game.utils.actor.animDelay
import com.finansoviy.gurochek.game.utils.actor.animHide
import com.finansoviy.gurochek.game.utils.actor.animShow
import com.finansoviy.gurochek.game.utils.actor.setOnClickListener
import com.finansoviy.gurochek.game.utils.actor.setOnTouchListener
import com.finansoviy.gurochek.game.utils.advanced.AdvancedMainGroup
import com.finansoviy.gurochek.game.utils.font.FontParameter
import com.finansoviy.gurochek.game.utils.gdxGame

class AMainCategory(
    override val screen: CategoryScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "%")
    private val font_93   = screen.fontGenerator_Bold.generateFont(parameter.setSize(93))
    private val ls_93     = Label.LabelStyle(font_93, Color.WHITE)

    private val imgCatg   = Image(gdxGame.assetsAll.catg)
    private val listItems = List(5) { Image(gdxGame.assetsAll.listItems[it]) }
    private val listLbls  = List(5) { Label("0%", ls_93) }

    private val tmpGroup = ATmpGroup(screen)
    private val scroll   = ScrollPane(tmpGroup)

    override fun addActorsOnGroup() {
        color.a = 0f


        addAndFillActor(scroll)
        tmpGroup.setSize(width, 2300f)

        addImgCatg()
        addListItems()
        addListLbls()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgCatg() {
        tmpGroup.addActor(imgCatg)
        imgCatg.setBounds(344f, 2064f, 312f, 75f)
    }

    private fun addListItems() {
        var ny = 1670f
        listItems.onEachIndexed { index, img ->
            tmpGroup.addActor(img)
            img.setBounds(66f, ny, 869f, 342f)

            ny -= 53 + 342

            img.setOnTouchListener {
                CategoryScreen.CURRENT_QUIZ_INDEX = index

                screen.hideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }

    private fun addListLbls() {
        var ny = 1731f
        listLbls.onEachIndexed { index, lbl ->
            tmpGroup.addActor(lbl)
            lbl.setBounds(146f, ny, 145f, 75f)
            ny -= 320 + 75

            val percentValue = when(index) {
                0 -> gdxGame.ds_Percent.flow.value.quiz1
                1 -> gdxGame.ds_Percent.flow.value.quiz2
                2 -> gdxGame.ds_Percent.flow.value.quiz3
                3 -> gdxGame.ds_Percent.flow.value.quiz4
                4 -> gdxGame.ds_Percent.flow.value.quiz5
                else -> 0
            }

            lbl.setText("$percentValue%")
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