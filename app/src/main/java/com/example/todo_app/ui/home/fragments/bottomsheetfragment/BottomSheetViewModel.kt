package com.example.todo_app.ui.home.fragments.bottomsheetfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.usecase.InsertUseCase
import com.example.todo_app.ui.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(private val insertUseCase: InsertUseCase):ViewModel(),
    BottomSheetContract.ViewModel {
    override fun invokeAction(action: BottomSheetContract.Action) {
        when(action)
        {
            is BottomSheetContract.Action.AddTask -> {
                insertTask(action.task)
            }
        }
    }

    val taskName = MutableLiveData<String?>()
    val taskNameError = MutableLiveData<String?>()
    val taskDescription = MutableLiveData<String?>()
    val taskDescriptionError = MutableLiveData<String?>()
    val taskTime= MutableLiveData<String?>()
    val taskTimeError = MutableLiveData<String?>()

    private val _state = MutableLiveData<BottomSheetContract.State>()
    private val _event = SingleLiveEvent<BottomSheetContract.Event>()
    override val state: LiveData<BottomSheetContract.State>
        get() = _state
    override val event: SingleLiveEvent<BottomSheetContract.Event>
        get() = _event
    private fun insertTask(task: Task)
    {
        if (!isValid())return
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                insertUseCase.invoke(task)
                _state.postValue(BottomSheetContract.State.Success(task))
                _event.postValue(BottomSheetContract.Event.NavigateToTasksFragment)
            }catch (e:Exception)
            {
                _state.postValue(BottomSheetContract.State.Error(e))
            }
        }
    }

    private fun isValid(): Boolean {
        var valid = true
        if (taskName.value.isNullOrBlank()) {
            taskNameError.value= "Invalid task name!"
            valid = false
        } else {
            taskNameError.value = null
        }
        if (taskDescription.value.isNullOrBlank()) {
            taskDescriptionError.value = "Invalid description!"
            valid = false
        } else {
            taskDescriptionError.value=null
        }
        if (taskTime.value.isNullOrBlank()) {
            taskTimeError.value = "Choose date ,please!"
            valid = false
        } else {
            taskTimeError.value = null
        }
        return valid
    }



}