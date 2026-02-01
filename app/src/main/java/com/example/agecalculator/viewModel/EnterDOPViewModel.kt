package com.example.agecalculator.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnterDOPViewModel: ViewModel() {
    private val _dob = MutableLiveData<String>()
    val dob: LiveData<String> = _dob


    fun setDOB(date: String ) {
        _dob.value = date
    }


}
