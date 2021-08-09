package com.gmail.appverstas.privatetaskmanagerapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.appverstas.privatetaskmanagerapp.data.entities.Task
import com.gmail.appverstas.privatetaskmanagerapp.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 26.7.2021
 */

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
    ): ViewModel() {

    val getAllTasks = taskRepository.getAllTasks

    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }

}