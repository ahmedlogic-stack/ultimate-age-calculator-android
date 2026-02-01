package com.example.agecalculator.adapter

import com.example.agecalculator.model.PlanetAgeModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.agecalculator.R
import com.example.agecalculator.databinding.ItemPlanetAgeBinding


class PlanetAgeAdapter(private val planetList: List<PlanetAgeModel>) :
    RecyclerView.Adapter<PlanetAgeAdapter.PlanetVH>() {

    class PlanetVH(val binding: ItemPlanetAgeBinding) : RecyclerView.ViewHolder(binding.root)

    // Map planet names to colors
    private val planetColors = mapOf(
        "Mercury" to R.color.planet_mercury,
        "Venus" to R.color.planet_venus,
        "Earth" to R.color.planet_earth,
        "Mars" to R.color.planet_mars,
        "Jupiter" to R.color.planet_jupiter,
        "Saturn" to R.color.planet_saturn,
        "Uranus" to R.color.planet_uranus,
        "Neptune" to R.color.planet_neptune)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetVH {
        val binding = ItemPlanetAgeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanetVH(binding)
    }

    override fun onBindViewHolder(holder: PlanetVH, position: Int) {
        val planet = planetList[position]
        with(holder.binding) {
            tvPlanetName.text = planet.name
            imgPlanet.setImageResource(planet.imageRes)
            tvDaysValue.text = planet.daysAge.toInt().toString()
            tvYearsValue.text = String.format("%.2f", planet.yearsAge)
            // Set background color of planet name header
            val colorRes = planetColors[planet.name] ?: R.color.planet_earth
            tvPlanetName.setBackgroundColor(ContextCompat.getColor(root.context, colorRes))
        }
    }

    override fun getItemCount() = planetList.size
}
