package com.example.agecalculator
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.agecalculator.databinding.FragmentSettingsBinding
import com.example.agecalculator.viewModel.SettingsViewModel
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        // 🔙 Back button
        binding.btnBack.setOnClickListener {
          findNavController().navigateUp()
        }

        // 🌐 Language
        binding.optionLanguage.setOnClickListener {
           showLanguageDialog()
        }

        // 📅 Change Date Format
        binding.optionFormat.setOnClickListener {
            showFormatDialog()
        }

        // 📤 Share App
        binding.optionShare.setOnClickListener {
            shareApp()
        }

        // ⭐ Rate App
        binding.optionRate.setOnClickListener {
            rateApp()
        }

        // 🔒 Privacy Policy
        binding.optionPrivacy.setOnClickListener {
            openLink("https://yourprivacylink.com")
        }

        // 📜 Terms & Conditions
        binding.optionTerms.setOnClickListener {
            openLink("https://yourtermslink.com")
        }

        // Observe LiveData if needed
        viewModel.dateFormat.observe(viewLifecycleOwner) { format ->
            // Update UI if you want to show current format somewhere
        }
    }

    private fun showFormatDialog() {
        val formats = arrayOf("DD/MM/YYYY", "MM/DD/YYYY", "YYYY/MM/DD")

        AlertDialog.Builder(requireContext())
            .setTitle("Select Date Format")
            .setItems(formats) { _, which ->
                viewModel.saveDateFormat(formats[which])
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    private fun showLanguageDialog() {
        val languages = arrayOf("English", "Urdu", "Spanish", "French") // add your languages

        // Get currently selected language
        val current = viewModel.language.value
        val checkedItem = languages.indexOf(current)

        AlertDialog.Builder(requireContext())
            .setTitle("Select Language")
            .setSingleChoiceItems(languages, checkedItem) { dialog, which ->
                viewModel.saveLanguage(languages[which])
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out this Age Calculator app: https://play.google.com/store/apps/details?id=${requireContext().packageName}"
            )
        }
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    private fun rateApp() {
        val uri = "market://details?id=${requireContext().packageName}".toUri()
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun openLink(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
