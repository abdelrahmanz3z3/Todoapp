package com.example.data.repository

import com.example.data.datasource.contracts.RoomDataSource
import com.example.domain.model.Task
import com.example.domain.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(private val roomDataSource: RoomDataSource) :
    RoomRepository {
    override suspend fun insert(task: Task) {
        roomDataSource.insert(task)
    }

    override suspend fun update(task: Task) {
        roomDataSource.update(task)
    }

    override suspend fun delete(task: Task) {
        roomDataSource.delete(task)
    }

    override suspend fun getAllTasks(): List<Task?> {
        return roomDataSource.getAllTasks()
    }

    override suspend fun getTasksByDate(date: Long): List<Task?> {
        return roomDataSource.getTasksByDate(date)
    }
}