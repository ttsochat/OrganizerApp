package com.example.organizerapp.ui.dailyTasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R

 class TaskAdapter(private val tasks : List<Task>, private val listener: OnTaskClickListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

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

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var textOfTask: TextView?
        var iconOfEdit: ImageView?

        init {
            itemView.setOnClickListener(this)
            textOfTask = itemView.findViewById<TextView>(R.id.taskText)
            iconOfEdit = itemView.findViewById<ImageView>(R.id.editIcon)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onTaskClick(position)
            }
        }

    }
     interface OnTaskClickListener{
         fun onTaskClick(position: Int)
     }
}
