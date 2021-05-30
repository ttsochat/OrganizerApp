package com.example.organizerapp.ui.tomatoStats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.db.entities.DailyTask

/**
 * TomatoStatsAdapter is the bridge between the TomatoStatsView and the underlying data.
 */
class TomatoStatsAdapter(private var listener: OnTaskClickListener) : RecyclerView.Adapter<TomatoStatsAdapter.TaskViewHolder>() {

    private var tomatoStatsCompList = emptyList<DailyTask>()
    private var tomatoStatsUncList = emptyList<DailyTask>()

    class TomatoStatsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }

    /**
     * Overwritten function onCreateViewHolder creates a new RecyclerView.ViewHolder
     * and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_tomato_stats, parent, false)
        return TaskViewHolder(view)
    }

    /**
     * Overwritten function onBindViewHolder updates the RecyclerView.ViewHolder contents with
     * the item at the given position and also sets up some private fields to be used by RecyclerView.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var statUnc = tomatoStatsUncList.get(position)
        var statC = tomatoStatsCompList.getOrNull(position)
        holder.dateOfStat?.text = statUnc.description
        holder.tomatoTotal?.text = statUnc.dtid.toString()

        if (statC != null) {
            holder.tomatoRate?.text = statC.dtid.toString()
            when {
                statC.dtid <= 5 -> holder.tomatoImage?.setImageResource(R.drawable.tomato_icon)
                statC.dtid <= 10 -> holder.tomatoImage?.setImageResource(R.drawable.tomato_stack_icon)
                statC.dtid > 10 -> holder.tomatoImage?.setImageResource(R.drawable.tomato_box_icon)
            }

        } else {
            holder.tomatoRate?.text = 0.toString()
            holder.tomatoImage?.setImageResource(R.drawable.tomato_icon)
        }
    }

    /**
     * Sets the list in the adapter with all tasks grouped by date
     * in order to be compared with the completed tasks.
     */
    fun setUncData(dailyTasks: List<DailyTask>){
        this.tomatoStatsUncList = dailyTasks
        this.tomatoStatsCompList = dailyTasks
        notifyDataSetChanged()
    }

    /**
     * Sets the list in the adapter with completed tasks grouped by date
     * in order to be compared with all tasks.
     */
    fun setComData(dailyTasks: List<DailyTask>){
        this.tomatoStatsCompList = dailyTasks
        notifyDataSetChanged()
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return tomatoStatsUncList.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        var dateOfStat: TextView? = itemView.findViewById<TextView>(R.id.date_of_stat)
        val tomatoRate: TextView? = itemView.findViewById<TextView>(R.id.tomato_val)
        val tomatoTotal: TextView? = itemView.findViewById<TextView>(R.id.tomato_total)
        val tomatoImage: ImageView? = itemView.findViewById<ImageView>(R.id.tomato_icon)

        init {
            tomatoRate?.setOnClickListener(this)
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
