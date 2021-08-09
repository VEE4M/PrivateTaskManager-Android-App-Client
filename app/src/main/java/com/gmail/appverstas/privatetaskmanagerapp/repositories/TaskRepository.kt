package com.gmail.appverstas.privatetaskmanagerapp.repositories

import com.gmail.appverstas.privatetaskmanagerapp.data.TaskDao
import com.gmail.appverstas.privatetaskmanagerapp.data.entities.Task
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 25.7.2021
 */


class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {


    val getAllTasks = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun deleteTaskByID(taskID: String) = taskDao.deleteTaskByID(taskID)

    fun observeTaskByID(taskID: String) = taskDao.observeTaskByID(taskID)




}