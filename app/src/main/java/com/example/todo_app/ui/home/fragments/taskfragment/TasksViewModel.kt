package com.example.todo_app.ui.home.fragments.taskfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.usecase.DeleteUseCase
import com.example.domain.usecase.GetAllTaskByDateUseCase
import com.example.domain.usecase.UpdateUseCase
import com.example.todo_app.ui.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getAllTaskByDateUseCase: GetAllTaskByDateUseCase,
    private val updateUseCase: UpdateUseCase,
    private val deleteUseCase: DeleteUseCase
) : ViewModel(), TasksContract.ViewModel {
    override fun invokeAction(action: TasksContract.Action) {
        when (action) {
            is TasksContract.Action.DeleteTask -> {
                deleteTask(action.task)
            }

            is TasksContract.Action.GoToDetailsActivity -> {
                _event.postValue(TasksContract.Event.NavigateDetailsActivity(action.task))
            }

            is TasksContract.Action.UpdateTask -> {
                updateTask(action.task)
            }

            is TasksContract.Action.GetAllTasksByDate -> {
                getAllTasksByDate(action.date)
            }
        }
    }

    private val _event = SingleLiveEvent<TasksContract.Event>()
    private val _state = MutableLiveData<TasksContract.State>()
    override val event: SingleLiveEvent<TasksContract.Event>
        get() = _event
    override val state: LiveData<TasksContract.State>
        get() = _state

    private fun getAllTasksByDate(date: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val result = getAllTaskByDateUseCase.invoke(date)
                _state.postValue(TasksContract.State.GetTaskListSuccess(result))
            } catch (e: Exception) {
                _state.postValue(TasksContract.State.Error(e))
            }
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateUseCase.invoke(task)
                _state.postValue(TasksContract.State.Success("Task Updated Successfully"))
            } catch (e: Exception) {
                _state.postValue(TasksContract.State.Error(e))
            }
        }
    }

    private fun deleteTask(task: Task) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                deleteUseCase.invoke(task)
                _state.postValue(TasksContract.State.Success("Task Deleted Successfully"))
            } catch (e: Exception) {
                _state.postValue(TasksContract.State.Error(e))
            }
        }
    }

}