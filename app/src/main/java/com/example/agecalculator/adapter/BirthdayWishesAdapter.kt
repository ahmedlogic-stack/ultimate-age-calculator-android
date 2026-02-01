package com.example.agecalculator.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agecalculator.databinding.ItemBirthdayWishBinding
import com.example.agecalculator.model.BirthdayWishes

class BirthdayWishesAdapter(
    private val onClick: (BirthdayWishes) -> Unit
) : RecyclerView.Adapter<BirthdayWishesAdapter.WishViewHolder>() {

    private val items = mutableListOf<BirthdayWishes>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<BirthdayWishes>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        val binding = ItemBirthdayWishBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class WishViewHolder(private val binding: ItemBirthdayWishBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wish: BirthdayWishes) {
            binding.tvRelation.text = wish.relation
            binding.cardWish.setOnClickListener {
                onClick(wish)
            }
        }
    }
}
