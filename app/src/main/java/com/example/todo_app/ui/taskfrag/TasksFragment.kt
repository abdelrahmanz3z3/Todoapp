package com.example.todo_app.ui.taskfrag

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo_app.R
import com.example.todo_app.data.MyDataBase
import com.example.todo_app.data.Tasks
import com.example.todo_app.databinding.FragmentTasksBinding
import com.example.todo_app.ui.edit.EditActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.Calendar

class TasksFragment : Fragment() {

    lateinit var viewBinding: FragmentTasksBinding
    var adapter: TasksAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentTasksBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TasksAdapter(null)
        viewBinding.rec.adapter = adapter
        adapter?.onTrueClickListener = TasksAdapter.OnItemClickListener { position, tasks ->
            tasks.isDone = true
            MyDataBase.getInstance(requireContext()).tasksdao().update(tasks)
            loadTasks()

        }

        adapter?.onItemClickListener = TasksAdapter.OnItemClickListener { position, tasks ->

            val i = Intent(requireContext(), EditActivity::class.java)
            i.putExtra("task", tasks)
            startActivityForResult(i, 2)

        }
        initCal()

    }

    var sel = Calendar.getInstance()

    init {
        sel.set(Calendar.HOUR_OF_DAY, 0)
        sel.set(Calendar.MINUTE, 0)
        sel.set(Calendar.SECOND, 0)
        sel.set(Calendar.MILLISECOND, 0)
    }

    fun initCal() {
        viewBinding.calendarView.selectedDate = CalendarDay.today()
        viewBinding.calendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->

            if (selected) {
                sel.set(Calendar.YEAR, date.year)
                sel.set(Calendar.MONTH, date.month - 1)
                sel.set(Calendar.DAY_OF_MONTH, date.day)
                loadTasks()
            }
        })
    }


    private fun onDelete() {
        adapter?.onDeleteListner = TasksAdapter.OnItemClickListener { position, tasks ->
            val alert = AlertDialog.Builder(requireContext())
            alert.setMessage(getString(R.string.delete_this_task))
            alert.setPositiveButton(R.string.ok)
            { dialogInterface: DialogInterface, i: Int ->
                MyDataBase.getInstance(requireContext()).tasksdao().delete(tasks)
                loadTasks()
                dialogInterface.dismiss()
            }
            alert.setNegativeButton(R.string.no)
            { dialogInterface: DialogInterface, i: Int ->
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
    }

    private fun onUndo() {
        adapter?.onUndoClickListener = TasksAdapter.OnItemClickListener { position, tasks ->
            tasks.isDone = false
            MyDataBase.getInstance(requireContext()).tasksdao().update(tasks)
            loadTasks()

        }
    }

    fun loadTasks() {
        var list =
            context?.let { MyDataBase.getInstance(it).tasksdao().getTasksByDate(sel.timeInMillis) }
        adapter?.bind(list!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            var task = data?.getSerializableExtra("rtask") as Tasks
            MyDataBase.getInstance(requireContext()).tasksdao().update(task)
        } else
            return
    }


}