package com.example.moonphases

import MoonPhaseCalculator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary.Words.LOCALE
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    var currentMoonPhase: Int = 0
    private val calculator: MoonPhaseCalculator = MoonPhaseCalculator()
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        //months are counted from 0 dunno why
        val todaysDate = Calendar.getInstance()
        currentMoonPhase = calculator.Conway(
            todaysDate.get(Calendar.YEAR),
            todaysDate.get(Calendar.MONTH) + 1,
            todaysDate.get(Calendar.DATE)
        )

        val previousNewMoon = getPreviousNewMoon().time
        val previousNewMoonFormatted = "Poprzedni nów " + dateFormat.format(previousNewMoon) + " r."

        val nextFullMoon = getNextFullMoon().time
        val nextFullMoonFormatted = "Następna pełnia " + dateFormat.format(nextFullMoon) + " r."

        previousNewMoonText.text = previousNewMoonFormatted
        nextFullMoonText.text = nextFullMoonFormatted
    }

    private fun getDaysAgo(daysAgo: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        return calendar
    }

    private fun getDaysAfter(getDaysAfter: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, getDaysAfter)
        return calendar
    }

    private fun getNextFullMoon(): Calendar {
        var i: Int = 1
        var nextFullMoon: Int
        var calendar: Calendar
        do {
            calendar = getDaysAfter(i)
            nextFullMoon = calculator.Conway(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE)
            )
            i++
        } while (nextFullMoon != 15)

        return calendar
    }

    private fun getPreviousNewMoon(): Calendar {

        var i: Int = 1
        var previousNewMoon: Int
        var calendar: Calendar
        do {
            calendar = getDaysAgo(i)
            previousNewMoon = calculator.Conway(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE)
            )
            i++
        } while (previousNewMoon != 0)

        return calendar
    }


}
