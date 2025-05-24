package com.bagazkz.klarebadew.game.data

data class DataQuiz(
    val title      : String,
    val time       : Int,
    val description: String,
    val listQ      : List<String>,
    val listA      : List<List<String>>,
)
