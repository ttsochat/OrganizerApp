package com.example.organizerapp.ui.myLists

import android.icu.util.UniversalTimeScale.toLong
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.organizerapp.R
import com.example.organizerapp.databinding.ListDetailsBinding
import kotlin.random.Random

class MyListEditFragment: Fragment() {

    private lateinit var myListEditViewModel: MyListsViewModel
    private lateinit var binding: ListDetailsBinding
    private var listId : Long = -1
    private val defaultId : Long = -1
    private lateinit var currentTitle: String
    private lateinit var currentList: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getLong("listId")?.let { lId ->
            this.listId = lId
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myListEditViewModel =
            ViewModelProvider(this).get(MyListsViewModel::class.java)

        binding = ListDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(listId != defaultId){
            binding.editTextTitle.setText(myListEditViewModel.getCurrentListFromId(listId).title)
            binding.editTextList.setText(myListEditViewModel.getCurrentListFromId(listId).list)
        }

       // binding.editTextList.imeOptions = EditorInfo.IME_ACTION_NEXT

        //an mporesv na brw editorimfo gia allagh gragmhs!
        binding.editTextList.setOnEditorActionListener{ v, actionId, event ->
            if( actionId == EditorInfo.IME_FLAG_NO_ENTER_ACTION){
                println("ta kataferame")
                true
            }else{
                false
            }
        }



        binding.listDoneImageButton.setOnClickListener {
            currentTitle = binding.editTextTitle.text.toString()
            currentList = binding.editTextList.text.toString()
            val bundle = Bundle()
            bundle.putString("title", currentTitle)
            bundle.putString("list", currentList)
            bundle.putLong("id", Random.nextLong())
            myListEditViewModel.addItemToList(Lists(currentTitle, currentList, Random.nextLong()))

            Navigation.findNavController(root)
                .navigate(R.id.action_myListEditFragment_to_nav_my_list, bundle)
        }


        return root
    }



}

