package com.example.organizerapp.ui.myLists


import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.db.entities.MyList


class MyListsAdapter (private val onClick: (MyList) -> Unit):
    ListAdapter<MyList, MyListsAdapter.ListsViewHolder>(ListsDiffCallBack){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_list_item, parent,false)
        return ListsViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ListsViewHolder, position: Int) {
        val currentCardView = getItem(position)
        holder.connect(currentCardView)

    }


    //View Holder
    class ListsViewHolder(cardView : View, val onClick: (MyList) -> Unit) : RecyclerView.ViewHolder(cardView) ,
        View.OnCreateContextMenuListener {

        private val  title: TextView = cardView.findViewById(R.id.card_list_title)
        private val lists: TextView = cardView.findViewById(R.id.card_list_text)
        val  layout: LinearLayout = cardView.findViewById(R.id.my_list_item_layout)
        private var currentList: MyList? = null

        //click listener for card view
        init {
            cardView.setOnCreateContextMenuListener(this)
            cardView.setOnClickListener {
                currentList?.let {
                    onClick(it)
                }
            }
        }

        fun connect(list : MyList){
            currentList = list
            title.text = list.title
            lists.text = list.listDetails
        }


        //attach context menu
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
             currentList?.mlid?.let {
                 menu?.add(this.adapterPosition,
                     it, 111, "Delete List")
             }
        }

    }


}


object ListsDiffCallBack: DiffUtil.ItemCallback<MyList>(){
    override fun areItemsTheSame(oldItem: MyList, newItem: MyList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MyList, newItem: MyList): Boolean {
       return oldItem.mlid == newItem.mlid
    }

}