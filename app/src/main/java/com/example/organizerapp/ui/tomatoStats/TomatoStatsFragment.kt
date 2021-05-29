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
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.databinding.FragmentTomatoStatsBinding
import com.example.organizerapp.db.entities.DailyTask
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class TomatoStatsFragment : Fragment(), TomatoStatsAdapter.OnTaskClickListener {

    private lateinit var tomatoStatsViewModel: TomatoStatsViewModel
    private var _binding: FragmentTomatoStatsBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var tomatoStatsAdapter: TomatoStatsAdapter

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

        var recyclerView: RecyclerView = binding.tomatoStatsRecycler
        tomatoStatsAdapter = TomatoStatsAdapter(this)
        recyclerView.adapter = tomatoStatsAdapter

        tomatoStatsViewModel.getUncompletedDailyTaskGroupedByDate(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyStats ->
            tomatoStatsAdapter.setUncData(dailyStats)

            tomatoStatsViewModel.getDailyTaskGroupedByDate(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyStats ->
                tomatoStatsAdapter.setComData(dailyStats)
            })
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTaskClick(position: Int) {
        TODO("Not yet implemented")
    }
}