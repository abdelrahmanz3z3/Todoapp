package com.example.todo_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var taskName: String? = null,
    var description: String? = null,
    var date: Long? = null,
    var isDone: Boolean? = false,
) : Serializable
