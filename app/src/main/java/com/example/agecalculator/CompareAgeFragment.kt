package com.example.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.agecalculator.databinding.FragmentCompareAgeBinding
import com.example.agecalculator.viewModel.AgeCompareViewModel
import java.time.LocalDate


class CompareAgeFragment : Fragment() {

    private lateinit var binding: FragmentCompareAgeBinding
    private val viewModel: AgeCompareViewModel by viewModels()

    private var firstDob: LocalDate? = null
    private var secondDob: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        binding = FragmentCompareAgeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatePickers()
        observeViewModel()
        setupButtons()

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_compareAgeFragment_to_settingsFragment)
        }


    }

    private fun setupDatePickers() {
        binding.etFirstDOB.setOnClickListener {
            showDatePicker { date ->
                firstDob = date
                binding.etFirstDOB.setText(date.toString())
            }

        }

        binding.etSecondDOB.setOnClickListener {
            showDatePicker { date ->
                secondDob = date
                binding.etSecondDOB.setText(date.toString())
            }
        }
    }

    private fun showDatePicker(onDateSelected: (LocalDate) -> Unit) {
        val today = LocalDate.now()
       val datePicker = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
            { _, year, month, day ->
                onDateSelected(LocalDate.of(year, month + 1, day))
            },

            today.year,
            today.monthValue - 1,
            today.dayOfMonth
        )
        datePicker.show()
        datePicker.datePicker.maxDate = System.currentTimeMillis()
    }

    private fun setupButtons() {
        binding.btnCalculate.setOnClickListener {
            val name1 = binding.etFirstName.text.toString().trim()
            val name2 = binding.etSecondName.text.toString().trim()

            if (name1.isEmpty() || name2.isEmpty() || firstDob == null || secondDob == null) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()


                return@setOnClickListener
            }

            viewModel.calculateAgeDifference(name1, firstDob!!, name2, secondDob!!)
        }

        binding.btnReset.setOnClickListener {
            binding.etFirstName.text?.clear()
            binding.etSecondName.text?.clear()
            binding.etFirstDOB.text?.clear()
            binding.etSecondDOB.text?.clear()

        }
    }

    private fun observeViewModel() {
        viewModel.ageCompare.observe(viewLifecycleOwner) { result ->
            binding.tvDiffYears.text = result.years.toString()
            binding.tvDiffMonths.text = result.months.toString()
            binding.tvDiffDays.text = result.days.toString()

            binding.tvAgeComparison.text =
                "${result.olderPersonName} is ${result.years} years ${result.months} months and ${result.days} days older than ${result.youngerPersonName}"

        }
    }
}
