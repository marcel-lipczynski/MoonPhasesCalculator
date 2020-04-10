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
    lateinit var southImages: IntArray
    lateinit var northImages: IntArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        northImages = calculator.getImagesNorth()
        southImages = calculator.getImagesSouth()

    }

    override fun onStart() {
        super.onStart()

        val todaysDate = Calendar.getInstance()


        if (currentAlgorithm == "Trig1") {
            currentMoonPhase = calculator.Trig1(
                todaysDate.get(Calendar.YEAR),
                todaysDate.get(Calendar.MONTH) + 1,
                todaysDate.get(Calendar.DATE)
            )
        } else if (currentAlgorithm == "Trig2") {
            currentMoonPhase = calculator.Trig2(
                todaysDate.get(Calendar.YEAR),
                todaysDate.get(Calendar.MONTH) + 1,
                todaysDate.get(Calendar.DATE)
            )
        } else if (currentAlgorithm == "Conway") {
            currentMoonPhase = calculator.Conway(
                todaysDate.get(Calendar.YEAR),
                todaysDate.get(Calendar.MONTH) + 1,
                todaysDate.get(Calendar.DATE)
            )
        } else {
            currentMoonPhase = calculator.Simple(
                todaysDate.get(Calendar.YEAR),
                todaysDate.get(Calendar.MONTH),
                todaysDate.get(Calendar.DATE)
            )
        }


        val nextNewMoon = calculator.daysTillNextNewMoon()

        val previousNewMoon = calculator.getPreviousNewMoon()
        val previousNewMoonFormatted =
            "Poprzedni nów " + calculator.dateFormat.format(previousNewMoon.time) + " r."

        val nextFullMoon = calculator.getNextFullMoon()
        val nextFullMoonFormatted =
            "Następna pełnia " + calculator.dateFormat.format(nextFullMoon.time) + " r."

        val phasePercentage =
            "Dzisiaj: " + (100 * (currentMoonPhase) / (nextNewMoon + currentMoonPhase)) + "%"

        todaysPhasePercentage.text = phasePercentage
        previousNewMoonText.text = previousNewMoonFormatted
        nextFullMoonText.text = nextFullMoonFormatted



        if (currentHemisphere == "S") {
            currentPhaseImage.setImageResource(southImages[currentMoonPhase])
        } else {
            currentPhaseImage.setImageResource(northImages[currentMoonPhase])
        }
//
    }


    fun onFullMoonListButtonClick(view: View) {
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

    private fun showFullMoonListActivity() {
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
