package com.gmail.appverstas.privatetaskmanagerapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmail.appverstas.privatetaskmanagerapp.data.entities.Task

/**
 *Veli-Matti Tikkanen, 25.7.2021
 */
@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks_table WHERE id = :taskID")
    suspend fun deleteTaskByID(taskID: String)

    @Query("SELECT * FROM tasks_table WHERE id = :taskID")
    fun observeTaskByID(taskID: String): LiveData<Task>

    @Query("SELECT * FROM tasks_table ORDER BY date ASC")
    fun getAllTasks(): LiveData<List<Task>>



}