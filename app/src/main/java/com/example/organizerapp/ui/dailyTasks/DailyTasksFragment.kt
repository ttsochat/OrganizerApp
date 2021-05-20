package com.example.organizerapp.ui.dailyTasks

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentDailyTasksBinding
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class DailyTasksFragment : Fragment(),  DailyTasksAdapter.OnTaskClickListener{

    private lateinit var galleryDailyTasksViewModel: DailyTasksViewModel
    private var _binding: FragmentDailyTasksBinding? = null
    val task1 = Task("task 1")
    val task2 = Task("task 2")
    val task3 = Task("task 3")
    private val allTasks = mutableListOf<Task>(task1, task2, task3)
    //val firebaseDatabase = FirebaseDataBase.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryDailyTasksViewModel =
            ViewModelProvider(this).get(DailyTasksViewModel::class.java)

        _binding = FragmentDailyTasksBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val adapter = DailyTasksAdapter(allTasks, this)

        val recyclerView: RecyclerView = binding.tasksRecycler

        recyclerView.adapter = adapter

        val textView: TextView = binding.textDailyTasks

        val info: ImageView = binding.info
        //set clicklistener for info


        val swipeDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder.bindingAdapterPosition)
                Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
            }

        }

        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(recyclerView)

        val swipeArchive = object : SwipeToArchive(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.archiveItem(viewHolder.bindingAdapterPosition)
                Toast.makeText(context, "Task archived, archived tasks: ${adapter.archivedTasksSize()}", Toast.LENGTH_SHORT).show()
            }
        }
        val touchHelper2 = ItemTouchHelper(swipeArchive)
        touchHelper2.attachToRecyclerView(recyclerView)

        galleryDailyTasksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        //Floating action button listener!
        _binding!!.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTaskClick(position: Int) {
//        Toast.makeText(context, "Item $position clicked", Toast.LENGTH_SHORT).show()
//        val clickedItem : Task = allTasks[position]
        openDialog()
    }
    fun openDialog(){
        val editTaskDialog = EditTaskDialog()
        activity?.supportFragmentManager?.let { editTaskDialog.show(it, "example dialog") }

    }
}