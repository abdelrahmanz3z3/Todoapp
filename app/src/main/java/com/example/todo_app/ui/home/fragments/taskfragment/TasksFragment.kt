package com.example.todo_app.ui.home.fragments.taskfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todo_app.R
import com.example.data.database.MyDataBase
import com.example.data.model.TasksDTo
import com.example.domain.model.Task
import com.example.todo_app.databinding.FragmentTasksBinding
import com.example.todo_app.ui.edit.EditActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
@AndroidEntryPoint
class TasksFragment : Fragment() {

    private lateinit var viewBinding: FragmentTasksBinding
    private var adapter: TasksAdapter? = null
    private lateinit var viewModel: TasksViewModel
    private var sel: Calendar = Calendar.getInstance()
    init {
        sel.set(Calendar.HOUR_OF_DAY, 0)
        sel.set(Calendar.MINUTE, 0)
        sel.set(Calendar.SECOND, 0)
        sel.set(Calendar.MILLISECOND, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentTasksBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initViews()
    }

    private fun observe() {
        viewModel.state.observe(viewLifecycleOwner,::handelStates)
        viewModel.event.observe(viewLifecycleOwner){
            when(it)
            {
                is TasksContract.Event.NavigateDetailsActivity -> {
                    goToDetailsActivity(it.task)

                }
            }
        }
    }

    private fun goToDetailsActivity(task: Task) {
        val i = Intent(requireContext(), EditActivity::class.java)
        i.putExtra("task", task)
        startActivity(i)
    }

    private fun handelStates(state: TasksContract.State) {
        when(state)
        {
            is TasksContract.State.Error -> {}
            is TasksContract.State.GetTaskListSuccess -> {
                adapter?.bind(state.list)
            }
            is TasksContract.State.Success -> {
                Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
                loadTasks()
            }
        }
    }

    private fun initViews() {
        adapter = TasksAdapter(listOf())
        viewBinding.rec.adapter = adapter
        adapter?.onTrueClickListener = TasksAdapter.OnItemClickListener { position, tasks ->
            tasks.isDone = true
            viewModel.invokeAction(TasksContract.Action.UpdateTask(tasks))
        }

        adapter?.onItemClickListener = TasksAdapter.OnItemClickListener { position, tasks ->

            viewModel.invokeAction(TasksContract.Action.GoToDetailsActivity(tasks))
        }
        initiateCalender()
    }




    private fun initiateCalender() {
        viewBinding.calendarView.selectedDate = CalendarDay.today()
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->

            if (selected) {
                sel.set(Calendar.YEAR, date.year)
                sel.set(Calendar.MONTH, date.month - 1)
                sel.set(Calendar.DAY_OF_MONTH, date.day)
                loadTasks()
            }
        }
    }


    private fun onDelete() {
        adapter?.onDeleteListner = TasksAdapter.OnItemClickListener { position, tasks ->
            val alert = AlertDialog.Builder(requireContext())
            alert.setMessage(getString(R.string.delete_this_task))
            alert.setPositiveButton(R.string.ok)
            { dialogInterface: DialogInterface, i: Int ->
                viewModel.invokeAction(TasksContract.Action.DeleteTask(tasks))
                dialogInterface.dismiss()
            }
            alert.setNegativeButton(R.string.no)
            {   dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            alert.show()

        }
    }

    override fun onStart() {
        super.onStart()
        onDelete()
        onUndo()
        loadTasks()
        observe()
    }

    private fun onUndo() {
        adapter?.onUndoClickListener = TasksAdapter.OnItemClickListener { position, tasks ->
            tasks.isDone = false
            viewModel.invokeAction(TasksContract.Action.UpdateTask(tasks))
        }
    }

    fun loadTasks() {
        viewModel.invokeAction(TasksContract.Action.GetAllTasksByDate(sel.timeInMillis))
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == 1) {
//            val task = data?.getSerializableExtra("rtask") as Task
//            viewModel.invokeAction(TasksContract.Action.UpdateTask(task))
//        } else
//            return
//    }


}