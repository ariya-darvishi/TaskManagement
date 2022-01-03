package com.example.taskmanagement.repositories

import com.example.taskmanagement.data.TaskManagementDao
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.data.entities.relations.TaskWithSubTasks
import com.example.taskmanagement.data.entities.relations.TaskWithUsers
import kotlinx.coroutines.flow.Flow

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

    override suspend fun updateSubTaskStatus(subTask: SubTask) {
        dao.updateSubTaskStatus(subTask)
    }

    override fun getAllTasks(): Flow<List<Task>> = dao.getAllTasks()


    override fun getNewestTask(): Flow<Task> = dao.getNewestTask()

    override fun getAllTasksMinusNewestTask(): Flow<List<Task>> =
        dao.getAllTasksMinusNewestTask()

    override fun getTaskWithSubTasks(taskId: Int): Flow<List<TaskWithSubTasks>> =
        dao.getTaskWithSubTasks(taskId)

    override fun getTaskWithUsers(taskId: Int): Flow<List<TaskWithUsers>> =
        dao.getTaskWithUsers(taskId)
}