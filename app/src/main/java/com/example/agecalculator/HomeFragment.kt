// HomeFragment.kt
package com.example.agecalculator.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import com.example.agecalculator.databinding.FragmentHomeBinding
import com.example.agecalculator.viewModel.HomeViewModel
import com.example.agecalculator.viewModel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe user name
        viewModel.name.observe(viewLifecycleOwner) { name ->
            binding.etUserName.setText(name)
        }


        // Observe today date
        viewModel.date.observe(viewLifecycleOwner) { date ->
            binding.etTodayDate.setText(date)
        }
        setTodayDate()


        binding.btnCalculateAge.setOnClickListener {

            val name = binding.etUserName.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save name in SharedViewModel
            sharedViewModel.name.value = name

            // Trigger navigation ONLY ONCE
            viewModel.onEnterDOBClick()
        }

        binding.btnCompareAge.setOnClickListener { viewModel.onCompareAgeClick() }
        binding.btnBirthdayWishes.setOnClickListener { viewModel.onBirthdayWishesClick() }
        binding.btnPlanetaryAge.setOnClickListener { viewModel.onPlanetaryAgeClick() }

        // Navigation observer
        viewModel.navigate.observe(viewLifecycleOwner) { destination ->
            destination?.let {
                when (it) {
                    "enterDOPFragment" -> findNavController().navigate(com.example.agecalculator.R.id.action_home_to_enterDOPFragment)
                    "ageCompareFragment" -> findNavController().navigate(com.example.agecalculator.R.id.action_home_to_compareAgeFragment)
                    "birthdayWishesFragment" -> findNavController().navigate(com.example.agecalculator.R.id.action_home_to_birthdayFragment)
                    "planetaryAgeFragment" -> findNavController().navigate(com.example.agecalculator.R.id.action_home_to_planetaryFragment)
                }
                viewModel.onNavigationHandled()
            }
        }

        // Toolbar buttons
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(com.example.agecalculator.R.id.action_home_to_settingsFragment)
        }
        binding.btnHistory.setOnClickListener {
            findNavController().navigate(com.example.agecalculator.R.id.action_home_to_historyFragment)
        }
    }

    private fun setTodayDate(){
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        binding.etTodayDate.setText(currentDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
