package com.fincarable.kapletaloverno.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.fincarable.kapletaloverno.game.LibGDXGame
import com.fincarable.kapletaloverno.game.actors.AButton
import com.fincarable.kapletaloverno.game.actors.AChooseColor_BWB
import com.fincarable.kapletaloverno.game.actors.AFactoryProgress
import com.fincarable.kapletaloverno.game.utils.TIME_ANIM
import com.fincarable.kapletaloverno.game.utils.actor.animHide
import com.fincarable.kapletaloverno.game.utils.actor.animShow
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedScreen
import com.fincarable.kapletaloverno.game.utils.advanced.AdvancedStage
import com.fincarable.kapletaloverno.game.utils.font.FontParameter
import com.fincarable.kapletaloverno.game.utils.region
import com.fincarable.kapletaloverno.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Factory_1_Screen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var INTERIOR_INDEX = 0
            private set
    }

    private var TAP_COUNT  = 2
    private val percentTap = 100f / TAP_COUNT

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(42)
    private val font          = fontGenerator_Pusia.generateFont(fontParameter)
    private val ls            = LabelStyle(font, Color.WHITE)

    private val lblTap = Label("Нажмите $TAP_COUNT раз", ls)

    private val aProgress  = AFactoryProgress(this)
    private val aChooseColor     = AChooseColor_BWB(this)
    private val imgFactory = Image(game.all.listT_1[INTERIOR_INDEX])
    private val btnCreate  = AButton(this, AButton.Type.Create)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loader.background_1.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(aProgress, aChooseColor, imgFactory, btnCreate, lblTap)
        aProgress.setBounds(158f,1728f,560f,125f)
        aChooseColor.setBounds(147f,1255f,583f,352f)
        aChooseColor.apply {
            check(INTERIOR_INDEX)
            blockSelector = { index ->
                INTERIOR_INDEX = index
                imgFactory.drawable = TextureRegionDrawable(game.all.listT_1[index])
            }
        }
        imgFactory.setBounds(29f,515f,819f,482f)
        btnCreate.setBounds(157f,148f,563f,128f)

        btnCreate.setOnClickListener(null) {
            game.soundUtil.apply { play(game.soundUtil.listS.random()) }

            btnCreate.disable()
            aProgress.progressPercentFlow.value += percentTap
            TAP_COUNT--
            lblTap.setText("Нажмите $TAP_COUNT раз")

            coroutine?.launch {
                delay(400)
                runGDX {
                    if (TAP_COUNT <= 0) {
                        game.soundUtil.apply { play(success, 0.2f) }

                        root.animHide(TIME_ANIM) {
                            game.navigationManager.navigate(Factory_2_Screen::class.java.name)
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