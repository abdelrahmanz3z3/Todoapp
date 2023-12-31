package com.example.todo_app.ui.home.fragments.bottomsheetfragment

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.data.database.MyDataBase
import com.example.data.model.TasksDTo
import com.example.domain.model.Task
import com.example.todo_app.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentBottomSheetBinding
    lateinit var viewModel: BottomSheetViewModel
    private val calendar: Calendar =Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[BottomSheetViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.vm=viewModel
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewsActions()
        observe()


    }

    private fun viewsActions() {
        viewBinding.addTask.setOnClickListener {
            addTask()
        }
        viewBinding.timeCon.setOnClickListener {
            showtimePicker()
        }
    }

    val cal = Calendar.getInstance()

    private fun showtimePicker() {

        context?.let {
            val timepicker = DatePickerDialog(it)
            timepicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                viewBinding.time.text = "$dayOfMonth / ${month + 1} / $year"
                cal.set(year, month, dayOfMonth)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)


            }
            timepicker.show()
        }
    }

        private fun observe()
        {
            viewModel.state.observe(viewLifecycleOwner,::handelStates)
            viewModel.event.observe(viewLifecycleOwner,::handelEvents)
        }

    private fun handelEvents(event: BottomSheetContract.Event) {
        when(event)
        {
            BottomSheetContract.Event.NavigateToTasksFragment ->{
                dialog?.dismiss()
            }
        }
    }

    private fun handelStates(state: BottomSheetContract.State) {
        when(state)
        {
            is BottomSheetContract.State.Error -> {
                Toast.makeText(requireContext(), state.e.localizedMessage!!, Toast.LENGTH_SHORT).show()}
            is BottomSheetContract.State.Success -> {
                onTaskAdded?.showSnackBar()
            }
        }
    }

    private fun addTask() {
        val task = Task(
            taskName = viewBinding.taskName.text.toString().trim(),
            description = viewBinding.desc.text.toString(),
            date = cal.timeInMillis
        )
        viewModel.invokeAction(BottomSheetContract.Action.AddTask(task = task))

    }

    var onTaskAdded: OnTaskAdded? = null

    fun interface OnTaskAdded {
        fun showSnackBar()
    }


}