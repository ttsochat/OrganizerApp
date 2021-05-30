package com.example.organizerapp.ui.myLists


import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R
import com.example.organizerapp.db.entities.MyList

/**
  MyListsAdapter class extents from ListAdapter and is responsible for converting data from the
  database to cardViews in order to display them on screen. It also takes as parameter a function
  given to the adapter via MyListFragment to add on click functionality to the card views.
 */
class MyListsAdapter (private val onClick: (MyList) -> Unit):
    ListAdapter<MyList, MyListsAdapter.ListsViewHolder>(ListsDiffCallBack){

    /**
        Overwritten function onCreateViewHolder inflates my_list_item layout to the adapter to display
        the data according to the cardViews design.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_list_item, parent,false)
        return ListsViewHolder(view, onClick)
    }

    /**
        Overwritten function onBindViewHolder connects new cardView crated by the ListViewHolder class
        to the recycler view of MyListFragment.
     */
    override fun onBindViewHolder(holder: ListsViewHolder, position: Int) {
        val currentCardView = getItem(position)
        holder.connect(currentCardView)

    }

    /**
        This class is the ViewHolder of the MyListsAdapter class.
        ListsViewHolder class extents from RecyclerView ViewHolder and View.OnCreateContextMenuListener
        in order to create every card View about to be displayed on screen and add to them a popup
        menu to give them extra functionalities such as Delete Item by by clicking popup menu option.
     */
    class ListsViewHolder(cardView : View, val onClick: (MyList) -> Unit) : RecyclerView.ViewHolder(cardView) ,
        View.OnCreateContextMenuListener {

        private val  title: TextView = cardView.findViewById(R.id.card_list_title)
        private val lists: TextView = cardView.findViewById(R.id.card_list_text)
        val  layout: LinearLayout = cardView.findViewById(R.id.my_list_item_layout)
        private var currentList: MyList? = null

        /**
            Initializing block for adding on click listener to card view and onCreateContextMenuListener
            on every new cardView.
         */
        init {
            cardView.setOnCreateContextMenuListener(this)
            cardView.setOnClickListener {
                currentList?.let {
                    onClick(it)
                }
            }
        }

        /**
            Function connect takes as parameter the list about to be displayed on screen and sets it
            title and list to the correct text Views of the cardView.
         */
        fun connect(list : MyList){
            currentList = list
            title.text = list.title
            lists.text = list.listDetails
        }


        /**
            Overwritten function onCreateContextMenu attaches a popup menu to every cardView with one
            option. Option is Delete List and it is used when user wants to permanently delete a list
            from the database.
         */
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

/**
    Object ListsDiffCallBack extents DiffUtil.ItemCallback and is used for updating a single item
    when this item is changes instead of updating the whole list of items displayed on screen.
 */
object ListsDiffCallBack: DiffUtil.ItemCallback<MyList>(){
    override fun areItemsTheSame(oldItem: MyList, newItem: MyList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MyList, newItem: MyList): Boolean {
       return oldItem.mlid == newItem.mlid
    }

}