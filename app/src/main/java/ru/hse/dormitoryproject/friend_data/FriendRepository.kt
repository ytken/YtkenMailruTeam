package ru.hse.dormitoryproject.friend_data

import android.util.Log
import java.util.*

class FriendRepository() {
    protected val mData: MutableList<FriendItem>

    init {
        mData = initializeData()
    }

    fun list(): List<FriendItem> {
        return mData
    }

    fun item(id: Int): FriendItem {
        for (friend in mData)
            if (friend.id == id){
                Log.v("My", id.toString() +" "+ friend.id)
                return friend}
        return FriendItem(0,"0")
    }

    fun size(): Int {
        return mData.size
    }

    protected fun initializeData(): MutableList<FriendItem> {
        val data: MutableList<FriendItem> = ArrayList()

        val friend1 = FriendItem(23, "Ann")
        val friend2 = FriendItem(34, "Serg")
        val friend3 = FriendItem(11, "Alex")
        val friend4 = FriendItem(99, "Den")
        data.add(friend1)
        data.add(friend2)
        data.add(friend3)
        data.add(friend4)
        return data
    }

    companion object {
        val instance = FriendRepository()
    }
}