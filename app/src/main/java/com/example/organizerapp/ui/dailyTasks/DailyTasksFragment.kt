package com.example.organizerapp.ui.dailyTasks

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentDailyTasksBinding
import com.example.organizerapp.db.entities.DailyTask
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_daily_tasks.view.*
import java.util.*

class DailyTasksFragment : Fragment(){

    private lateinit var viewDailyTasksModel: DailyTasksViewModel
    private var _binding: FragmentDailyTasksBinding? = null
    private lateinit var adapter: DailyTasksAdapter
    private lateinit var emptyFragmentMessage : TextView
    private lateinit var auth: FirebaseAuth

    //val firebaseDatabase = FirebaseDataBase.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        viewDailyTasksModel = ViewModelProvider(this).get(DailyTasksViewModel::class.java)

        _binding = FragmentDailyTasksBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val recyclerView: RecyclerView = binding.tasksRecycler

        val view = inflater.inflate(R.layout.fragment_daily_tasks, container, false)

//        val recyclerView = view.tasksRecycler
        adapter = DailyTasksAdapter(viewDailyTasksModel.getDailyTasksByUserId(auth.currentUser.uid), this)

        viewDailyTasksModel = ViewModelProvider(this).get(DailyTasksViewModel::class.java)
        viewDailyTasksModel.getDailyTasksByUserId(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyTasks->
            adapter.setData(dailyTasks)
            tasksNumberUpdate(viewDailyTasksModel.getNumberOfDailyTasksByUserId(auth.currentUser.uid))
        })

        recyclerView.adapter = adapter

        emptyFragmentMessage = binding.noTasksText
        //tasksNumberUpdate(viewDailyTasksModel.getNumberOfDailyTasksByUserId(auth.currentUser.uid))

        val info = binding.info

        //set clicklistener for info
        info.setOnClickListener{
             Toast.makeText(context, "Swipe finished tasks right to delete & unfinished tasks left to save for later", Toast.LENGTH_LONG).show()

        }

//        val swipeDelete = object : SwipeToDelete() {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position : Int = viewHolder.bindingAdapterPosition
//                val deletedTask : Task = viewModel.getSpecificTask(position)
//                viewModel.removeTask(position)
//                tasksNumberUpdate(viewModel.getTasks().size)
//                adapter.notifyItemRemoved(position)
//                Snackbar.make(recyclerView, "Good job, you finished this task!", Snackbar.LENGTH_LONG)
//                    .setAction("Undo") { _ ->
//                        viewModel.addTaskToSpecificPosition(position, deletedTask)
//                        adapter.notifyItemInserted(position)
//
//                    }
//                    .show()
//            }
//        }
//
//        val touchHelper = ItemTouchHelper(swipeDelete)
//        touchHelper.attachToRecyclerView(recyclerView)
//
//        val swipeArchive = object : SwipeToArchive(){
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position : Int = viewHolder.bindingAdapterPosition
//                val archivedTask : Task = viewModel.getSpecificTask(position)
//                viewModel.archiveItem(viewHolder.bindingAdapterPosition)
//                adapter.notifyDataSetChanged()
//                Snackbar.make(recyclerView, "It's okay, you can do it tomorrow", Snackbar.LENGTH_LONG)
//                    .setAction("Undo") { _ ->
//                        viewModel.addTaskToSpecificPosition(position, archivedTask)
//                        adapter.notifyItemInserted(position)
//
//                    }
//                    .show()
//            }
//        }
//        val touchHelper2 = ItemTouchHelper(swipeArchive)
//        touchHelper2.attachToRecyclerView(recyclerView)

//        viewModel.text.observe(viewLifecycleOwner, {
//            textView.text = it
//        })
        //Floating action button listener!
        _binding!!.fab.setOnClickListener { view ->
            addTaskDialog()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onTaskClick(position: Int) {
////        Toast.makeText(context, "Item $position clicked", Toast.LENGTH_SHORT).show()
////        val clickedItem : Task = allTasks[position]
//        editTaskDialog(position)
//    }
    private fun addTaskDialog(){
//        val editTaskDialog = EditTaskDialog()
//        activity?.supportFragmentManager?.let { editTaskDialog.show(it, "example dialog") }
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
                var newTask = DailyTask(0,editText.text.toString(),Calendar.getInstance().time,"Active",1,auth.currentUser.uid)
                viewDailyTasksModel.addDailyTask(newTask)
                ad.dismiss()
            }
        }
        cancel.setOnClickListener{
            ad.dismiss()
        }
//        builder.setPositiveButton("DONE") { _, _ ->
//            var newTask = Task(editText.text.toString())
//            viewModel.getTasks().add(newTask)
//        }
//        builder.setNegativeButton("CANCEL") { _, _ ->
//        }
//        builder.show()
    }
//    private fun editTaskDialog(position: Int){
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Edit Task")
//        val view = layoutInflater.inflate(R.layout.edit_task_dialog, null)
//        val editText = view.findViewById<EditText>(R.id.edit_text)
//        val done = view.findViewById<Button>(R.id.done)
//        val cancel = view.findViewById<Button>(R.id.cancel)
//        editText.setText(viewDailyTasksModel.getSpecificTask(position).text)
//        builder.setView(view)
//        val ad : AlertDialog = builder.show()
//        done.setOnClickListener {
//            if(editText.text.toString().trim().isEmpty()){
//                Snackbar.make(view, "You forgot to type your task!", Snackbar.LENGTH_SHORT).show()
//            }else{
//                viewModel.setTaskText(position, editText.text.toString())
//                adapter.notifyDataSetChanged()
//                ad.dismiss()
//            }
//        }
//        builder.setPositiveButton("DONE") { _, _ ->
//            viewModel.setTaskText(position, editText.text.toString())
//            adapter.notifyDataSetChanged()
//        }
//        builder.setNegativeButton("CANCEL") { _, _ ->
//        }
//        cancel.setOnClickListener{
//            ad.dismiss()
//        }
//        builder.show()
//    }

    fun tasksNumberUpdate(numOfTasks : Int){
        if(numOfTasks == 0){
            emptyFragmentMessage.visibility = View.VISIBLE
        }else{
            emptyFragmentMessage.visibility = View.INVISIBLE
        }

    }
}

