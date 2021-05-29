package com.example.organizerapp.ui.myLists


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentMyListsBinding
import com.example.organizerapp.db.entities.MyList
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MyListsFragment : Fragment() {

    private lateinit var myListsViewModel: MyListsViewModel
    private var _binding: FragmentMyListsBinding? = null
    private val myAdapter = MyListsAdapter{ list -> onClick(list)}
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()

        myListsViewModel =
            ViewModelProvider(this).get(MyListsViewModel::class.java)

        _binding = FragmentMyListsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //view model observer and adapter submit list!!
        myListsViewModel.getMyListsByUserId(auth.currentUser.uid).observe(viewLifecycleOwner, Observer{
            var myList = mutableListOf<MyList>()
            for(list in it){
                myList.add(list)
            }
            myListsViewModel.setMyLists(myList)
            myAdapter.submitList(myList)
            showHideMessage(it)
        })
        val recyclerView: RecyclerView = _binding!!.recyclerView
        recyclerView.adapter = myAdapter


        //Floating action button listener!
        binding.fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("listId", -1)
            bundle.putString("userId", auth.currentUser.uid)
            Navigation.findNavController(root).navigate(R.id.action_nav_my_list_to_myListEditFragment, bundle)

        }

        //info button listener!
        binding.infoButton.setOnClickListener{
            Snackbar.make(binding.root,"Press and hold on a list to delete it.", 2000).show()
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //function for context menuItem selected to delete list
    override fun onContextItemSelected(item: MenuItem): Boolean {

        myListsViewModel.removeItemFromList(item.itemId)
        return super.onContextItemSelected(item)

    }

    //navigate to myListEditFragment
    private fun onClick(list: MyList){

        val bundle = Bundle()
        list.mlid?.let { bundle.putInt("listId", it) }
        bundle.putString("userId", auth.currentUser.uid)
        view?.let { Navigation.findNavController(it)
            .navigate(R.id.action_nav_my_list_to_myListEditFragment, bundle)}

    }


    private fun showHideMessage(list: List<MyList>){

        if( !list.isNullOrEmpty()){
            binding.messageEmptyList.visibility = View.INVISIBLE
        }else{
            binding.messageEmptyList.visibility = View.VISIBLE
        }

    }

}