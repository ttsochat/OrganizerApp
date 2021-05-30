package com.example.organizerapp.ui.myLists


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.databinding.FragmentMyListsBinding
import com.example.organizerapp.db.entities.MyList
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


/**
    Fragment to display and interact with user lists. This fragment uses a recycler view to display
    user lists on the screen. It is connected to MyListsViewModel to obtain the data necessary
    from the data base to submit them to the list adapter in order to be visible to the user.
    Also it observes the live data sent from the viewModel to have real time updates. At last it
    handles the navigation to MyListsEditFragment when a list if about to be edit of when a new list
    is created.
 */
class MyListsFragment : Fragment() {

    private lateinit var myListsViewModel: MyListsViewModel
    private var _binding: FragmentMyListsBinding? = null
    private val myAdapter = MyListsAdapter{ list -> onClick(list)}
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
        Ovewriten onCreateView method takes care of basic binding initialization, connects live data
        sent from the ViewModel to the recycler view adapter and also observes them in order to have
        real time updates displayed on screen. Also listen to events from the floating action button.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        auth = FirebaseAuth.getInstance()

        myListsViewModel =
            ViewModelProvider(this).get(MyListsViewModel::class.java)

        _binding = FragmentMyListsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //view model observer and adapter submit list!!
        myListsViewModel.getMyListsByUserId(auth.currentUser.uid).observe(viewLifecycleOwner, {
            val myList = mutableListOf<MyList>()
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

    /**
        Overwritten function onContextItemSElected reacts with the popup menu of every cardView-list
        and when Delete List option is selected from the user it removes the list from data base by
        calling MyListsViewModel function removeItemFromList.
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {

        myListsViewModel.removeItemFromList(item.itemId)
        return super.onContextItemSelected(item)

    }

    /**
      Function onClick navigates to MyListsEditFragment when an existing list is selected from the
      user.
     */
    private fun onClick(list: MyList){

        val bundle = Bundle()
        list.mlid.let { bundle.putInt("listId", it) }
        bundle.putString("userId", auth.currentUser.uid)
        view?.let { Navigation.findNavController(it)
            .navigate(R.id.action_nav_my_list_to_myListEditFragment, bundle)}

    }


    /**
        Function showHideMessage is responsible for updating messageEmptyList textView from visible
        to invisible when recycler view is empty or not.
     */
    private fun showHideMessage(list: List<MyList>){

        if( !list.isNullOrEmpty()){
            binding.messageEmptyList.visibility = View.INVISIBLE
        }else{
            binding.messageEmptyList.visibility = View.VISIBLE
        }

    }

}