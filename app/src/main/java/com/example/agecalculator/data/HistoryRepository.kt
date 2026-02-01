package com.example.agecalculator.data

import androidx.lifecycle.LiveData
import com.example.agecalculator.model.HistoryItem

class HistoryRepository(private val historyDao: HistoryDao) {

    val allHistory: LiveData<List<HistoryItem>> = historyDao.getAllHistory()

    suspend fun insert(item: HistoryItem) {
        historyDao.insert(item)
    }

    suspend fun delete(item: HistoryItem) {
        historyDao.delete(item)
    }


}
