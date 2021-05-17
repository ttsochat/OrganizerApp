package com.example.organizerapp.ui.dailyTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.organizerapp.databinding.FragmentDailyTasksBinding
import com.google.android.material.snackbar.Snackbar

class DailyTasksFragment : Fragment() {

    private lateinit var galleryDailyTasksViewModel: DailyTasksViewModel
    private var _binding: FragmentDailyTasksBinding? = null

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

        val textView: TextView = binding.textDailyTasks
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
}