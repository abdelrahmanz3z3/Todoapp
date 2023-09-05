package com.example.todo_app.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityHomeBinding
import com.example.todo_app.ui.settingsfrag.SettingsFragment
import com.example.todo_app.ui.taskfrag.BottomSheetFragment
import com.example.todo_app.ui.taskfrag.TasksFragment
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    var fr: TasksFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.list -> {
                    fr = TasksFragment()
                    ShowFragment(fr!!)
                    viewBinding.tool.title = getString(R.string.to_do_list)
                }

                R.id.settings -> {
                    ShowFragment(SettingsFragment())
                    viewBinding.tool.title = getString(R.string.settings)
                }
            }
            return@setOnItemSelectedListener true
        }
        viewBinding.bottomNavBar.selectedItemId = R.id.list
        viewBinding.add.setOnClickListener {
            val bottom = BottomSheetFragment()
            bottom.onTaskAdded = BottomSheetFragment.OnTaskAdded {
                Snackbar.make(viewBinding.root, "Task Added Successfully", Snackbar.LENGTH_SHORT)
                    .show()
                fr?.loadTasks()
            }

            bottom.show(supportFragmentManager, "")
        }

    }

    private fun ShowFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.con, fragment).commit()

    }
}