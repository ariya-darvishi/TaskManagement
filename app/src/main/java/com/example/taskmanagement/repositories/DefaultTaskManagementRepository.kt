package com.example.taskmanagement.repositories

import androidx.lifecycle.LiveData
import com.example.taskmanagement.data.TaskManagementDao
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.data.entities.relations.TaskWithSubTasks
import com.example.taskmanagement.data.entities.relations.TaskWithTaskMembers

class DefaultTaskManagementRepository(
    private val dao: TaskManagementDao
) : TaskManagementRepository {
    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun insertSubTask(subTask: SubTask) {
        dao.insertSubTask(subTask)
    }

    override suspend fun insertUser(user: User) {
        dao.insertUser(user)
    }

    override suspend fun insertTaskMember(taskMember: TaskMember) {
        dao.insertTaskMember(taskMember)
    }

    override suspend fun updateSubTaskStatus(subTask: SubTask) {
        dao.updateSubTaskStatus(subTask)
    }

    override suspend fun updateUser(user: User) {
        dao.updateUser(user)
    }

    override suspend fun updateTask(task_progression: Float, taskId: Int) {
        dao.updateTask(task_progression, taskId)
    }

    override fun getAllTasks(): LiveData<List<Task>> = dao.getAllTasks()
    override fun getAllUsers(): LiveData<List<User>> = dao.getAllUsers()


    override fun getNewestTask(): LiveData<Task> = dao.getNewestTask()
    override fun getAllTaskMembers(): LiveData<List<TaskMember>>  = dao.getAllTaskMembers()

    override fun getAllTasksMinusNewestTask(): LiveData<List<Task>> =
        dao.getAllTasksMinusNewestTask()

    override fun getTaskWithSubTasks(taskId: Int): LiveData<List<TaskWithSubTasks>> =
        dao.getTaskWithSubTasks(taskId)

    override fun getTaskWithTaskMembers(taskId: Int): LiveData<List<TaskWithTaskMembers>> =
        dao.getTaskWithTaskMembers(taskId)
}