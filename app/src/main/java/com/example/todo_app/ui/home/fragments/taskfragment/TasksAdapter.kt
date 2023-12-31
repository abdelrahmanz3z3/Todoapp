package com.example.todo_app.ui.home.fragments.taskfragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.INVISIBLE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.example.data.model.TasksDTo
import com.example.domain.model.Task
import com.example.todo_app.databinding.ItemTaskBinding
import java.util.Calendar

class TasksAdapter(var tasks: List<Task?>) : Adapter<TasksAdapter.ViewHolder>() {
    private var deletedPosition:Int?=null
    class ViewHolder(val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(task: Task?) {
            itemBinding.task = task
            if (task?.isDone == true) {
                itemBinding.imageButton.visibility = INVISIBLE
                itemBinding.done.visibility = VISIBLE
                itemBinding.done.setTextColor(Color.GREEN)
                itemBinding.view.setBackgroundColor(Color.GREEN)
                itemBinding.textView.setTextColor(Color.GREEN)
            } else {
                itemBinding.imageButton.visibility = VISIBLE
                itemBinding.done.visibility = INVISIBLE
                itemBinding.done.setTextColor(Color.parseColor("#5D9CEC"))
                itemBinding.view.setBackgroundColor(Color.parseColor("#5D9CEC"))
                itemBinding.textView.setTextColor(Color.parseColor("#5D9CEC"))
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = tasks.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
        holder.itemBinding.draged.setOnClickListener {
            onItemClickListener?.onClick(position, tasks[position]!!)
        }
        holder.itemBinding.imageButton.setOnClickListener {
            onTrueClickListener?.onClick(position, tasks[position]!!)
        }

        holder.itemBinding.swipe.setOnClickListener {
            onDeleteListner?.onClick(position, tasks[position]!!)
            holder.itemBinding.s2.close(true)
        }
        holder.itemBinding.swipe2.setOnClickListener {
            onUndoClickListener?.onClick(position, tasks[position]!!)
            holder.itemBinding.s2.close(true)
        }


    }


    fun bind(list: List<Task?>) {
        this.tasks = list
        notifyDataSetChanged()
    }


    var onTrueClickListener: OnItemClickListener? = null
    var onItemClickListener: OnItemClickListener? = null
    var onUndoClickListener: OnItemClickListener? = null
    var onDeleteListner: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onClick(position: Int, tasks: Task)
    }

}