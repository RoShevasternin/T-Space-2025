package com.pezdunkov.sberdarorcassa.game.actors

import com.pezdunkov.sberdarorcassa.game.actors.button.AButton
import com.pezdunkov.sberdarorcassa.game.screens.S1Screen
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedGroup
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedScreen
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame

class ABtns(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val btnHome = AButton(screen, AButton.Type.Home)
    private val btnSave = AButton(screen, AButton.Type.Save)

    var blockSave = {}

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {
        addActors(btnHome, btnSave)
        btnHome.setBounds(0f, 30f, 312f, 94f)
        btnSave.setBounds(321f, 30f, 312f, 94f)

        btnHome.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.clearBackStack()
                gdxGame.navigationManager.navigate(S1Screen::class.java.name)
            }
        }

        btnSave.setOnClickListener {
            blockSave()
        }
    }

}