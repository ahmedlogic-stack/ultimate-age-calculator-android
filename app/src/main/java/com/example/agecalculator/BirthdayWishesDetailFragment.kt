package com.example.agecalculator


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.agecalculator.databinding.FragmentBirthdayWishesDetailBinding


class BirthdayWishesDetailFragment : Fragment() {

    private var _binding: FragmentBirthdayWishesDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBirthdayWishesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnShare.setOnClickListener {
            val messageToShare = binding.tvFullMessage.text.toString()
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, messageToShare)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
        binding.btnCopy.setOnClickListener {
            val messageToCopy = binding.tvFullMessage.text.toString()
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Birthday Message", messageToCopy)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Message copied to clipboard", Toast.LENGTH_SHORT).show()


        }



        // Receive data from bundle
        val relation = arguments?.getString("relation") ?: "Birthday Wish"
        val message = arguments?.getString("message") ?: "No message found"

        binding.tvTitle.text = relation
        binding.tvFullMessage.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
