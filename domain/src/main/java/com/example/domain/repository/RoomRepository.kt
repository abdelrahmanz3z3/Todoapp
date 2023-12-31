package com.example.domain.repository

import com.example.domain.model.Task

interface RoomRepository {
    suspend fun insert(task:Task)
    suspend fun update(task:Task)
    suspend fun delete(task:Task)
    suspend fun getAllTasks():List<Task?>
    suspend fun getTasksByDate(date:Long):List<Task?>
}