package com.rugosbon.pybonesrus.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.rugosbon.pybonesrus.game.actors.button.AButton
import com.rugosbon.pybonesrus.game.screens.MenuScreen
import com.rugosbon.pybonesrus.game.screens.TestScreen
import com.rugosbon.pybonesrus.game.screens.TestSelectScreen
import com.rugosbon.pybonesrus.game.utils.Block
import com.rugosbon.pybonesrus.game.utils.GameColor
import com.rugosbon.pybonesrus.game.utils.TIME_ANIM_SCREEN
import com.rugosbon.pybonesrus.game.utils.actor.animDelay
import com.rugosbon.pybonesrus.game.utils.actor.animHide
import com.rugosbon.pybonesrus.game.utils.actor.animShow
import com.rugosbon.pybonesrus.game.utils.actor.setOnClickListener
import com.rugosbon.pybonesrus.game.utils.advanced.AdvancedMainGroup
import com.rugosbon.pybonesrus.game.utils.font.FontParameter
import com.rugosbon.pybonesrus.game.utils.gdxGame

class AMainTestSelect(
    override val screen: TestSelectScreen,
): AdvancedMainGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font51        = screen.fontGenerator_Ruberoid_Bold.generateFont(fontParameter.setSize(51))

    private val ls51 = Label.LabelStyle(font51, GameColor.black_21)

    private val imgLoga   = Image(gdxGame.assetsAll.loga)
    private val lblTitle  = Label("Тест", ls51)
    private val btnX      = AButton(screen, AButton.Type.X)
    private val imgSelect = Image(gdxGame.assetsAll.BUTTONS)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgLoga()
        addTitle()
        addImgSelector()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoga() {
        addActor(imgLoga)
        imgLoga.setBounds(65f, 1911f, 354f, 129f)
    }

    private fun addTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(422f, 1772f, 124f, 71f)

        addActor(btnX)
        btnX.apply {
            setBounds(847f, 1769f, 77f, 77f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
    }

    private fun addImgSelector() {
        addActor(imgSelect)
        imgSelect.setBounds(47f, 770f, 875f, 938f)

        val listBtn = List(4) { Actor() }
        var ny = 1381f

        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(96f, ny, 776f, 129f)
            ny -= 52 + 129

            btn.setOnClickListener(gdxGame.soundUtil) {
                TestSelectScreen.SELECTED_TEST_INDEX = index
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(TestScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }


    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        //screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        //screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}