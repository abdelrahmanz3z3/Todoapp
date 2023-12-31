package com.example.data

import android.content.Context
import androidx.room.Room
import com.example.data.database.MyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context): MyDataBase {
        return Room
            .databaseBuilder(context.applicationContext, MyDataBase::class.java, "db")
            .fallbackToDestructiveMigration()
            .build()
    }
}