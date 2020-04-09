package com.example.moonphases

import MoonPhaseCalculator
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE = 997

    private val calculator: MoonPhaseCalculator = MoonPhaseCalculator()
    var currentMoonPhase: Int = 0
    var currentHemisphere: String = "N"
    var currentAlgorithm: String = "Trig1"


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

        val nextNewMoon = calculator.daysTillNextNewMoon()

        val previousNewMoon = calculator.getPreviousNewMoon()
        val previousNewMoonFormatted =
            "Poprzedni nów " + calculator.dateFormat.format(previousNewMoon.time) + " r."

        val nextFullMoon = calculator.getNextFullMoon()
        val nextFullMoonFormatted =
            "Następna pełnia " + calculator.dateFormat.format(nextFullMoon.time) + " r."

//        val daysBetweenNewMoons = ceil((nextNewMoon.timeInMillis - previousNewMoon.timeInMillis).toDouble() / (1000 * 60 * 60 * 24))

        //nextNewMoon - przechowuje wartość dni do następnego nowiu, currentMoonPhase ile dni upłynęło od ostatniego nowiu razem
        // dają ilość dni pomiędzy następnym i kolejnym nowiem
        val phasePercentage =
            "Dzisiaj: " + (100 * (currentMoonPhase) / (nextNewMoon + currentMoonPhase)) + "%"

        todaysPhasePercentage.text = phasePercentage
        previousNewMoonText.text = previousNewMoonFormatted
        nextFullMoonText.text = nextFullMoonFormatted

        //setting picture


//        It is needed to change resource of the photo!!!!
//        currentPhaseImage.setImageResource(R.drawable.s47_4p)

    }



    fun onFullMoonListButtonClick(view: View){
        showFullMoonListActivity()
    }

    fun onSettingsButtonClick(view: View) {
        showSettingsActivity()
    }

    private fun showSettingsActivity() {
        val i = Intent(this, Settings::class.java)
        i.putExtra("hemisphere", currentHemisphere)
        i.putExtra("algorithm", currentAlgorithm)
        startActivityForResult(i, REQUEST_CODE)
    }

    private fun showFullMoonListActivity(){
        val i = Intent(this, FullMoonList::class.java)
        i.putExtra("algorithm", currentAlgorithm)
        startActivity(i)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            if (data != null) {
                if (data.hasExtra("hemisphere") && data.hasExtra("algorithm")) {
                    currentAlgorithm = data.extras?.getString("algorithm").toString()
                    currentHemisphere = data.extras?.getString("hemisphere").toString()

                }
            }
        }
    }




}
