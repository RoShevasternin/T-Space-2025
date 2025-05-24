package com.startegfin.financester.game.global

object Balance {

    private const val MAX_SUM = 99_999_999

    var balance = 0
        get() = if (field <= MAX_SUM) field else MAX_SUM

    var balanceUsed = 0

}

private var tmpStringBuilder = StringBuilder()

val Int.toBalance: String get() {
    tmpStringBuilder = StringBuilder(this.toString())

    when(tmpStringBuilder.length) {
        4 -> tmpStringBuilder.insert(1, ' ')
        5 -> tmpStringBuilder.insert(2, ' ')
        6 -> tmpStringBuilder.insert(3, ' ')
        7 -> {
            tmpStringBuilder.insert(1, ' ')
            tmpStringBuilder.insert(5, ' ')
        }
        8 -> {
            tmpStringBuilder.insert(2, ' ')
            tmpStringBuilder.insert(6, ' ')
        }
    }
    return tmpStringBuilder.toString()
}