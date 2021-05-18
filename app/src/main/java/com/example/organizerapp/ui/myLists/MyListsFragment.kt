package com.example.organizerapp.ui.myLists


import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentMyListsBinding
import com.google.android.material.snackbar.Snackbar

class MyListsFragment : Fragment() {

    private lateinit var myListsViewModel: MyListsViewModel
    private var _binding: FragmentMyListsBinding? = null
    private val myAdapter = MyListsAdapter{ list -> onClick(list)}
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myListsViewModel =
            ViewModelProvider(this).get(MyListsViewModel::class.java)

        _binding = FragmentMyListsBinding.inflate(inflater, container, false)

        val root: View = binding.root




        val recyclerView: RecyclerView = _binding!!.recyclerView

        //view model observer and adapter submit list!!
        myListsViewModel.getLists().observe(viewLifecycleOwner) {
             myAdapter.submitList(it)

        }

        recyclerView.adapter = myAdapter


        //Floating action button listener!
        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your fraction", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val bundle = Bundle()
            bundle.putLong("listId", -1)
            Navigation.findNavController(root).navigate(R.id.action_nav_my_list_to_myListEditFragment, bundle)

        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        println( "oncon" + item.itemId.toLong())
        myListsViewModel.removeItemFromList(item.itemId.toLong())
        return super.onContextItemSelected(item)

    }

    //navigate to myListEditFragment
    private fun onClick(list: Lists){
        val bundle = Bundle()
            bundle.putLong("listId", list.id)
        view?.let { Navigation.findNavController(it).navigate(R.id.action_nav_my_list_to_myListEditFragment, bundle) }
    }



}