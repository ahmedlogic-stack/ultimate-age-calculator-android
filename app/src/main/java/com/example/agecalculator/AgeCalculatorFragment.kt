package com.example.agecalculator

import AgeCardAdapter
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agecalculator.databinding.FragmentAgeCalculatorBinding
import com.example.agecalculator.model.AgeCard
import com.example.agecalculator.model.HistoryItem
import com.example.agecalculator.viewModel.AgeViewModel
import com.example.agecalculator.viewModel.HistoryViewModel
import com.example.agecalculator.viewModel.SharedViewModel
import java.time.LocalDate


class AgeCalculatorFragment : Fragment() {
    private lateinit var _binding: FragmentAgeCalculatorBinding
    private val binding get() = _binding!!
    private val viewModel: AgeViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val historyViewModel: HistoryViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgeCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSaveHistory.setOnClickListener {

            val name = sharedViewModel.name.value ?: "Unknown"
            val dob = sharedViewModel.dob.value ?: "Unknown"
            val age = sharedViewModel.age.value ?: return@setOnClickListener

            historyViewModel.addHistory(HistoryItem(name = name, dob = dob, age = age, timeStamp = System.currentTimeMillis()))

            findNavController().navigate(R.id.action_ageCalculatorFragment_to_historyFragment)
        }

        // Initialize RecyclerView and adapter
        val ageCardAdapter = AgeCardAdapter()
        binding.rvAge.adapter = ageCardAdapter
        binding.rvAge.layoutManager = LinearLayoutManager(requireContext())

        // Submit list of age cards to the adapter
        viewModel.ageCardList.observe(viewLifecycleOwner) { ageCards ->
            ageCardAdapter.submitList(ageCards)

            // Find AgeRightNow card
            val ageNowCard = ageCards.find { it is AgeCard.AgeRightNow } as? AgeCard.AgeRightNow
                ?: return@observe

            // Convert to readable age string
            val ageText = "${ageNowCard.years} Years ${ageNowCard.months} Months ${ageNowCard.days} Days"

            // Save to SharedViewModel
            sharedViewModel.age.value = ageText

        }
        // Get the date of birth from the arguments
        val dobString = arguments?.getString("dob") ?: "01/01/2000"
        val parts = dobString.split("/").map { it.toInt() }
        val dob = LocalDate.of(parts[2], parts[1], parts[0])

        viewModel.calculateAllCards(dob)









    }
}