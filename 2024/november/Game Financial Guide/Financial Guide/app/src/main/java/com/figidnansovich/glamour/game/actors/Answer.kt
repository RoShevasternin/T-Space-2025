package com.figidnansovich.glamour.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.figidnansovich.glamour.game.utils.actor.setOnClickListener
import com.figidnansovich.glamour.game.utils.advanced.AdvancedGroup
import com.figidnansovich.glamour.game.utils.advanced.AdvancedScreen

class Answer(
    override val screen: AdvancedScreen,
    private val isTrue: Boolean
) : AdvancedGroup() {

    private val imgBtn = Image(screen.game.all.def)

    var blockTrue = {}
    var blockClick = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgBtn)

        imgBtn.setOnClickListener(screen.game.soundUtil) {
            blockClick()
            imgBtn.drawable = TextureRegionDrawable(if (isTrue) {
                screen.game.soundUtil.apply { play(bonus, 0.5f) }
                blockTrue()
                screen.game.all._true
            } else {
                screen.game.soundUtil.apply { play(fail, 0.5f) }
                screen.game.all._false
            })
        }
    }

    fun toDEF() {
        imgBtn.drawable = TextureRegionDrawable(screen.game.all.def)
    }

}