package com.example.todo_app.ui.taskfrag

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo_app.data.MyDataBase
import com.example.todo_app.data.Tasks
import com.example.todo_app.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar


class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                viewBinding.time.text = "$dayOfMonth-${month + 1}-$year"
                cal.set(year, month, dayOfMonth)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)


            }
            timepicker.show()
        }
    }

    private fun isValid(): Boolean {
        var valid = true
        if (viewBinding.taskName.text.isNullOrBlank()) {
            viewBinding.taskCon.error = "Invalid task name!"
            valid = false
        } else {
            viewBinding.taskCon.error = null
        }
        if (viewBinding.desc.text.isNullOrBlank()) {
            viewBinding.descCon.error = "Invalid description!"
            valid = false
        } else {
            viewBinding.descCon.error = null
        }
        if (viewBinding.time.text.isNullOrBlank()) {
            viewBinding.timeCon.error = "Choose date ,please!"
            valid = false
        } else {
            viewBinding.timeCon.error = null
        }
        return valid

    }

    private fun addTask() {
        if (!isValid()) {
            return
        }
        var task = Tasks(
            taskName = viewBinding.taskName.text.toString().trim(),
            description = viewBinding.desc.text.toString(),
            date = cal.timeInMillis
        )


        context?.let {
            MyDataBase.getInstance(it).tasksdao().insert(task = task)
        }
        onTaskAdded?.showSnackBar()
        dialog?.dismiss()
    }

    var onTaskAdded: OnTaskAdded? = null

    fun interface OnTaskAdded {
        fun showSnackBar()
    }


}