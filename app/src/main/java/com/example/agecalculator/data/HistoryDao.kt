package com.example.agecalculator.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.agecalculator.model.HistoryItem

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyItem: HistoryItem)

    @Delete
    suspend fun delete(item: HistoryItem)



    @Query("SELECT * FROM history_table ORDER BY id DESC")
    fun getAllHistory(): LiveData<List<HistoryItem>>
}
