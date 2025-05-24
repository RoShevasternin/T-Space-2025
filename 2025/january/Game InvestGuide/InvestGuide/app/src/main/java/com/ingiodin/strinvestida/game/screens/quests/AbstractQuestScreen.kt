package com.ingiodin.strinvestida.game.screens.quests

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.ingiodin.strinvestida.game.actors.AButton
import com.ingiodin.strinvestida.game.actors.Answer
import com.ingiodin.strinvestida.game.screens.Rating_10_Screen
import com.ingiodin.strinvestida.game.screens.Rating_1_Screen
import com.ingiodin.strinvestida.game.screens.Rating_5_Screen
import com.ingiodin.strinvestida.game.utils.*
import com.ingiodin.strinvestida.game.utils.actor.animHide
import com.ingiodin.strinvestida.game.utils.actor.animShow
import com.ingiodin.strinvestida.game.utils.actor.setOnClickListener
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedScreen
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedStage
import com.ingiodin.strinvestida.game.utils.font.FontParameter

abstract class AbstractQuestScreen : AdvancedScreen() {

    abstract val listTitle : List<String>
    abstract val listAnswer: List<List<String>>

    private val fontParameter = FontParameter()
    private val fontM_54 = fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC).setSize(54))
    private val fontR_37 = fontGenerator_Roboto_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC).setSize(37))

    private val ls54 = LabelStyle(fontM_54, Color.BLACK)
    private val ls37 = LabelStyle(fontR_37, GColor.text)

    private val imgLogo    by lazy { Image(game.loader.logo) }
    private val btnBack    by lazy { AButton(this, AButton.Type.Back) }
    private val btnNext    by lazy { AButton(this, AButton.Type.Next) }
    private val lblTitle   by lazy { Label(listTitle[0], ls54) }
    private val lblCount   by lazy { Label("1/6", ls54) }

    private val listLblAns by lazy { List(4) { Label(listAnswer[0][it], ls37) } }
    private val listBtnAns by lazy { List(4) { Answer(this, it == 0) } }

    // Field

    private val randomIndices get() = (0..3).shuffled()
    private val listLblY = listOf(1180f, 988f, 797f, 599f)
    private val listBtnY = listOf(1153f, 961f, 769f, 577f)

    private var currentIndex = 0
    private var counterTrue  = 0

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loader.B_Loader.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgLogo, lblTitle, btnBack, btnNext)
        imgLogo.setBounds(245f,1966f,521f,101f)
        lblTitle.setBounds(0f,1345f,1014f,165f)
        lblTitle.setAlignment(Align.center)
        lblTitle.wrap = true
        btnBack.apply {
            setBounds(54f, 237f, 109f, 109f)
            setOnClickListener(game.soundUtil) {
                if (currentIndex-1 >= 0) {
                    currentIndex -= 1
                    listLblAns.onEachIndexed { index, label -> label.setText(listAnswer[currentIndex][index]) }
                    lblTitle.setText(listTitle[currentIndex])
                    change()
                    if (counterTrue > 0) counterTrue -= 1
                } else {
                    root.animHide(TIME_ANIM) {
                        game.navigationManager.back()
                    }
                }
            }
        }
        btnNext.apply {
            setBounds(851f, 237f, 109f, 109f)
            setOnClickListener(game.soundUtil) {
                if (currentIndex + 1 <= 5) {
                    currentIndex += 1
                    listLblAns.onEachIndexed { index, label -> label.setText(listAnswer[currentIndex][index]) }
                    lblTitle.setText(listTitle[currentIndex])
                    change()
                } else {
                    root.animHide(TIME_ANIM) {
                        val screenName = when(counterTrue) {
                            in 0..2 -> Rating_1_Screen::class.java.name
                            in 3..4 -> Rating_5_Screen::class.java.name
                            else -> Rating_10_Screen::class.java.name
                        }
                        game.navigationManager.navigate(screenName)
                    }
                }
            }
        }
        addActor(lblCount)
        lblCount.apply {
            setBounds(465f, 403f, 83f, 38f)
            setAlignment(Align.center)
        }


        addListBtnAnswer()
        addListLblAnswer()
        change()
    }

    private fun AdvancedStage.addListLblAnswer() {
        listLblAns.onEach { lbl ->
            addActor(lbl)
            lbl.setSize(772f, 84f)
            lbl.disable()
            lbl.wrap = true
            lbl.setAlignment(Align.center)
            lbl.x = 121f
        }
    }

    private fun AdvancedStage.addListBtnAnswer() {
        listBtnAns.onEach { btn ->
            addActor(btn)
            btn.setSize(820f, 139f)
            btn.x = 97f

            btn.blockClick = {
                listBtnAns.onEach { it.disable() }
            }
            btn.blockTrue = {
                counterTrue += 1
            }
        }
    }

    // Logic ---------------------------------------------------------------------

    private fun change() {
        lblCount.setText("${currentIndex.inc()}/6")

        val indices = randomIndices

        listLblAns.onEachIndexed { index, label ->
            label.y = listLblY[indices[index]]
        }
        listBtnAns.onEachIndexed { index, btn ->
            btn.enable()
            btn.toDEF()
            btn.y = listBtnY[indices[index]]
        }
    }

}