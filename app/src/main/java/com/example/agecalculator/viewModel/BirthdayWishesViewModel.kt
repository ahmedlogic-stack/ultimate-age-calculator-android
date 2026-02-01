package com.example.agecalculator.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agecalculator.model.BirthdayWishes

class BirthdayWishesViewModel : ViewModel() {

    private val _wishes = MutableLiveData<List<BirthdayWishes>>()
    val wishes: LiveData<List<BirthdayWishes>>  = _wishes

    init {
        loadWishes()
    }

    private fun loadWishes() {
        val list = listOf(
            BirthdayWishes("Birthday Message For Mother", "Happy Birthday Mom! You are my heart and my strength."),
            BirthdayWishes("Birthday Message For Father", "Happy Birthday Dad! Thank you for everything."),
            BirthdayWishes("Birthday Message For Brother", "Happy Birthday Bro! Stay strong and happy."),
            BirthdayWishes("Birthday Message For Sister", "Happy Birthday Sis! Keep shining."),
            BirthdayWishes("Birthday Message For Wife", "Happy Birthday My Love! You are my world."),
            BirthdayWishes("Birthday Message For Husband", "Happy Birthday Dear Husband! My life is beautiful with you."),
            BirthdayWishes("Birthday Message For Daughter", "Happy Birthday Princess! Daddy loves you."),
            BirthdayWishes("Birthday Message For Friend", "Happy Birthday Bestie! Life is better with you.")
        )
        _wishes.value = list
    }
}
