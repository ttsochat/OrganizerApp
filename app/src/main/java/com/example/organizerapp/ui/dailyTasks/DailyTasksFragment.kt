package com.example.organizerapp.ui.dailyTasks

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentDailyTasksBinding
import com.example.organizerapp.db.entities.DailyTask
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.properties.Delegates

/**
 * DailyTasksFragment is used for displaying the daily tasks.
 */
class DailyTasksFragment : Fragment(), DailyTasksAdapter.OnTaskClickListener{

    private lateinit var viewDailyTasksModel: DailyTasksViewModel
    private var _binding: FragmentDailyTasksBinding? = null
    private lateinit var adapter: DailyTasksAdapter
    private lateinit var emptyFragmentMessage : TextView
    private lateinit var auth: FirebaseAuth
    private var tasksDoneNum by Delegates.notNull<Int>()
    private var dailyActiveListSize: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * Overwritten function onCreateView
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()
        viewDailyTasksModel = ViewModelProvider(this).get(DailyTasksViewModel::class.java)

        _binding = FragmentDailyTasksBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val recyclerView: RecyclerView = binding.tasksRecycler

        val view = inflater.inflate(R.layout.fragment_daily_tasks, container, false)

        adapter = DailyTasksAdapter(this)

        val today: Calendar = GregorianCalendar()
        // reset hour, minutes, seconds and millis
        // reset hour, minutes, seconds and millis
        today[Calendar.HOUR_OF_DAY] = 0
        today[Calendar.MINUTE] = 0
        today[Calendar.SECOND] = 0
        today[Calendar.MILLISECOND] = 0

        //displays the LiveData of daily tasks on the fragment using an Observer
        viewDailyTasksModel = ViewModelProvider(this).get(DailyTasksViewModel::class.java)
        viewDailyTasksModel.getDailyTasksByUserId(auth.currentUser.uid).observe(viewLifecycleOwner, { dailyTasks->
            tasksDoneNum=0
            dailyActiveListSize=0
            var dailyListSize=0
            val list = mutableListOf<DailyTask>()
            for(dailyTask in dailyTasks){
                if(today.time < dailyTask.date){
                    dailyListSize++
                }
                if(dailyTask.status.toString() == "ACTIVE" && today.time < dailyTask.date){
                    list.add(dailyTask)
                    dailyActiveListSize++
                }
                if(dailyTask.status.toString() == "DONE" && today.time < dailyTask.date){
                    tasksDoneNum++
                }
            }
            tomatoBar(dailyListSize)
            viewDailyTasksModel.setDailyTasksList(list)
            adapter.setData(list)
            tasksNumberUpdate(list.size)
        })
        recyclerView.adapter = adapter

        emptyFragmentMessage = binding.noTasksText
        tasksNumberUpdate(dailyActiveListSize)


