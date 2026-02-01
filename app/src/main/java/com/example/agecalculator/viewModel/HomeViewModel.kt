package com.example.agecalculator.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel: ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

   init {
       //set today date by default
       val today = SimpleDateFormat("dd | MMMM | yyyy", Locale.getDefault()).format(Date())
       _date.value = today
   }

    //fun to handle button click
    private val _navigate = MutableLiveData<String>()
    val navigate: LiveData<String> = _navigate

    fun onEnterDOBClick() {
        _navigate.value = "enterDOPFragment"
    }

    fun onCompareAgeClick() {
        _navigate.value = "ageCompareFragment"
    }
    fun onBirthdayWishesClick() {
        _navigate.value = "birthdayWishesFragment"
    }
    fun onPlanetaryAgeClick() {
        _navigate.value = "planetaryAgeFragment"
    }
    fun onNavigationHandled() {
        _navigate.value = null
    }


}