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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskManagementViewModel @Inject constructor(
    private val repository: TaskManagementRepository
) : ViewModel() {

    private var _allTasks: MutableLiveData<List<Task>> = MutableLiveData()
    var allTasks: LiveData<List<Task>> = _allTasks

    private var _newestTask: MutableLiveData<Task> = MutableLiveData()
    var newestTask: LiveData<Task> = _newestTask

    private var _allTasksMinusNewestTask: MutableLiveData<List<Task>> = MutableLiveData()
    var allTasksMinusNewestTask: LiveData<List<Task>> = _allTasksMinusNewestTask

    private var _taskWithSubTasks: MutableLiveData<List<TaskWithSubTasks>> = MutableLiveData()
    var taskWithSubTasks: LiveData<List<TaskWithSubTasks>> = _taskWithSubTasks

    private var _taskWithUsers: MutableLiveData<List<TaskWithUsers>> = MutableLiveData()
    var taskWithUsers: LiveData<List<TaskWithUsers>> = _taskWithUsers


    fun insertTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTask(task)
    }

    fun insertSubTask(subTask: SubTask) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertSubTask(subTask)
    }

    fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUser(user)
    }

    fun updateSubTaskStatus(subTask: SubTask) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateSubTaskStatus(subTask)
    }

    fun getAllTasks() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTasks().collect {
                _allTasks.postValue(it)
            }
        }

    fun getNewestTask() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNewestTask().collect {
                _newestTask.postValue(it)
            }
        }

    fun getAllTasksMinusNewestTask() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTasksMinusNewestTask().collect {
                _allTasksMinusNewestTask.postValue(it)
            }
        }

    fun getTaskWithSubTasks(taskId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTaskWithSubTasks(taskId).collect {
                _taskWithSubTasks.postValue(it)
            }
        }

    fun getTaskWithUsers(taskId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTaskWithUsers(taskId).collect {
                _taskWithUsers.postValue(it)
            }
        }


}

