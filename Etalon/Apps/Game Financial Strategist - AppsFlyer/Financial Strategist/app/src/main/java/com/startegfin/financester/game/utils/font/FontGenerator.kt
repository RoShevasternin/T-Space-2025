package com.startegfin.financester.game.utils.font

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable
import com.startegfin.financester.game.utils.disposeAll

class FontGenerator(fontPath: FontPath): FreeTypeFontGenerator(Gdx.files.internal(fontPath.path)) {

    private val disposableSet = mutableSetOf<Disposable>()

    override fun generateFont(parameter: FreeTypeFontParameter?): BitmapFont {
        val font = super.generateFont(parameter)
        disposableSet.add(font)
        return font
    }

    override fun dispose() {
        super.dispose()
        disposableSet.disposeAll()
        disposableSet.clear()
    }

    companion object {
        enum class FontPath(val path: String) {
            NunitoSans_10pt_Bold     ("font/NunitoSans_10pt-Bold.ttf"),
            NunitoSans_10pt_ExtraBold("font/NunitoSans_10pt-ExtraBold.ttf"),
            NunitoSans_10pt_Medium   ("font/NunitoSans_10pt-Medium.ttf"),
            NunitoSans_10pt_SemiBold ("font/NunitoSans_10pt-SemiBold.ttf"),
        }
    }

}