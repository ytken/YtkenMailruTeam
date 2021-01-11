package ru.hse.dormitoryproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.Utils.DataBase
import ru.hse.dormitoryproject.Utils.PostObject
import ru.hse.dormitoryproject.favourites.FavPostAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [favouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        var data = arrayListOf<PostObject>()
        val postAdapter = FavPostAdapter(data, activity?.supportFragmentManager) { a, b, c ->
            FeedFragment.showPost(
                a,
                b,
                c
            )
        }

        val updateFavorites = {
            val sth = { postAdapter.notifyDataSetChanged()}
            DataBase.readAllFavorites(data,sth)
        }

        updateFavorites.invoke()


        view?.findViewById<RecyclerView>(R.id.feed_recycler)?.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = postAdapter
        }

        return view
    }


    // TODO: Add 'onSave(+Restore)InstanceState' to save instance after screen rotation. But before this we need local DB.

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment favouritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}