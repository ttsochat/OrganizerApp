package com.example.organizerapp.ui.dailyTasks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.db.entities.DailyTask

class DailyTasksAdapter(dailyTasksByUserId: Any?, dailyTasksFragment: DailyTasksFragment) : RecyclerView.Adapter<DailyTasksAdapter.TaskViewHolder>() {

    private var dailyTaskList = emptyList<DailyTask>()

    class DailyTasksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_tasks, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var task = dailyTaskList[position]
        holder.textOfTask?.text = task.description
    }

    fun setData(dailyTasks: List<DailyTask>){
        this.dailyTaskList = dailyTasks
        for(dailyTask in dailyTasks){
            Log.d("",dailyTask.description.toString())
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dailyTaskList.size
    }

//     fun deleteItem(index: Int){
//         tasks.removeAt(index)
//         notifyDataSetChanged()
//     }


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var textOfTask: TextView? = itemView.findViewById<TextView>(R.id.taskText)
        val editIcon: ImageView? = itemView.findViewById<ImageView>(R.id.editIcon)

        init {
            editIcon?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onTaskClick(position)
//            }
        }

    }
    interface OnTaskClickListener{
         fun onTaskClick(position: Int)
    }



}
