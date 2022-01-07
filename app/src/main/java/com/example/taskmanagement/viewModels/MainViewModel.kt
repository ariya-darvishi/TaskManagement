package com.example.taskmanagement.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.data.entities.relations.TaskWithSubTasks
import com.example.taskmanagement.data.entities.relations.TaskWithUsers
import com.example.taskmanagement.repositories.TaskManagementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TaskManagementRepository
) : ViewModel() {


    var allTasksMinusNewestTask: LiveData<List<Task>> = repository.getAllTasksMinusNewestTask()
    var allTasks: LiveData<List<Task>> = repository.getAllTasks()
    var allUsers: LiveData<List<User>> = repository.getAllUsers()
    var newestTask: LiveData<Task> = repository.getNewestTask()


    fun insertTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTask(task)
    }

    fun insertSubTask(subTask: SubTask) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertSubTask(subTask)
    }

    fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }

    fun updateSubTaskStatus(subTask: SubTask) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateSubTaskStatus(subTask)
    }

    fun getTaskWithSubTasks(taskId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getTaskWithSubTasks(taskId)
    }

    fun getTaskWithUsers(taskId: Int):LiveData<List<TaskWithUsers>> =
          repository.getTaskWithUsers(taskId)

}

