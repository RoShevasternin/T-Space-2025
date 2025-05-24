package com.finansoviy.gurochek.game.data

data class DataQuiz(
    val title      : String,
    val time       : Int,
    val listQ      : List<String>,
    val listA      : List<List<String>>,
)
