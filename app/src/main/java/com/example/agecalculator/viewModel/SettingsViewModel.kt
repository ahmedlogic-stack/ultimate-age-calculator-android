package com.example.agecalculator.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.core.content.edit

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // Date Format
    private val _dateFormat = MutableLiveData<String>()
    val dateFormat: LiveData<String> = _dateFormat

    // Language
    private val _language = MutableLiveData<String>()
    val language: LiveData<String> = _language

    init {
        _dateFormat.value = prefs.getString("date_format", "DD/MM/YYYY")
        _language.value = prefs.getString("language", "en") // default to English
    }

    fun saveDateFormat(format: String) {
        prefs.edit { putString("date_format", format) }
        _dateFormat.value = format
    }

    fun saveLanguage(langCode: String) {
        prefs.edit { putString("language", langCode) }
        _language.value = langCode
    }
}
