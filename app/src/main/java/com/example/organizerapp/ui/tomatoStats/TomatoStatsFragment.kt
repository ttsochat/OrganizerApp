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
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentTomatoStatsBinding
import com.example.organizerapp.ui.dailyTasks.DailyTasksViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.properties.Delegates

/**
 * TomatoStatsFragment is used for displaying the daily user's stats
 *
 */
class TomatoStatsFragment : Fragment(), TomatoStatsAdapter.OnTaskClickListener {

    private lateinit var viewDailyTasksModel: DailyTasksViewModel
    private lateinit var tomatoStatsViewModel: TomatoStatsViewModel
    private var _binding: FragmentTomatoStatsBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var tomatoStatsAdapter: TomatoStatsAdapter
    private var tasksDoneNum by Delegates.notNull<Int>()

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

        // This is  for statistics
        tomatoStatsViewModel.getUncompletedDailyTaskGroupedByDate(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyStats ->
            tomatoStatsAdapter.setUncData(dailyStats)
            if(dailyStats.size.compareTo(0)>0)
                textView.text = ""

            tomatoStatsViewModel.getDailyTaskGroupedByDate(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyStats ->
                tomatoStatsAdapter.setComData(dailyStats)
            })
        })

        // This is for the tomato bar
        val today: Calendar = GregorianCalendar()
        // reset hour, minutes, seconds and millis
        // reset hour, minutes, seconds and millis
        today[Calendar.HOUR_OF_DAY] = 0
        today[Calendar.MINUTE] = 0
        today[Calendar.SECOND] = 0
        today[Calendar.MILLISECOND] = 0
        viewDailyTasksModel = ViewModelProvider(this).get(DailyTasksViewModel::class.java)
        viewDailyTasksModel.getDailyTasksByUserId(auth.currentUser.uid).observe(viewLifecycleOwner, androidx.lifecycle.Observer { dailyTasks->
            tasksDoneNum=0
            var dailyListSize=0
            for(dailyTask in dailyTasks){
                if(today.time.compareTo(dailyTask.date)<0){
                    dailyListSize++
                }
                if(dailyTask.status.toString().equals("DONE") && today.time.compareTo(dailyTask.date)<0){
                    tasksDoneNum++
                }
            }
            tomatoBar(dailyListSize)
        })

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
     * Overwritten function onTaskClick.
     * It shows a Log when tomato stats item is clicked
     */
    override fun onTaskClick(position: Int) {
        Log.d("","clicked")
    }

    /**
     * It displays the tomatoes when a daily task is completed
     * on the tomatoBar on the top of the Fragment. It also contains
     * the algorithm that changes the ImageViews.
     */
    fun tomatoBar(allDailyTasks: Int){
        var tomato_precentage = binding.tomatoPercentage
        tomato_precentage.setText("$tasksDoneNum / $allDailyTasks")

        val tomato0 = binding.tomato0
        val tomato1 = binding.tomato1
        val tomato2 = binding.tomato2
        val tomato3 = binding.tomato3
        val tomato4 = binding.tomato4

        var tomatoes : IntArray = intArrayOf(0, 0, 0, 0, 0)
        var i : Int = 0
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
        var head : Int = 0;
        var i : Int = 0
        while (i < array.size){
            if (array[head] > array[i]){
                head = i;
            }
            i++
        }
        Arrays.fill(array, head + 1, array.size, 0)
        array[head]++
    }
}