package com.example.data.datasource.di

import com.example.data.datasource.contracts.RoomDataSource
import com.example.data.datasource.implementation.RoomDataSourceImpl
import com.example.data.repository.RoomRepositoryImpl
import com.example.domain.repository.RoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Di {
    @Binds
   abstract fun bindRoomDataSource(roomDataSourceImpl: RoomDataSourceImpl):RoomDataSource
   @Binds
   abstract fun bindRoomRepository(roomRepositoryImpl: RoomRepositoryImpl):RoomRepository
}