package com.gmail.appverstas.privatetaskmanagerapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.appverstas.privatetaskmanagerapp.data.entities.Task

/**
 *Veli-Matti Tikkanen, 25.7.2021
 */

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun taskDao(): TaskDao

}