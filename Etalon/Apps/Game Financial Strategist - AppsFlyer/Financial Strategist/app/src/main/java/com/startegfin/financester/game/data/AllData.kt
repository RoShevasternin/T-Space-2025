package com.startegfin.financester.game.data

import kotlinx.serialization.Serializable

@Serializable
data class Roshod(
    override val uid: Long,
    override val suma: Int,
    override val date: String,
    override val categoryIndex: Int,
    override val description: String,
): Transaction()

@Serializable
data class Dohod(
    override val uid: Long,
    override val suma: Int,
    override val date: String,
    override val categoryIndex: Int,
    override val description: String,
): Transaction()

abstract class Transaction {
    abstract val uid: Long
    abstract val suma: Int
    abstract val date: String
    abstract val categoryIndex: Int
    abstract val description: String

    fun getType(): TransactionType = when (this) {
        is Roshod -> TransactionType.ROSHOD
        is Dohod  -> TransactionType.DOHOD
        else      -> TransactionType.ROSHOD
    }
}

enum class TransactionType {
    ROSHOD, DOHOD
}
