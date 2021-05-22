package com.example.organizerapp.ui.dailyTasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R

 class DailyTasksAdapter(private val tasks : MutableList<Task>, private val listener: OnTaskClickListener) : RecyclerView.Adapter<DailyTasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_tasks, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

     override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var task = tasks[position]
        holder.textOfTask?.text = task.text
    }

     fun deleteItem(index: Int){
         tasks.removeAt(index)
         notifyDataSetChanged()
     }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var textOfTask: TextView? = itemView.findViewById<TextView>(R.id.taskText)
        val editIcon: ImageView? = itemView.findViewById<ImageView>(R.id.editIcon)

        init {
            editIcon?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onTaskClick(position)
            }
        }

    }
     interface OnTaskClickListener{
         fun onTaskClick(position: Int)
     }

}
