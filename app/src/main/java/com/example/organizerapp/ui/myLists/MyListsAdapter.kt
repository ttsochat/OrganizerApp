package com.example.organizerapp.ui.myLists


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R

class MyListsAdapter ( private val onClick: (Lists) -> Unit):
    ListAdapter<Lists, MyListsAdapter.ListsViewHolder>(ListsDiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_list_item, parent,false)
        return ListsViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ListsViewHolder, position: Int) {
        val currentCardView = getItem(position)
        holder.connect(currentCardView)

    }


    class ListsViewHolder(cardView : View, val onClick: (Lists) -> Unit) : RecyclerView.ViewHolder(cardView) {

        val  title: TextView = cardView.findViewById(R.id.card_list_title)
        val lists: TextView = cardView.findViewById(R.id.card_list_text)
        val  constraintLayout: ConstraintLayout = cardView.findViewById(R.id.my_list_item_layout)
        private var currentList: Lists? = null

        //click listener for card view
        init {
            cardView.setOnClickListener {
                currentList?.let {
                    onClick(it)
                }
            }
        }

        fun connect(list : Lists){
            currentList = list
            title.text = list.title
            lists.text = list.list.toString()
        }
    }
}

object ListsDiffCallBack: DiffUtil.ItemCallback<Lists>(){
    override fun areItemsTheSame(oldItem: Lists, newItem: Lists): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Lists, newItem: Lists): Boolean {
       return oldItem.id == newItem.id
    }

}