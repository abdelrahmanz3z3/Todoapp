package com.example.todo_app.ui.taskfrag

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.INVISIBLE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.example.todo_app.data.Tasks
import com.example.todo_app.databinding.ItemTaskBinding
import java.util.Calendar

class TasksAdapter(var tasks: List<Tasks>?) : Adapter<TasksAdapter.ViewHolder>() {
    class ViewHolder(val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = tasks?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.textView.text = tasks?.get(position)?.taskName.toString()
        holder.itemBinding.textView3.text = tasks?.get(position)?.description.toString()
        var time = tasks?.get(position)?.date.toString()
        var c = Calendar.getInstance()
        c.timeInMillis = time.toLong()
        holder.itemBinding.textView2.text =
            "${c.get(Calendar.DAY_OF_MONTH)}-${c.get(Calendar.MONTH) + 1}-${c.get(Calendar.YEAR)}"

        holder.itemBinding.draged.setOnClickListener {
            onItemClickListener?.onClick(position, tasks!![position])
        }
        //--------------------------------------


        if (tasks!![position].isDone == true) {
            holder.itemBinding.imageButton.visibility = INVISIBLE
            holder.itemBinding.done.visibility = VISIBLE
            holder.itemBinding.done.setTextColor(Color.GREEN)
            holder.itemBinding.view.setBackgroundColor(Color.GREEN)
            holder.itemBinding.textView.setTextColor(Color.GREEN)
        } else {
            holder.itemBinding.imageButton.visibility = VISIBLE
            holder.itemBinding.done.visibility = INVISIBLE
            holder.itemBinding.done.setTextColor(Color.parseColor("#5D9CEC"))
            holder.itemBinding.view.setBackgroundColor(Color.parseColor("#5D9CEC"))
            holder.itemBinding.textView.setTextColor(Color.parseColor("#5D9CEC"))
        }
        holder.itemBinding.imageButton.setOnClickListener {
            onTrueClickListener?.onClick(position, tasks!![position])
        }

        holder.itemBinding.swipe.setOnClickListener {
            onDeleteListner?.onClick(position, tasks!![position])
            holder.itemBinding.s2.close(true)
        }
        holder.itemBinding.swipe2.setOnClickListener {
            onUndoClickListener?.onClick(position, tasks!![position])
            holder.itemBinding.s2.close(true)
        }


    }


    fun bind(list: List<Tasks>) {
        this.tasks = list
        notifyDataSetChanged()
    }


    var onTrueClickListener: OnItemClickListener? = null
    var onItemClickListener: OnItemClickListener? = null
    var onUndoClickListener: OnItemClickListener? = null
    var onDeleteListner: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onClick(position: Int, tasks: Tasks)
    }

}