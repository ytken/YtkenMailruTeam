package ru.hse.dormitoryproject.friend_adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.friend_data.FriendItem

class FriendViewHolder(itemView: View, protected val listener: IListener) :
    RecyclerView.ViewHolder(itemView) {
    interface IListener {
        fun onFriendClicked(id: Int)
    }
    protected val mName: TextView

    init {
        mName = itemView.findViewById(R.id.item_name)

        val clickListener =
            View.OnClickListener { listener.onFriendClicked(adapterPosition) }
        itemView.setOnClickListener(clickListener)
    }

    fun bind(friend:FriendItem) {
        mName.text = friend.name
    }


}