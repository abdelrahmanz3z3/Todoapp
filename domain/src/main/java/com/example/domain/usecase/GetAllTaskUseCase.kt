package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repository.RoomRepository
import javax.inject.Inject

class GetAllTaskUseCase @Inject constructor(private val roomRepository: RoomRepository) {
    suspend fun invoke():List<Task?>
    {
        return roomRepository.getAllTasks()
    }
}