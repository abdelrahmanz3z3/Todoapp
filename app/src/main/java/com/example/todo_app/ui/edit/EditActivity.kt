package com.example.todo_app.ui.edit

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todo_app.R
import com.example.data.model.TasksDTo
import com.example.domain.model.Task
import com.example.todo_app.databinding.ActivityEditBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class EditActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityEditBinding
    private lateinit var i: Intent
    private lateinit var t: Task
    private var calendar = Calendar.getInstance()
    private lateinit var viewModel: EditViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observe()

    }

    private fun observe() {
        viewModel.state.observe(this,::handelState)
        viewModel.event.observe(this){
            when(it)
            {
                is EditContract.Event.NavigateToTasksFragment ->
                    {
                        finish()
                    }
            }
        }
    }

    private fun handelState(state: EditContract.State) {
        when(state)
        {
            is EditContract.State.Error -> {
                Toast.makeText(this, state.msg, Toast.LENGTH_SHORT).show()
            }
            is EditContract.State.Success ->{
                Toast.makeText(this, state.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[EditViewModel::class.java]
        viewBinding.lifecycleOwner=this
        i = intent
        t = i.getSerializableExtra("task") as Task
        calendar.timeInMillis = t.date!!.toLong()
        viewBinding.task = t
        viewBinding.dateCon.setOnClickListener {
            showTimePicker()
        }
        viewBinding.save.setOnClickListener {
            saveChanges()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(0)
        finish()
    }

    private fun showTimePicker() {

        val timePicker = DatePickerDialog(this)
        timePicker.setOnDateSetListener { view, year, month, dayOfMonth ->

            viewBinding.date.text = "${dayOfMonth} / ${month + 1} / ${year}"
            calendar.set(year, month, dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

        }
        timePicker.show()
    }

    private fun isValidate(): Boolean {
        if (viewBinding.title.text.isNullOrBlank()) {
            viewBinding.titleCon.error = "Please enter a valid title"
            return false
        }
        if (viewBinding.details.text.isNullOrBlank()) {
            viewBinding.detailsCon.error = "Please enter a valid title"
            return false
        }
        if (viewBinding.date.text.isNullOrBlank()) {
            viewBinding.dateCon.error= "Please enter a valid title"
            return false
        }
        return true
    }


    private fun saveChanges() {
        if (!isValidate())return
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.do_you_want_save_changes))
        alert.setPositiveButton(getString(R.string.ok))
        { dialogInterface: DialogInterface, _: Int ->
            t.taskName = viewBinding.title.text.toString()
            t.description = viewBinding.details.text.toString()
            t.date = calendar.timeInMillis
            viewModel.invokeAction(EditContract.Action.EditTask(t))
            dialogInterface.dismiss()
        }
        alert.setNegativeButton(getString(R.string.no))
        { dialogInterface: DialogInterface, con: Int ->
            dialogInterface.dismiss()
        }
        alert.show()

    }
}