package com.example.organizerapp.ui.tomatoStats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.organizerapp.databinding.FragmentTomatoStatsBinding
import com.example.organizerapp.db.entities.DailyTask
import com.google.firebase.auth.FirebaseAuth

class TomatoStatsFragment : Fragment() {

    private lateinit var tomatoStatsViewModel: TomatoStatsViewModel
    private var _binding: FragmentTomatoStatsBinding? = null
    private lateinit var auth: FirebaseAuth
    private var dailyTasksList: Int = 0
    private var dailyTasksComList: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tomatoStatsViewModel =
            ViewModelProvider(this).get(TomatoStatsViewModel::class.java)

        _binding = FragmentTomatoStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTomatoStats
        tomatoStatsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        auth = FirebaseAuth.getInstance()

        tomatoStatsViewModel.getUncompletedDailyTaskGroupedByDate(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyStats ->
            var text = ""
            for(dailyTask in dailyStats){
                text += "Date: " + dailyTask.description + " Tasks: 0/" + dailyTask.dtid + '\n'
            }
            textView.text = text
        })

        tomatoStatsViewModel.getDailyTaskGroupedByDate(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyStats ->
            var text = textView.text.subSequence(0, 22).toString()
            var theRest = textView.text.subSequence(0, 24).toString()
            for(dailyTask in dailyStats){
                //TODO: To change the way the stats get displayed
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}