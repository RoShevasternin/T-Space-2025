package com.startegfin.financester.game.global

import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil {

    val currentDate: String
        get() {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return dateFormat.format(Date())
        }

}