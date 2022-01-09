package com.example.taskmanagement.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.User

data class TaskWithSubTasks(

    @Embedded val task: Task,

    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskId"
    )

    val subTasks: List<SubTask>

)