        //the object that implements the swipe right functionality that implies that the task is done
        val swipeDelete = object : SwipeToDone() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position : Int = viewHolder.adapterPosition
                viewDailyTasksModel.taskDone(position, auth.currentUser.uid)
                adapter.removeTaskFromAdapterList(position)
                adapter.notifyItemRemoved(position)
                Snackbar.make(recyclerView, "Good job, you finished this task!", Snackbar.LENGTH_LONG).show()
            }
        }

        //inserts the previous object to an ItemTouchHelper
        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(recyclerView)

        //the object that implements the swipe left functionality that deletes the task
        val swipeArchive = object : SwipeToArchive(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position : Int = viewHolder.adapterPosition
                viewDailyTasksModel.deleteTask(position, auth.currentUser.uid)
                adapter.removeTaskFromAdapterList(position)
                adapter.notifyItemRemoved(position)
                Snackbar.make(recyclerView, "This task has been removed!", Snackbar.LENGTH_LONG).show()
            }
        }
        //inserts the previous object to an ItemTouchHelper
        val touchHelper2 = ItemTouchHelper(swipeArchive)
        touchHelper2.attachToRecyclerView(recyclerView)

        //setOnClickListener for the add button on the button of the fragment
        _binding!!.fab.setOnClickListener {
            addTaskDialog()
        }
        return root
    }

    /**
     * Overwritten function onDestroyView
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Overwritten function onTaskClick. Allows the user to edit the description
     * of a daily task.
     */
    override fun onTaskClick(position: Int) {
        editTaskDialog(position)
    }

    /**
     * Adds a new daily task on the fragment and also calls the corresponding methods
     * of the Adapter and the ViewModel
     */
    private fun addTaskDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("New Task")
        val view = layoutInflater.inflate(R.layout.edit_task_dialog, null)
        val editText = view.findViewById<EditText>(R.id.edit_text)
        val done = view.findViewById<Button>(R.id.done)
        val cancel = view.findViewById<Button>(R.id.cancel)
        editText.hint = "Add your task here"
        builder.setView(view)
        val ad : AlertDialog = builder.show()
        done.setOnClickListener{
            if(editText.text.toString().trim().isEmpty()){
                Snackbar.make(view, "You forgot to type your task!", Snackbar.LENGTH_SHORT).show()
            }else{
                val newTask = DailyTask(0,editText.text.toString(),Calendar.getInstance().time,"ACTIVE",1,auth.currentUser.uid)
                viewDailyTasksModel.addDailyTask(newTask)
                ad.dismiss()
            }
        }
        cancel.setOnClickListener{
            ad.dismiss()
        }
    }

    /**
     * Edits the description of a new daily task and also calls the corresponding methods
     * of the Adapter and the ViewModel
     */
    private fun editTaskDialog(position: Int){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Task")
        val view = layoutInflater.inflate(R.layout.edit_task_dialog, null)
        val editText = view.findViewById<EditText>(R.id.edit_text)
        val done = view.findViewById<Button>(R.id.done)
        val cancel = view.findViewById<Button>(R.id.cancel)
        editText.setText(viewDailyTasksModel.getSpecificTask(position).description)
        builder.setView(view)
        val ad : AlertDialog = builder.show()
        done.setOnClickListener {
            if(editText.text.toString().trim().isEmpty()){
                Snackbar.make(view, "You forgot to type your task!", Snackbar.LENGTH_SHORT).show()
            }else{
                viewDailyTasksModel.setTaskText(position, editText.text.toString(), auth.currentUser.uid)
                adapter.notifyDataSetChanged()
                ad.dismiss()
            }
        }
        cancel.setOnClickListener{
            ad.dismiss()
        }
    }

    /**
     * It displays the tomatoes when a daily task is completed
     * on the tomatoBar on the top of the Fragment. It also contains
     * the algorithm that changes the ImageViews.
     */
    private fun tomatoBar(allDailyTasks: Int){
        val tomatoPrecentage = binding.tomatoPercentage
        tomatoPrecentage.text = "$tasksDoneNum / $allDailyTasks"

        val tomato0 = binding.tomato0
        val tomato1 = binding.tomato1
        val tomato2 = binding.tomato2
        val tomato3 = binding.tomato3
        val tomato4 = binding.tomato4

        val tomatoes : IntArray = intArrayOf(0, 0, 0, 0, 0)
        var i = 0
        while (i<tasksDoneNum){
            next(tomatoes)
            i++
        }

        when {
            tomatoes[0] == 0 -> {
                tomato0.setImageResource(R.drawable.empty_icon)
            }
            tomatoes[0] == 1 -> {
                tomato0.setImageResource(R.drawable.tomato_icon)
            }
            tomatoes[0] == 2 -> {
                tomato0.setImageResource(R.drawable.tomato_stack_icon)
            }
            tomatoes[0] == 3 -> {
                tomato0.setImageResource(R.drawable.tomato_box_icon)
            }
        }

        when {
            tomatoes[1] == 0 -> {
                tomato1.setImageResource(R.drawable.empty_icon)
            }
            tomatoes[1] == 1 -> {
                tomato1.setImageResource(R.drawable.tomato_icon)
            }
            tomatoes[1] == 2 -> {
                tomato1.setImageResource(R.drawable.tomato_stack_icon)
            }
            tomatoes[1] == 3 -> {
                tomato1.setImageResource(R.drawable.tomato_box_icon)
            }
        }

        when {
            tomatoes[2] == 0 -> {
                tomato2.setImageResource(R.drawable.empty_icon)
            }
            tomatoes[2] == 1 -> {
                tomato2.setImageResource(R.drawable.tomato_icon)
            }
            tomatoes[2] == 2 -> {
                tomato2.setImageResource(R.drawable.tomato_stack_icon)
            }
            tomatoes[2] == 3 -> {
                tomato2.setImageResource(R.drawable.tomato_box_icon)
            }
        }

        when {
            tomatoes[3] == 0 -> {
                tomato3.setImageResource(R.drawable.empty_icon)
            }
            tomatoes[3] == 1 -> {
                tomato3.setImageResource(R.drawable.tomato_icon)
            }
            tomatoes[3] == 2 -> {
                tomato3.setImageResource(R.drawable.tomato_stack_icon)
            }
            tomatoes[3] == 3 -> {
                tomato3.setImageResource(R.drawable.tomato_box_icon)
            }
        }

        when {
            tomatoes[4] == 0 -> {
                tomato4.setImageResource(R.drawable.empty_icon)
            }
            tomatoes[4] == 1 -> {
                tomato4.setImageResource(R.drawable.tomato_icon)
            }
            tomatoes[4] == 2 -> {
                tomato4.setImageResource(R.drawable.tomato_stack_icon)
            }
            tomatoes[4] == 3 -> {
                tomato4.setImageResource(R.drawable.tomato_box_icon)
            }
        }
    }

    fun next(array : IntArray) {
        var head = 0
        var i = 0
        while (i < array.size){
            if (array[head] > array[i]){
                head = i
            }
            i++
        }
        Arrays.fill(array, head + 1, array.size, 0)
        array[head]++
    }

    /**
     * Determines on whether or not the empty task message is visible
     */
    private fun tasksNumberUpdate(numOfTasks : Int){
        if(numOfTasks == 0){
            emptyFragmentMessage.visibility = View.VISIBLE
        }else{
            emptyFragmentMessage.visibility = View.INVISIBLE
        }
    }
}



