package com.example.organizerapp.ui.myLists


import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.organizerapp.R


class MyListsAdapter ( private val onClick: (Lists) -> Unit):
    ListAdapter<Lists, MyListsAdapter.ListsViewHolder>(ListsDiffCallBack)  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_list_item, parent,false)
        return ListsViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ListsViewHolder, position: Int) {
        val currentCardView = getItem(position)
        holder.connect(currentCardView)

    }

    fun removeItem(pos: Int){
        this.currentList.removeAt(pos)
        notifyDataSetChanged()
    }

    //View Holder
    class ListsViewHolder(cardView : View, val onClick: (Lists) -> Unit) : RecyclerView.ViewHolder(cardView) ,
        View.OnCreateContextMenuListener {

        val  title: TextView = cardView.findViewById(R.id.card_list_title)
        val lists: TextView = cardView.findViewById(R.id.card_list_text)
        val  constraintLayout: ConstraintLayout = cardView.findViewById(R.id.my_list_item_layout)
        private var currentList: Lists? = null

        //click listener for card view
        init {
            cardView.setOnCreateContextMenuListener(this)
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


        //attach context menu
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {

             currentList?.id?.toInt()?.let {
                 menu?.add(this.adapterPosition,
                     it, 111, "Delete List")
             }

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