package com.example.agecalculator.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agecalculator.model.AgeCompare
import java.time.LocalDate
import java.time.Period

class AgeCompareViewModel : ViewModel() {

    private val _ageCompare = MutableLiveData<AgeCompare>()
    val ageCompare: LiveData<AgeCompare> = _ageCompare



    fun calculateAgeDifference(
        name1: String,
        dob1: LocalDate,
        name2: String,
        dob2: LocalDate
    ) {
        val older: LocalDate
        val younger: LocalDate
        val olderName: String
        val youngerName: String

        if (dob1.isBefore(dob2)) {
            older = dob1
            younger = dob2
            olderName = name1
            youngerName = name2
        } else {
            older = dob2
            younger = dob1
            olderName = name2
            youngerName = name1
        }

        val period = Period.between(older, younger)

        _ageCompare.value = AgeCompare(
            years = period.years,
            months = period.months,
            days = period.days,
            olderPersonName = olderName,
            youngerPersonName = youngerName
        )
    }
}
