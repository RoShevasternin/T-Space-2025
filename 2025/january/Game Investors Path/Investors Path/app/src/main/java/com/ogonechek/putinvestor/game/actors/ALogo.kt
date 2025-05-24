package com.ogonechek.putinvestor.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedGroup
import com.ogonechek.putinvestor.game.utils.advanced.AdvancedScreen
import com.ogonechek.putinvestor.game.utils.gdxGame
import com.ogonechek.putinvestor.util.log

class ALogo(override val screen: AdvancedScreen): AdvancedGroup() {

    private val logo  = Image(gdxGame.assetsLoader.logo)
    private val title = Image(gdxGame.assetsLoader.title)

    override fun addActorsOnGroup() {
        addActors(logo, title)
        logo.setBounds(0f, 0f, 222f, 306f)
        title.setBounds(254f, 12f, 706f, 254f)
    }

}