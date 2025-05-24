package com.plannercap.pitalcamp.game.data

import kotlinx.serialization.Serializable

@Serializable
data class Desire(
    val uuid      : Long,
    val nameDesire: String,
    val summa     : Int,
)

@Serializable
data class Income(
    override val uuid      : Long,
    override val nameDesire: String,
    override val summa     : Int,
): AllData

@Serializable
data class Expense(
    override val uuid      : Long,
    override val nameDesire: String,
    override val summa     : Int,
): AllData

interface AllData {
    val uuid      : Long
    val nameDesire: String
    val summa     : Int
}
