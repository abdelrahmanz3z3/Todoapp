package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Task

@Entity
data class TasksDTo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var taskName: String? = null,
    var description: String? = null,
    var date: Long? = null,
    var isDone: Boolean? = false,
){
    fun toTask():Task{
        return Task(
            id=id,
            taskName = taskName,
            description = description,
            date = date,
            isDone = isDone
        )
    }
 companion object{
     fun convertTaskToDTO(task: Task): TasksDTo
     {
         return TasksDTo(
             id=task.id,
             taskName = task.taskName,
             description = task.description,
             date = task.date,
             isDone = task.isDone
         )
     }
 }
}
