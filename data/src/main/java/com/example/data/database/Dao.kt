package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.TasksDTo

@Dao
interface Dao {
    @Insert
    suspend fun insert(task: TasksDTo)

    @Delete
    suspend fun delete(task: TasksDTo)

    @Update
    suspend fun update(task: TasksDTo)


    @Query("select * from TasksDTo")
    suspend fun getAllTasks(): List<TasksDTo>

    @Query("select * from TasksDTo where date = :date")
    suspend fun getTasksByDate(date: Long?): List<TasksDTo>
}