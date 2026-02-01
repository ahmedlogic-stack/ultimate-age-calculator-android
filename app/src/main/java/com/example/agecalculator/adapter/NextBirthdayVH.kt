package com.example.agecalculator.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.agecalculator.databinding.CardNextBirthdayBinding
import com.example.agecalculator.model.AgeCard

class NextBirthdayVH(
    private val binding: CardNextBirthdayBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AgeCard.NextBirthday) = with(binding) {
        tvNextMonthsValue.text = item.months.toString()
        tvNextDaysValue.text = item.days.toString()
        tvNextDayNameValue.text = item.dayName
    }
}
