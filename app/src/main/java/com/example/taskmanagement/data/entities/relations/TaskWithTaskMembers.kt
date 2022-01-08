package com.example.taskmanagement.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.data.entities.User

data class TaskWithTaskMembers(

    @Embedded val task: Task,

    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskId"
    )

    val taskMembers: List<TaskMember>

)
