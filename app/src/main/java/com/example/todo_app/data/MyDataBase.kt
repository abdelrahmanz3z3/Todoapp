package com.example.todo_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Tasks::class], version = 1)
abstract class MyDataBase : RoomDatabase() {

    abstract fun tasksdao(): Dao

    companion object {
        private var instance: MyDataBase? = null
        fun getInstance(context: Context): MyDataBase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context.applicationContext, MyDataBase::class.java, "db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance!!
        }
    }
}