package ru.hse.dormitoryproject

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.friend_adapter.FriendAdapter
import ru.hse.dormitoryproject.friend_adapter.FriendViewHolder
import ru.hse.dormitoryproject.friend_data.FriendItem
import ru.hse.dormitoryproject.friend_data.FriendRepository


class SearchProfilesFragment : Fragment() {
    lateinit var recycler: RecyclerView
    lateinit var adapter : FriendAdapter


    override fun onDetach() {
        super.onDetach()
        activity?.finish()
    }

    fun newInstance(): SearchProfilesFragment{
        val args = Bundle()

        val fragment = this
        fragment.arguments = args
        return fragment
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_profiles, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.recycler_search)
        adapter = FriendAdapter(FriendRepository.instance.list(), object : FriendAdapter.FriendClickedListener {
            override fun friendClicked(friend: FriendItem) {
                val profileFragment = profileFragment.newInstance(friend)
                activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_search, profileFragment)
                        .addToBackStack(resources.getString(R.string.tag_profile_from_search))
                        .commit()
            }

        })
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val toolbarSearch = view.findViewById<Toolbar>(R.id.toolbar_search)
        val searchItem = toolbarSearch.menu.findItem(R.id.action_search)
        (searchItem.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        toolbarSearch?.title = resources.getString(R.string.search_title)
        toolbarSearch.setOnMenuItemClickListener() {
            when (it.itemId) {
                R.id.action_search -> {
                    val searchView = it!!.actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            adapter.filter.filter(newText)
                            return false
                        }
                    })
                }
                R.id.action_cancel -> {
                    activity?.finish()
                }
            }
        true
        }
    }

}