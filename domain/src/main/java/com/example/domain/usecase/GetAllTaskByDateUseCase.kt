package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repository.RoomRepository
import javax.inject.Inject

class GetAllTaskByDateUseCase @Inject constructor(private val roomRepository: RoomRepository) {
    suspend fun invoke(date:Long):List<Task?>
    {
        return roomRepository.getTasksByDate(date)
    }
}