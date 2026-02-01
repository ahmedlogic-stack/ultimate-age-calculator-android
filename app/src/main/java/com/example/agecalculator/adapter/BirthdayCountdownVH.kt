package com.example.agecalculator.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.agecalculator.databinding.CardBirthdayCountdownBinding
import com.example.agecalculator.model.AgeCard

class BirthdayCountdownVH(
    private val binding: CardBirthdayCountdownBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AgeCard.BirthdayCountdown) = with(binding) {
        tvMonthsValue.text = item.months.toString()
        tvDaysValue.text = item.days.toString()
        tvHoursValue.text = item.hours.toString()
        tvMinutesValue.text = item.minutes.toString()
        tvSecondsValue.text = item.seconds.toString()
    }
}
