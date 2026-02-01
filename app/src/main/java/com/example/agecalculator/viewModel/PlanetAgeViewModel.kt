package com.example.agecalculator.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agecalculator.R
import com.example.agecalculator.model.PlanetAgeModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class PlanetAgeViewModel : ViewModel() {

    private val _planetList = MutableLiveData<List<PlanetAgeModel>>()
    val planetList: LiveData<List<PlanetAgeModel>> = _planetList

    // Planet orbital periods in Earth days
    private val orbitalPeriods = mapOf(
        "Mercury" to 87.97,
        "Venus" to 224.70,
        "Earth" to 365.25,
        "Mars" to 686.98,
        "Jupiter" to 4332.59,
        "Saturn" to 10759.22,
        "Uranus" to 30685.4,
        "Neptune" to 60189.0
    )

    private val planetImages = mapOf(
        "Mercury" to R.drawable.mercury,
        "Venus" to R.drawable.venus,
        "Earth" to R.drawable.earth,
        "Mars" to R.drawable.mars,
        "Jupiter" to R.drawable.jupiter,
        "Saturn" to R.drawable.saturn,
        "Uranus" to R.drawable.uranus,
        "Neptune" to R.drawable.napturn
    )

    fun calculatePlanetAges(dob: LocalDate) {
        val today = LocalDate.now()
        val earthDaysOld = ChronoUnit.DAYS.between(dob, today).toDouble()

        val planets = orbitalPeriods.map { (name, periodDays) ->
            val yearsOnPlanet = earthDaysOld / periodDays
            PlanetAgeModel(
                name = name,
                imageRes = planetImages[name] ?: R.drawable.earth,
                daysAge = earthDaysOld / 1.0,  // Keep same days on all planets for now
                yearsAge = yearsOnPlanet
            )
        }
        _planetList.value = planets
    }
}
