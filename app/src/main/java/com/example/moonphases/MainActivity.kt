package com.example.moonphases

import MoonPhaseCalculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val mapOfNorthPictures: Map<Int, String> = mapOf()

    var currentMoonPhase: Int = 0
    private val calculator: MoonPhaseCalculator = MoonPhaseCalculator()
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
    var currentHemisphere: String = "N"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        //months are counted from 0 dunno why
        val todaysDate = Calendar.getInstance()

        //Using Trig1 as a default function to calculate date on first screen
        currentMoonPhase = calculator.Conway(
            todaysDate.get(Calendar.YEAR),
            todaysDate.get(Calendar.MONTH) + 1,
            todaysDate.get(Calendar.DATE)
        )

        val nextNewMoon = daysTillNextNewMoon()

        val previousNewMoon = getPreviousNewMoon()
        val previousNewMoonFormatted = "Poprzedni nów " + dateFormat.format(previousNewMoon.time) + " r."

        val nextFullMoon = getNextFullMoon()
        val nextFullMoonFormatted = "Następna pełnia " + dateFormat.format(nextFullMoon.time) + " r."

//        val daysBetweenNewMoons = ceil((nextNewMoon.timeInMillis - previousNewMoon.timeInMillis).toDouble() / (1000 * 60 * 60 * 24))

        //nextNewMoon - przechowuje wartość dni do następnego nowiu, currentMoonPhase ile dni upłynęło od ostatniego nowiu razem
        // dają ilość dni pomiędzy następnym i kolejnym nowiem
        val phasePercentage = "Dzisiaj: " + (100*(currentMoonPhase)/(nextNewMoon+currentMoonPhase)) + "%"

        todaysPhasePercentage.text = phasePercentage
        previousNewMoonText.text = previousNewMoonFormatted
        nextFullMoonText.text = nextFullMoonFormatted

        //setting picture





//        It is needed to change resource of the photo!!!!
//        currentPhaseImage.setImageResource(R.drawable.s47_4p)

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


    private fun daysTillNextNewMoon(): Int {

        var i: Int = 1
        var previousNewMoon: Int
        var calendar: Calendar
        do {
            calendar = getDaysAfter(i)
            previousNewMoon = calculator.Conway(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE)
            )
            i++
        } while (previousNewMoon != 0)

        return i-1
    }


}
