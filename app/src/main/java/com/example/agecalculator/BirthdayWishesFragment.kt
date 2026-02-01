package com.example.agecalculator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agecalculator.adapter.BirthdayWishesAdapter
import com.example.agecalculator.databinding.FragmentBirthdayWishesBinding
import com.example.agecalculator.viewModel.BirthdayWishesViewModel

class BirthdayWishesFragment : Fragment() {

    private var _binding: FragmentBirthdayWishesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BirthdayWishesViewModel by viewModels()
    private lateinit var adapter: BirthdayWishesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirthdayWishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        adapter = BirthdayWishesAdapter { wish ->
            val bundle = Bundle().apply {
                putString("relation", wish.relation)
                putString("message", wish.message)
            }
            findNavController().navigate(
                com.example.agecalculator.R.id.action_birthdayWishesFragment_to_birthdayWishesDetailFragment,
                bundle
            )
        }

        binding.rvWishes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWishes.adapter = adapter

        viewModel.wishes.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

