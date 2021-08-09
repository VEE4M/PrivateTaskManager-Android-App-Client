package com.gmail.appverstas.privatetaskmanagerapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 *Veli-Matti Tikkanen, 25.7.2021
 */
@Entity(tableName = "tasks_table")
data class Task(
    val title: String,
    val content: String,
    val date: Long,
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString())