package com.example.organizerapp.ui.dailyTasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.db.entities.DailyTask

/**
 * DailyTasksAdapter is the bridge between the DailyTasksView and the underlying data.
 */
class DailyTasksAdapter(private var listener: OnTaskClickListener) : RecyclerView.Adapter<DailyTasksAdapter.TaskViewHolder>() {

    private var dailyTaskList = emptyList<DailyTask>()

    /**
     * Overwritten function onCreateViewHolder creates a new RecyclerView.ViewHolder
     * and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tasks, parent, false)
        return TaskViewHolder(view)
    }

    /**
     * Overwritten function onBindViewHolder updates the RecyclerView.ViewHolder contents with
     * the item at the given position and also sets up some private fields to be used by RecyclerView.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = dailyTaskList[position]
        holder.textOfTask?.text = task.description
    }

    /**
     * Sets the data for the adapter list and notifies the View.
     */
    fun setData(dailyTasks: List<DailyTask>){
        this.dailyTaskList = dailyTasks
        notifyDataSetChanged()
    }

    /**
     * Removes a daily task from the list and stops displaying
     * it.
     */
    fun removeTaskFromAdapterList(position: Int){
        val list = dailyTaskList.toMutableList()
        list.removeAt(position)
        dailyTaskList = list.toList()
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return dailyTaskList.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var textOfTask: TextView? = itemView.findViewById<TextView>(R.id.taskText)
        private val cardView: CardView? = itemView.findViewById<CardView>(R.id.taskCard)

        init {
            cardView?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onTaskClick(position)
            }
        }

    }
    interface OnTaskClickListener{
         fun onTaskClick(position: Int)
    }

}
