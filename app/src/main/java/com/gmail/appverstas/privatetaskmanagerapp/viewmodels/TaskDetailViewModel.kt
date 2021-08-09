package com.gmail.appverstas.privatetaskmanagerapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.appverstas.privatetaskmanagerapp.data.entities.Task
import com.gmail.appverstas.privatetaskmanagerapp.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 3.8.2021
 */

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    fun observeTaskByID(taskID: String) = taskRepository.observeTaskByID(taskID)

    fun deleteTaskByID(taskID: String) = viewModelScope.launch {
        taskRepository.deleteTaskByID(taskID)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

}