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
import com.example.organizerapp.db.Converters
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class TomatoStatsFragment : Fragment() {

    private lateinit var tomatoStatsViewModel: TomatoStatsViewModel
    private var _binding: FragmentTomatoStatsBinding? = null
    private lateinit var auth: FirebaseAuth

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
        tomatoStatsViewModel.getDailyTaskGroupedByDate(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyStats ->
            var stats = ""
            for(dailyStat in dailyStats) {
                var rate = 5
                when {
                    dailyStat.dtid > 5 -> rate = 9
                    //TODO: Add/Change the rates
                }
                stats += dailyStat.description + "     Completed Tasks: " + dailyStat.dtid + '/' + rate + '\n'
                textView.text = stats
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}