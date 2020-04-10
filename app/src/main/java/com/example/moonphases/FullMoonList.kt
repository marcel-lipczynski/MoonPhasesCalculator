package com.example.moonphases

import MoonPhaseCalculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_full_moon_list.*
import java.util.*

class FullMoonList : AppCompatActivity() {

    private var calculator = MoonPhaseCalculator()
    private var usedAlgorithm: String = "Trig1"
    private var startingYear = "1900"
    private var listItems = arrayListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_moon_list)


        val extras = intent.extras ?: return
        usedAlgorithm = extras.getString("algorithm").toString()
        val currentlyUsedAlgorithm = "Aktualnie używany algorytm: $usedAlgorithm"
        algorithmInfo.text = currentlyUsedAlgorithm
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        fullMoonList.adapter = adapter
    }

    fun onClickAddYearButton(view: View) {

        val currentValue = yearInput.text.toString()
        if (currentValue == "") {
            yearInput.setText(startingYear)
        } else if (Integer.parseInt(currentValue) == 2200) {
            return
        } else {
            yearInput.setText((Integer.parseInt(currentValue) + 1).toString())
        }

    }

    fun onClickSubtractYearButton(view: View) {

        val currentValue = yearInput.text.toString()
        if (currentValue == "") {
            yearInput.setText(startingYear)
        } else if (Integer.parseInt(currentValue) == 1900) {
            return
        } else {
            yearInput.setText((Integer.parseInt(currentValue) - 1).toString())

        }

    }

    fun addItem(view: View) {

        if (yearInput.text.isEmpty()) {
            Toast.makeText(
                applicationContext, "Nie wprowadziłeś roku",
                Toast.LENGTH_SHORT
            ).show()
            return

        }

        listItems.clear()

        var currentMoonPhase: Int

        val currentYearValue = yearInput.text.toString()

        val currentYearValueInt = Integer.parseInt(currentYearValue)

        var firstDayOfGivenYear = Calendar.getInstance()
        firstDayOfGivenYear.set(Calendar.YEAR, currentYearValueInt)
        firstDayOfGivenYear.set(Calendar.DAY_OF_YEAR, 1)

        val lastDayOfGivenYear = Calendar.getInstance()
        lastDayOfGivenYear.set(Calendar.YEAR, currentYearValueInt)
        lastDayOfGivenYear.set(Calendar.MONTH, 11)
        lastDayOfGivenYear.set(Calendar.DAY_OF_MONTH, 31)

        if (currentYearValueInt < 1900 || currentYearValueInt > 2200) {
            Toast.makeText(
                applicationContext, "Wprowadź roku z zakresu 1900-2200",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            while (calculator.dateFormat.format(firstDayOfGivenYear.time) != calculator.dateFormat.format(
                    lastDayOfGivenYear.time
                )
            ) {


                if (usedAlgorithm == "Trig1") {
                    currentMoonPhase = calculator.Trig1(
                        firstDayOfGivenYear.get(Calendar.YEAR),
                        firstDayOfGivenYear.get(Calendar.MONTH) + 1,
                        firstDayOfGivenYear.get(Calendar.DATE)
                    )
                } else if (usedAlgorithm == "Trig2") {
                    currentMoonPhase = calculator.Trig2(
                        firstDayOfGivenYear.get(Calendar.YEAR),
                        firstDayOfGivenYear.get(Calendar.MONTH) + 1,
                        firstDayOfGivenYear.get(Calendar.DATE)
                    )

                } else if (usedAlgorithm == "Conway") {
                    currentMoonPhase = calculator.Conway(
                        firstDayOfGivenYear.get(Calendar.YEAR),
                        firstDayOfGivenYear.get(Calendar.MONTH) + 1,
                        firstDayOfGivenYear.get(Calendar.DATE)
                    )

                } else {
//                    currentMoonPhase = calculator.Simple(
//                        firstDayOfGivenYear.get(Calendar.YEAR),
//                        firstDayOfGivenYear.get(Calendar.MONTH) + 1,
//                        firstDayOfGivenYear.get(Calendar.DATE)
//                    )
                    return

                }

                if (currentMoonPhase == 15) {
                    listItems.add(calculator.dateFormat.format(firstDayOfGivenYear.time))
                    adapter.notifyDataSetChanged()
                }
                firstDayOfGivenYear.add(Calendar.DAY_OF_YEAR, 1)

            }
        }
    }

}




