package com.example.domain.usecase

import android.util.Log
import com.example.domain.model.Task
import com.example.domain.repository.RoomRepository
import javax.inject.Inject

class UpdateUseCase @Inject constructor(private val roomRepository: RoomRepository) {
    suspend fun invoke(task: Task) {


        roomRepository.update(task)

    }
}