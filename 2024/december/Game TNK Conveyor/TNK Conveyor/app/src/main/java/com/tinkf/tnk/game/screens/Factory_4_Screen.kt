package com.tinkf.tnk.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.tinkf.tnk.game.LibGDXGame
import com.tinkf.tnk.game.actors.AButton
import com.tinkf.tnk.game.actors.AChooseColor_BWB
import com.tinkf.tnk.game.actors.AChooseColor_RBG
import com.tinkf.tnk.game.actors.AFactoryProgress
import com.tinkf.tnk.game.utils.TIME_ANIM
import com.tinkf.tnk.game.utils.actor.animHide
import com.tinkf.tnk.game.utils.actor.animShow
import com.tinkf.tnk.game.utils.advanced.AdvancedScreen
import com.tinkf.tnk.game.utils.advanced.AdvancedStage
import com.tinkf.tnk.game.utils.font.FontParameter
import com.tinkf.tnk.game.utils.region
import com.tinkf.tnk.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Factory_4_Screen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var BODY_INDEX = 0
            private set
    }

    private var TAP_COUNT  = 5
    private val percentTap = 100f / TAP_COUNT

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(42)
    private val font          = fontGenerator_Pusia.generateFont(fontParameter)
    private val ls            = LabelStyle(font, Color.WHITE)

    private val lblTap = Label("Нажмите $TAP_COUNT раз", ls)

    private val aProgress    = AFactoryProgress(this)
    private val aChooseColor = AChooseColor_RBG(this)
    private val imgFactory   = Image(game.all.listT_4[BODY_INDEX])
    private val btnCreate    = AButton(this, AButton.Type.Create)

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
            check(BODY_INDEX)
            blockSelector = { index ->
                BODY_INDEX = index
                imgFactory.drawable = TextureRegionDrawable(game.all.listT_4[index])
            }
        }
        imgFactory.setBounds(29f,515f,819f,414f)
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
                            game.navigationManager.navigate(Factory_5_Screen::class.java.name)
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