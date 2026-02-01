package com.example.agecalculator.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.agecalculator.databinding.HoroscopeBinding
import com.example.agecalculator.model.AgeCard


    class HoroscopeVH(
        private val binding: HoroscopeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AgeCard.Horoscope) = with(binding) {
            tvZodiacSign.text = item.zodiacSign
            tvEmoji.text = item.emoji
            tvZodiacTraits.text = item.traits
        }
    }

