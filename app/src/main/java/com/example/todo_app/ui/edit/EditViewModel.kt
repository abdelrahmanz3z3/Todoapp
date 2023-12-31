package com.example.todo_app.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.usecase.UpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EditViewModel @Inject constructor(private val updateUseCase: UpdateUseCase):ViewModel(),EditContract.ViewModel {

    val taskName=MutableLiveData<String?>()
    val taskNameError=MutableLiveData<String?>()
    val taskDescription=MutableLiveData<String?>()
    val taskDescriptionError=MutableLiveData<String?>()
    val taskDate=MutableLiveData<String?>()
    val taskDateError=MutableLiveData<String?>()
    override fun invokeAction(action: EditContract.Action) {
        when(action)
        {
            is EditContract.Action.EditTask -> {
                updateTask(action.task)
            }
        }
    }

    private val _state=MutableLiveData<EditContract.State>()
    private val _event=MutableLiveData<EditContract.Event>()
    override val state: LiveData<EditContract.State>
        get() = _state
    override val event: LiveData<EditContract.Event>
        get() = _event
    private fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateUseCase.invoke(task)
                _state.postValue(EditContract.State.Success("Task Updated Successfully"))
                _event.postValue(EditContract.Event.NavigateToTasksFragment(task))
            }catch (e:Exception)
            {
                _state.postValue(EditContract.State.Error("Error During Update Task"))
            }
        }
    }

}