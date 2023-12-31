package com.example.todo_app.ui.edit

import androidx.lifecycle.LiveData
import com.example.domain.model.Task

class EditContract {
    interface ViewModel{
        fun invokeAction(action: Action)
        val state:LiveData<State>
        val event:LiveData<Event>
    }

    sealed class Action{
        class EditTask(val task: Task):Action()
    }
    sealed class State{
        class Success(val msg:String):State()
        class Error(val msg:String):State()
    }
    sealed class Event{
        class NavigateToTasksFragment(val task: Task):Event()
    }
}