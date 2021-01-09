package ru.hse.dormitoryproject.friend_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.friend_data.FriendItem


class FriendAdapter : Filterable, RecyclerView.Adapter<FriendViewHolder>, FriendViewHolder.IListener {
    protected var mData: List<FriendItem>

    lateinit var friendsList : MutableList<FriendItem>
    lateinit var friendsListAll : MutableList<FriendItem>
    lateinit var friendClickedListener: FriendClickedListener

    interface FriendClickedListener {
        fun friendClicked(friend:FriendItem)
    }

    constructor(data: List<FriendItem>, listener: FriendClickedListener) : super() {
        mData = data
        friendClickedListener = listener
        friendsList = mutableListOf()
        friendsListAll = mutableListOf()
        for (friend in mData) {
            friendsList.add(friend)
            friendsListAll.add(friend)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout: View = inflater.inflate(R.layout.item_search_profiles, parent, false)
        return FriendViewHolder(
            layout,
            this
        )
    }


    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val item = mData[position]
        holder.bind(item)
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    private var filter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: MutableList<FriendItem> = java.util.ArrayList()
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(friendsListAll)
            } else {
                for (friend in friendsListAll) {
                    if (friend.name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(friend)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            friendsList.clear()
            friendsList.addAll(results.values as Collection<FriendItem>)
            mData = friendsList
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    override fun onFriendClicked(id: Int) {
        friendClickedListener.friendClicked(mData.get(id))

    }

}