package com.example.todo_app.ui.home.fragments.bottomsheetfragment

import androidx.lifecycle.LiveData
import com.example.domain.model.Task
import com.example.todo_app.ui.common.SingleLiveEvent

class BottomSheetContract {
    interface ViewModel{
        fun invokeAction(action: Action)
        val state :LiveData<State>
        val event :SingleLiveEvent<Event>
    }
    sealed class Action{
        class AddTask(val task: Task): Action()
    }
    sealed class State{
        class Success(val task: Task): State()
        class Error(val e:Exception): State()
    }
    sealed class Event{
        data object NavigateToTasksFragment : Event()
    }

}