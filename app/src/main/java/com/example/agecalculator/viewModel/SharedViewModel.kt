package com.example.agecalculator.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val name = MutableLiveData<String>()
    val dob = MutableLiveData<String>()
    val age = MutableLiveData<String>()
}