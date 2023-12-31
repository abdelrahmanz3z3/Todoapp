package com.example.data.datasource.implementation

import com.example.data.database.Dao
import com.example.data.database.MyDataBase
import com.example.data.model.TasksDTo
import com.example.data.datasource.contracts.RoomDataSource
import com.example.domain.model.Task
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(private val tasksDao: Dao):RoomDataSource {
    override suspend fun insert(task: Task) {
        val roomTask = TasksDTo.convertTaskToDTO(task)
        tasksDao.insert(roomTask)
    }

    override suspend fun update(task: Task) {
        val roomTask = TasksDTo.convertTaskToDTO(task)
        tasksDao.update(roomTask)
    }

    override suspend fun delete(task: Task) {
        val roomTask = TasksDTo.convertTaskToDTO(task)
        tasksDao.delete(roomTask)
    }

    override suspend fun getAllTasks(): List<Task?> {

       val result = tasksDao.getAllTasks().map {
           it.toTask()
       }
        return result
    }

    override suspend fun getTasksByDate(date: Long): List<Task?> {
        val result =  tasksDao.getTasksByDate(date).map {
            it.toTask()
        }
        return result
    }
}