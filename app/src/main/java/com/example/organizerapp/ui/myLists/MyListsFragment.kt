package com.example.organizerapp.ui.myLists


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentMyListsBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MyListsFragment : Fragment() {

    private lateinit var myListsViewModel: MyListsViewModel
    private var _binding: FragmentMyListsBinding? = null
    private val myAdapter = MyListsAdapter{ list -> onClick(list)}
    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val title : String
//        val list : String
//        val id : Long
//        val let  = arguments?.getString("list")
//
//        val let1 = arguments?.getString("title")
//
//        val let2 = requireArguments().getLong("id")
//
//    }

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
             if( !it.isNullOrEmpty()){
                binding.messageEmptyList.visibility = View.INVISIBLE
            }else{
                binding.messageEmptyList.visibility = View.VISIBLE
            }
        }

      //  myAdapter.currentList.add(Lists(let.toString(),let1.toString(), let2 ))


        recyclerView.adapter = myAdapter


        //Floating action button listener!
        binding.fab.setOnClickListener {
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
        myListsViewModel.removeItemFromList(item.itemId.toLong())
        return super.onContextItemSelected(item)

    }

    //navigate to myListEditFragment
    private fun onClick(list: Lists){
        val bundle = Bundle()
            bundle.putLong("listId", list.id)
            println("list id" + list.id)
        view?.let { Navigation.findNavController(it).navigate(R.id.action_nav_my_list_to_myListEditFragment, bundle) }
    }



}