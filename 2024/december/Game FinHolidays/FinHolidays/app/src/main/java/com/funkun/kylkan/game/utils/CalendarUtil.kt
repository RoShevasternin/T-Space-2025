package com.funkun.kylkan.game.utils

import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {

    val currentDate: String
        get() {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return dateFormat.format(Date())
        }

}