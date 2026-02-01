package com.example.agecalculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history_table")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dob: String,
    val age: String,
    val timeStamp: Long
)
