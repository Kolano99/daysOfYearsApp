package com.example.polishcalendar

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.buttonSundays).setOnClickListener {
            val intent = Intent(this, ShoppingSundaysActivity::class.java)
            startActivity(intent)
        }
        val numberPicker = findViewById<NumberPicker>(R.id.yearPicker)
        if (numberPicker != null) {
            numberPicker.minValue = 1900
            numberPicker.maxValue = 2200
            numberPicker.wrapSelectorWheel = true
            numberPicker.value = Calendar.getInstance().get(Calendar.YEAR)
            setText(numberPicker.value)
            numberPicker.setOnValueChangedListener { _, _, newYear ->
                setText(newYear)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setText(year : Int) {
        val ashWednesday = findViewById<TextView>(R.id.ashWednesday)
        val easter = findViewById<TextView>(R.id.easter)
        val feastOfCorpusChristi = findViewById<TextView>(R.id.feastOfCorpusChristi)
        val advent = findViewById<TextView>(R.id.advent)
        val easterDate = HolidayCalculator.calculateEaster(year)
        ashWednesday.text = "Popielec: ${HolidayCalculator.calculateAshWednesday(easterDate)}"
        easter.text = "Wielkanoc: $easterDate"
        feastOfCorpusChristi.text = "Boże Ciało: ${HolidayCalculator.calculateFeastOfCorpusChristi(easterDate)}"
        advent.text = "Adwent: ${HolidayCalculator.calculateAdvent(year)}"
    }
}