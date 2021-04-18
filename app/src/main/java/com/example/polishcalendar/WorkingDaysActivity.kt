package com.example.polishcalendar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId

class WorkingDaysActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_working_days)

        val startDate = findViewById<DatePicker>(R.id.startDate)
        val endDate = findViewById<DatePicker>(R.id.endDate)

        if (startDate != null && endDate != null) {
            val minDate =
                LocalDate.of(1900, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                    .toEpochMilli()
            val maxDate =
                LocalDate.of(2200, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()
                    .toEpochMilli()
            startDate.minDate = minDate
            startDate.maxDate = maxDate
            endDate.minDate = minDate
            endDate.maxDate = maxDate
        }
        startDate.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val newStartDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
            val oldEndDate = LocalDate.of(endDate.year, endDate.month + 1, endDate.dayOfMonth)
            if (newStartDate > oldEndDate) {
                endDate.updateDate(year, monthOfYear, dayOfMonth)
            }
            setWorkingDays(getWorkingDays(newStartDate, LocalDate.of(endDate.year, endDate.month + 1, endDate.dayOfMonth)))
        }

        endDate.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val newEndDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
            val oldStartDate = LocalDate.of(startDate.year, startDate.month + 1, startDate.dayOfMonth)
            if (newEndDate < oldStartDate) {
                startDate.updateDate(year, monthOfYear, dayOfMonth)
            }
            setWorkingDays(getWorkingDays(LocalDate.of(startDate.year, startDate.month + 1, startDate.dayOfMonth), newEndDate))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setWorkingDays(days: Long) {
        findViewById<TextView>(R.id.workingDaysText).text = "Liczba dni roboczych: $days"
    }

    private fun getWorkingDays(startDate: LocalDate, endDate: LocalDate): Long {
        var mutableStartDate = startDate
        val holidays = mutableSetOf<LocalDate>()
        for (year in startDate.year..endDate.year) {
            holidays.addAll(HolidayCalculator.getBankingHolidays(year))
        }
        var businessDays = 0L
        while (mutableStartDate < endDate) {
            val dayOfWeek = mutableStartDate.dayOfWeek
            if (!holidays.contains(mutableStartDate) &&
                dayOfWeek != DayOfWeek.SATURDAY &&
                dayOfWeek != DayOfWeek.SUNDAY
            ) {
                businessDays++
            }
            mutableStartDate = mutableStartDate.plusDays(1)
        }
        return businessDays
    }
}