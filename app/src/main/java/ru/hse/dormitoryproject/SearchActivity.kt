package ru.hse.dormitoryproject

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import ru.hse.dormitoryproject.friend_adapter.FriendAdapter
import ru.hse.dormitoryproject.friend_adapter.FriendViewHolder
import ru.hse.dormitoryproject.friend_data.FriendItem
import ru.hse.dormitoryproject.friend_data.FriendRepository

class SearchActivity : AppCompatActivity() {

    lateinit var curFragment : SearchProfilesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        curFragment = SearchProfilesFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_search, curFragment).addToBackStack(null).commit()
    }

}