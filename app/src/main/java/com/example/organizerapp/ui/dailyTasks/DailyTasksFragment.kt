package com.example.organizerapp.ui.dailyTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentDailyTasksBinding
import com.google.android.material.snackbar.Snackbar

class DailyTasksFragment : Fragment() {

    private lateinit var galleryDailyTasksViewModel: DailyTasksViewModel
    private var _binding: FragmentDailyTasksBinding? = null
    private var allTasks = ArrayList<Task>()
//    val firebaseDatabase = FirebaseDataBase.getInstance()

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
        val recyclerView: RecyclerView = binding.tasksRecycler
        val textView: TextView = binding.textDailyTasks
        galleryDailyTasksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        //Floating action button listener!
        _binding!!.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //Adapter Specs
        recyclerView.hasFixedSize()
//        recyclerView.setLayoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = TaskAdapter(allTasks, R.layout.item_tasks)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Adapter
    class TaskAdapter(val tasks : List<Task>, val itemLayout : Int) : RecyclerView.Adapter<TaskViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(itemLayout, parent,false)
            return TaskViewHolder(view)
        }

        override fun getItemCount(): Int {
            return tasks.size
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            TODO("Not yet implemented")
        }
    }

    //ViewHolder
    class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var textOfTask: TextView?
        private var iconOfEdit: ImageView?
        init {
            textOfTask = itemView.findViewById<TextView>(R.id.taskText)
            iconOfEdit = itemView.findViewById<ImageView>(R.id.editIcon)
        }
    }
}