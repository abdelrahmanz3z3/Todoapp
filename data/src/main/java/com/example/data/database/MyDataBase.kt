package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.TasksDTo


@Database(entities = [TasksDTo::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun tasksDao(): Dao

}