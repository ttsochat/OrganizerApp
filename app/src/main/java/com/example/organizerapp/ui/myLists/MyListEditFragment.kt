package com.example.organizerapp.ui.myLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.organizerapp.R
import com.example.organizerapp.databinding.ListDetailsBinding
import com.example.organizerapp.db.entities.MyList
import com.google.android.material.snackbar.Snackbar

class MyListEditFragment: Fragment() {

    private lateinit var myListEditViewModel: MyListsViewModel
    private lateinit var binding: ListDetailsBinding
    private var listId : Int = -1
    private val defaultId : Int = -1
    private lateinit var userId : String
    private lateinit var currentTitle: String
    private lateinit var currentList: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("listId")?.let { lId ->
            this.listId = lId
        }
        arguments?.getString("userId")?.let{
            println("inside arguments!!")
            this.userId = it
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        myListEditViewModel =
            ViewModelProvider(this).get(MyListsViewModel::class.java)

        binding = ListDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(listId != defaultId){
            binding.editTextTitle.setText(myListEditViewModel.getCurrentListFromId(listId).title)
            binding.editTextList.setText(myListEditViewModel.getCurrentListFromId(listId).listDetails)
        }

        binding.listDoneImageButton.setOnClickListener {
            currentTitle = binding.editTextTitle.text.toString()
            currentList = binding.editTextList.text.toString()

            if(currentTitle == ""){
                Snackbar.make(binding.root,"At least choose a title! :)",2000).show()

            }else{
                if(listId == defaultId){
                    println("inside add new list!")
                    addNewList(currentTitle, currentList)
                }else{
                    updateList(currentTitle, currentList, listId)
                }

                Navigation.findNavController(root)
                    .navigate(R.id.action_myListEditFragment_to_nav_my_list)
            }

        }

        return root
    }



    private fun updateList(title: String, list: String, id: Int) {
        myListEditViewModel.updateList(title,list,id,userId)
    }


    fun addNewList(title: String, list: String){
        myListEditViewModel.addItemToList(MyList(0,title, list, userId))
    }
}

