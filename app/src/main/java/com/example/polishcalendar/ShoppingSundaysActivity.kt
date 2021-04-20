package com.example.polishcalendar

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ShoppingSundaysActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sundays)

        val numberPicker = findViewById<NumberPicker>(R.id.yearPicker)

        if (numberPicker != null) {
            numberPicker.minValue = 2020
            numberPicker.maxValue = 2200
            numberPicker.wrapSelectorWheel = true
            numberPicker.value = Calendar.getInstance().get(Calendar.YEAR)
            setAdapter(numberPicker.value)
            numberPicker.setOnValueChangedListener { _, _, newYear ->
                setAdapter(newYear)
            }
        }
    }

    private fun setAdapter(year: Int) {
        findViewById<ListView>(R.id.shoppingSundays).adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            ShoppingSundaysCalculator.shoppingSundays(year)
        )
    }
}