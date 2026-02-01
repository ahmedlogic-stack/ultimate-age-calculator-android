package com.example.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.agecalculator.databinding.FragmentEnterDOPBinding
import com.example.agecalculator.viewModel.EnterDOPViewModel
import com.example.agecalculator.viewModel.SharedViewModel
import java.util.Calendar


class EnterDOPFragment : Fragment() {

    private  var _binding: FragmentEnterDOPBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EnterDOPViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEnterDOPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatePickers()
        observeViewModel()
        setupButtons()
        backButton()


    }

    private fun setupDatePickers() {
        binding.etDob.setOnClickListener {
            val calender = Calendar.getInstance()
            val year = calender.get(Calendar.YEAR)
            val month = calender.get(Calendar.MONTH)
            val day = calender.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(
                requireContext(),
                        R.style.CustomDatePickerDialog,

                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth/${month + 1}/$year"
                    viewModel.setDOB(selectedDate)
                }, year, month, day

            )
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()


        }



    }
    private fun observeViewModel() {
        viewModel.dob.observe(viewLifecycleOwner) { dob ->
            binding.etDob.setText(dob)
        }
    }
    private fun setupButtons() {
        binding.calculateButton.setOnClickListener {
            val dob = viewModel.dob.value ?: return@setOnClickListener

            // Pass DOB via Bundle
            val bundle = Bundle()
            bundle.putString("dob", dob)
            sharedViewModel.dob.value = binding.etDob.text.toString()
            findNavController().navigate(R.id.action_enterDOPFragment_to_ageCalculatorFragment, bundle)
        }
    }
    private fun backButton() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
