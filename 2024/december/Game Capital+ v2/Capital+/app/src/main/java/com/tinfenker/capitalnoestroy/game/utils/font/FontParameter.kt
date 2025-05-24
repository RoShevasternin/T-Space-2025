package com.tinfenker.capitalnoestroy.game.utils.font

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

class FontParameter : FreeTypeFontParameter() {

    init {
        setLinear()
    }

    fun setLinear(): FontParameter {
        minFilter = Texture.TextureFilter.Linear
        magFilter = Texture.TextureFilter.Linear
        return this
    }
    fun setSize(size: Int): FontParameter {
        this.size = size
        return this
    }
    fun setCharacters(characters: CharType): FontParameter {
        this.characters = characters.chars
        return this
    }
    fun setCharacters(chars: String): FontParameter {
        this.characters = chars
        return this
    }

    enum class CharType(val chars: String) {
        NUMBERS("1234567890"),
        NUMBERS_RUB("1234567890 ₽"),
        ALL_LATIN("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,;:?!-'\"/()[]{}\\/@#\$%&*+=<>_^~|`°©®™§†∞µ\n"),
        CYRILLIC_ALL("₽ АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя0123456789.,;:?!-'\"/()[]{}\\/@#\$%&*+=<>_^~|`°©®™§†∞µ\n"),
        All(ALL_LATIN.chars+CYRILLIC_ALL.chars)
    }

}