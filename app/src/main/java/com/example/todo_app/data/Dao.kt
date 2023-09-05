package com.example.todo_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert
    fun insert(task: Tasks)

    @Delete
    fun delete(task: Tasks)

    @Update
    fun update(task: Tasks)


    @Query("select * from Tasks")
    fun getAllTasks(): List<Tasks>

    @Query("select * from Tasks where date = :date")
    fun getTasksByDate(date: Long?): List<Tasks>
}