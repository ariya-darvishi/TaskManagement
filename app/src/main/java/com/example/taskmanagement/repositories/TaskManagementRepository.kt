package com.example.taskmanagement.repositories


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


    fun getAllTasks(): Flow<List<Task>>
    fun getNewestTask(): Flow<Task>
    fun getAllTasksMinusNewestTask(): Flow<List<Task>>


    fun getTaskWithSubTasks(taskId: Int): Flow<List<TaskWithSubTasks>>
    fun getTaskWithUsers(taskId: Int): Flow<List<TaskWithUsers>>

}