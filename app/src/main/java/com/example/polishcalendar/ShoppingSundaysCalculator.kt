package com.example.polishcalendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.temporal.TemporalAdjusters

class ShoppingSundaysCalculator {
    companion object {
        private val listOfMonths = listOf(Month.JANUARY, Month.APRIL, Month.JUNE, Month.AUGUST)
        fun shoppingSundays(year: Int): List<LocalDate> {
            val listOfShoppingSundays = mutableListOf<LocalDate>()

            for (month in listOfMonths) {
                listOfShoppingSundays.add(getMonthlyShoppingSunday(year, month))
            }
            listOfShoppingSundays.addAll(
                listOf(
                    getSpecialShoppingSunday(HolidayCalculator.calculateEaster(year)),
                    getSpecialShoppingSunday(LocalDate.of(year, 12, 25)),
                    getSpecialShoppingSunday(LocalDate.of(year, 12, 25).minusDays(7))
                )
            )

            return listOfShoppingSundays.sorted()
        }

        private fun getMonthlyShoppingSunday(year: Int, month: Month): LocalDate {
            return LocalDate.of(
                year,
                month,
                month.length(false) // It doesn't change anything if it is leap year.
            )
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        }

        private fun getSpecialShoppingSunday(date: LocalDate): LocalDate {
            return date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
        }
    }
}