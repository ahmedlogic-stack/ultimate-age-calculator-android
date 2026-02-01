package com.example.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agecalculator.adapter.PlanetAgeAdapter
import com.example.agecalculator.databinding.FragmentPlanetaryBinding
import com.example.agecalculator.viewModel.PlanetAgeViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PlanetaryFragment : Fragment() {

    private var _binding: FragmentPlanetaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlanetAgeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ===== BACK BUTTON =====
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // ===== DATE PICKER =====
        binding.etDob.setOnClickListener {
            val today = LocalDate.now()
            val dpd = DatePickerDialog(
                requireContext(),
                R.style.CustomDatePickerDialog,
                { _, year, month, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth) // month is 0-based
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    binding.etDob.setText(selectedDate.format(formatter))
                },
                today.year,
                today.monthValue - 1,
                today.dayOfMonth
            )
            dpd.datePicker.maxDate = System.currentTimeMillis()
            dpd.show()
        }

        // ===== RECYCLER VIEW =====
        val adapter = PlanetAgeAdapter(emptyList())
        binding.rvPlanets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlanets.adapter = adapter

        // ===== OBSERVE PLANET LIST =====
        viewModel.planetList.observe(viewLifecycleOwner) { planets ->
            binding.rvPlanets.adapter = PlanetAgeAdapter(planets)
        }

        // ===== CALCULATE BUTTON =====
        binding.btnCalculate.setOnClickListener {
            val dobStr = binding.etDob.text.toString()
            if (dobStr.isNotEmpty()) {
                try {
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val dob = LocalDate.parse(dobStr, formatter)
                    viewModel.calculatePlanetAges(dob)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Invalid Date Format!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please select DOB", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
