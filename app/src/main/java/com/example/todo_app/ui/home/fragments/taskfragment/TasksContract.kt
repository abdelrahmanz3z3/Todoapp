package com.example.todo_app.ui.home.fragments.taskfragment

import androidx.lifecycle.LiveData
import com.example.domain.model.Task
import com.example.todo_app.ui.common.SingleLiveEvent

class TasksContract {
    interface ViewModel{
         fun invokeAction(action: Action)
         val state:LiveData<State>
         val event:SingleLiveEvent<Event>
    }
    sealed class Action
    {
        class GetAllTasksByDate(val date: Long): Action()
        class UpdateTask(val task:Task): Action()
        class DeleteTask(val task:Task): Action()

        class GoToDetailsActivity(val task: Task): Action()
    }
    sealed class Event
    {
        class NavigateDetailsActivity(val task: Task): Event()
    }
    sealed class State
    {
        class GetTaskListSuccess(val list:List<Task?>) : State()
        class Success(val msg:String) : State()
        class Error(val e:Exception): State()

    }
}