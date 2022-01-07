package com.example.taskmanagement.repositories


import androidx.lifecycle.LiveData
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.data.entities.relations.TaskWithSubTasks
import com.example.taskmanagement.data.entities.relations.TaskWithUsers
import kotlinx.coroutines.flow.Flow

interface TaskManagementRepository {

    suspend fun insertTask(task: Task)
    suspend fun insertSubTask(subTask: SubTask)
    suspend fun insertUser(user: User)

    suspend fun updateSubTaskStatus(subTask: SubTask)
    suspend fun updateUser(user: User)


    fun getAllTasks(): LiveData<List<Task>>
    fun getAllUsers(): LiveData<List<User>>
    fun getNewestTask(): LiveData<Task>
    fun getAllTasksMinusNewestTask(): LiveData<List<Task>>


    fun getTaskWithSubTasks(taskId: Int): LiveData<List<TaskWithSubTasks>>
    fun getTaskWithUsers(taskId: Int): LiveData<List<TaskWithUsers>>

}