package com.example.agecalculator.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.agecalculator.databinding.CardBornDayBinding
import com.example.agecalculator.model.AgeCard

class BornDayVH(
    private val binding: CardBornDayBinding
) : RecyclerView.ViewHolder(binding.root
) {
    fun bind(item: AgeCard.BornDay) {
        binding.tvBornDay.text = item.dayName
    }

}