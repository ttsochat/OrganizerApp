package com.example.organizerapp.ui.dailyTasks

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.view.menu.MenuView
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
import kotlinx.coroutines.NonCancellable.cancel
import org.w3c.dom.Text

class DailyTasksFragment : Fragment(),  DailyTasksAdapter.OnTaskClickListener{

    private lateinit var viewModel: DailyTasksViewModel
    private var _binding: FragmentDailyTasksBinding? = null
    private lateinit var adapter: DailyTasksAdapter

    //val firebaseDatabase = FirebaseDataBase.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(DailyTasksViewModel::class.java)

        _binding = FragmentDailyTasksBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val recyclerView: RecyclerView = binding.tasksRecycler

        adapter = DailyTasksAdapter(viewModel.getTasks(), this)

        recyclerView.adapter = adapter

        val textView: TextView = binding.textDailyTasks

        val info = binding.info

        //set clicklistener for info
        info.setOnClickListener{
//            info.tooltiptext = "Swipe finished tasks right to delete & unfinished tasks left to save for later"
             Toast.makeText(context, "Swipe finished tasks right to delete & unfinished tasks left to save for later", Toast.LENGTH_LONG).show()

        }

        val swipeDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position : Int = viewHolder.bindingAdapterPosition
                val deletedTask : Task = viewModel.getTasks()[position]
                viewModel.getTasks().removeAt(position)
                adapter.notifyItemRemoved(position)
                Snackbar.make(recyclerView, "Good job, you finished this task!", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { _ ->
                        viewModel.getTasks().add(position, deletedTask)
                        adapter.notifyItemInserted(position)

                    }
                    .show()
            }
        }

        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(recyclerView)

        val swipeArchive = object : SwipeToArchive(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position : Int = viewHolder.bindingAdapterPosition
                val archivedTask : Task = viewModel.getTasks()[position]
                adapter.archiveItem(viewHolder.bindingAdapterPosition)
                Snackbar.make(recyclerView, "It's okay, you can do it tomorrow", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { _ ->
                        viewModel.getTasks().add(position, archivedTask)
                        adapter.notifyItemInserted(position)

                    }
                    .show()
            }
        }
        val touchHelper2 = ItemTouchHelper(swipeArchive)
        touchHelper2.attachToRecyclerView(recyclerView)

        viewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
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

    override fun onTaskClick(position: Int) {
//        Toast.makeText(context, "Item $position clicked", Toast.LENGTH_SHORT).show()
//        val clickedItem : Task = allTasks[position]
        editTaskDialog(position)
    }
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
            var newTask = Task(editText.text.toString())
            viewModel.getTasks().add(newTask)
            ad.dismiss()
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
        builder.show()
    }
    private fun editTaskDialog(position: Int){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Task")
        val view = layoutInflater.inflate(R.layout.edit_task_dialog, null)
        val editText = view.findViewById<EditText>(R.id.edit_text)
        val done = view.findViewById<Button>(R.id.done)
        val cancel = view.findViewById<Button>(R.id.cancel)
        editText.setText(viewModel.getTasks()[position].text)
        builder.setView(view)
        val ad : AlertDialog = builder.show()
        done.setOnClickListener {
            viewModel.setTaskText(position, editText.text.toString())
            adapter.notifyDataSetChanged()
            ad.dismiss()

        }
//        builder.setPositiveButton("DONE") { _, _ ->
//            viewModel.setTaskText(position, editText.text.toString())
//            adapter.notifyDataSetChanged()
//        }
//        builder.setNegativeButton("CANCEL") { _, _ ->
//        }
        cancel.setOnClickListener{
            ad.dismiss()
        }
//        builder.show()
    }
}