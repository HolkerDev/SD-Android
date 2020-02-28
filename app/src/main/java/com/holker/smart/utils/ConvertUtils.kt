package com.holker.smart.utils

import java.util.*

class ConvertUtils {
    companion object {
        fun dateToString(date: Date): String {
            return "${date.date}-${date.month} ${date.time}"
        }

        fun dateToTimeString(date: Date): String {
            val hours = getLeadingZero(date.hours)
            val minutes = getLeadingZero(date.minutes);
            return "${hours}:$minutes"
        }


        fun dateToDateString(date: Date): String {
            val month = getLeadingZero(date.month + 1);
            val day = getLeadingZero(date.date);
            return "${month}-${day}"
        }

        private fun getLeadingZero(number: Int): String {
            return if (number in 0..9) {
                "0$number"
            } else {
                "$number"
            }
        }
    }

}