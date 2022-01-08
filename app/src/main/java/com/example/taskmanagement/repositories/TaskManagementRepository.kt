package com.example.taskmanagement.repositories


import androidx.lifecycle.LiveData
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.data.entities.relations.TaskWithSubTasks
import com.example.taskmanagement.data.entities.relations.TaskWithTaskMembers

interface TaskManagementRepository {

    suspend fun insertTask(task: Task)
    suspend fun insertSubTask(subTask: SubTask)
    suspend fun insertUser(user: User)
    suspend fun insertTaskMember(taskMember: TaskMember)

    suspend fun updateSubTaskStatus(subTask: SubTask)
    suspend fun updateUser(user: User)
    suspend fun updateTask(task_progression: Float, taskId: Int)


    fun getAllTasks(): LiveData<List<Task>>
    fun getAllUsers(): LiveData<List<User>>
    fun getNewestTask(): LiveData<Task>
    fun getAllTaskMembers(): LiveData<List<TaskMember>>
    fun getAllTasksMinusNewestTask(): LiveData<List<Task>>


    fun getTaskWithSubTasks(taskId: Int): LiveData<List<TaskWithSubTasks>>
    fun getTaskWithTaskMembers(taskId: Int): LiveData<List<TaskWithTaskMembers>>

}