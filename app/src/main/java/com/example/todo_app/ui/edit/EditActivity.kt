package com.example.todo_app.ui.edit

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_app.R
import com.example.todo_app.data.Tasks
import com.example.todo_app.databinding.ActivityEditBinding
import java.util.Calendar


class EditActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityEditBinding
    lateinit var i: Intent
    lateinit var t: Tasks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        i = intent
        t = i.getSerializableExtra("task") as Tasks
        viewBinding.title.setText(t.taskName.toString())
        viewBinding.details.setText(t.description.toString())
        viewBinding.dateCon.setOnClickListener {
            showTimePicker()
        }
        var c = Calendar.getInstance()
        c.timeInMillis = t.date!!
        viewBinding.date.text =
            "${c.get(Calendar.DAY_OF_MONTH)}-${c.get(Calendar.MONTH)}-${c.get(Calendar.YEAR)}"

        viewBinding.save.setOnClickListener {
            saveChanges()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(0)
        finish()
    }

    var calendar = Calendar.getInstance()
    private fun showTimePicker() {

        val timePicker = DatePickerDialog(this)
        timePicker.setOnDateSetListener { view, year, month, dayOfMonth ->

            viewBinding.date.text = "${dayOfMonth}-${month + 1}-${year}"
            calendar.set(year, month, dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

        }
        timePicker.show()
    }

    private fun isValidate(): Boolean {
        if (viewBinding.title.text.toString().isNullOrBlank()) {
            viewBinding.titleCon.error = "Please enter a valid title"
            return false
        }
        if (viewBinding.details.text.toString().isNullOrBlank()) {
            viewBinding.detailsCon.error = "Please enter a valid title"
            return false
        }
        if (viewBinding.date.text.toString().isNullOrBlank()) {
            viewBinding.dateCon.error = "Please enter a valid title"
            return false
        }
        return true
    }

    private fun saveChanges() {
        if (!isValidate())
            return
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.do_you_want_save_changes))
        alert.setPositiveButton(getString(R.string.ok))
        { dialogInterface: DialogInterface, con: Int ->
            t.taskName = viewBinding.title.text.toString()
            t.description = viewBinding.details.text.toString()
            t.date = calendar.timeInMillis
            i.putExtra("rtask", t)
            setResult(1, i)
            finish()
            dialogInterface.dismiss()
        }
        alert.setNegativeButton(getString(R.string.no))
        { dialogInterface: DialogInterface, con: Int ->
            dialogInterface.dismiss()
            finish()
        }
        alert.show()

    }
}