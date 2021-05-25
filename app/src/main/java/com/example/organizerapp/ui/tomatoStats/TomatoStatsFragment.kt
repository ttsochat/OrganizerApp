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
import com.google.firebase.auth.FirebaseAuth

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

        tomatoStatsViewModel.getDailyTaskGroupedByDate(auth.currentUser.uid).observe(viewLifecycleOwner, Observer { dailyStats ->
            for(dailyStat in dailyStats)
                Log.e("GAY CUNT", "Entry: " + dailyStat)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}