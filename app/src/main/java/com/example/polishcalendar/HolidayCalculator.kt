package com.example.polishcalendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import kotlin.math.floor

class HolidayCalculator {
    companion object {

        fun calculateEaster(year: Int): LocalDate {
            val a = year % 19
            val b = floor((year / 100).toDouble())
            val c = year % 100
            val d = floor(b / 4)
            val e = b % 4
            val f = floor((b + 8) / 25)
            val g = floor((b - f + 1) / 3)
            val h = (19 * a + b - d - g + 15) % 30
            val i = floor((c / 4).toDouble())
            val k = c % 4
            val l = (32 + 2 * e + 2 * i - h - k) % 7
            val m = floor((a + 11 * h + 22 * l) / 451)
            val p = (h + l - 7 * m + 114) % 31
            val day = p + 1
            val month = floor(h + l - 7 * m + 114) / 31
            return LocalDate.of(year, month.toInt(), day.toInt())
        }

        fun calculateAshWednesday(easterDate: LocalDate): LocalDate {
            return easterDate.minusDays(46)
        }

        fun calculateFeastOfCorpusChristi(easterDate: LocalDate): LocalDate {
            return easterDate.plusDays(60)
        }

        fun calculateAdvent(year: Int): LocalDate {
            return LocalDate.of(year, 12, 24)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
                .minusWeeks(3)
        }
    }
}