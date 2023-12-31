package com.example.data.datasource.contracts

import com.example.data.model.TasksDTo
import com.example.domain.model.Task

interface RoomDataSource {
    suspend fun insert(task:Task)
    suspend fun update(task:Task)
    suspend fun delete(task:Task)
    suspend fun getAllTasks():List<Task?>
    suspend fun getTasksByDate(date:Long):List<Task?>
}