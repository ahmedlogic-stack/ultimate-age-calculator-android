package com.example.agecalculator.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.agecalculator.data.AppDatabase
import com.example.agecalculator.data.HistoryRepository
import com.example.agecalculator.model.HistoryItem
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HistoryRepository
    val historyList: LiveData<List<HistoryItem>>

    init {
        val dao = AppDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(dao)
        historyList = repository.allHistory
    }

    fun addHistory(item: HistoryItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun deleteHistory(item: HistoryItem) = viewModelScope.launch {
        repository.delete(item)
    }


}
