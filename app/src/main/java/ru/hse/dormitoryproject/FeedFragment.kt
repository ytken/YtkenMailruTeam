package ru.hse.dormitoryproject

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.hse.dormitoryproject.Utils.DataBase
import ru.hse.dormitoryproject.newsFeed.FragmentCreatePost
import ru.hse.dormitoryproject.newsFeed.FragmentWholePost
import ru.hse.dormitoryproject.Utils.PostObject
import ru.hse.dormitoryproject.newsFeed.PostAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var posts : ArrayList<PostObject> = ArrayList()

    // TODO: Добавить SwipeRefreshLayout и возможность обновления ленты

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_feed, container, false)
        //var data = getPosts()
        val postAdapter = PostAdapter(posts, activity?.supportFragmentManager,::showPost)
        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        refresh.setColorScheme(R.color.first_refresh_color, R.color.second_refresh_color, R.color.third_refresh_color, R.color.fourth_refresh_color)

        val updateNewsFeed = {
            val sth = { postAdapter.notifyDataSetChanged();  refresh.isRefreshing = false}
            DataBase.readAllData(posts, sth)
        }

        updateNewsFeed.invoke()

        view?.findViewById<RecyclerView>(R.id.feed_recycler)?.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = postAdapter
        }


        val btn = view.findViewById<ImageButton>(R.id.feed_btn_new_post)
        btn.setOnClickListener {
            findNavController().navigate(R.id.createPostFragment)
        }

        refresh.setOnRefreshListener {
            updateNewsFeed.invoke()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.feed_menu, menu)
    }

    /**
     * Метод, который получает с сервера объекты типа PostObject, и возвращает все полученные объекты.
     */
    private fun getPosts(): ArrayList<PostObject> {
        var post = arrayListOf<PostObject>()
        // Получение записей с сервера
        post.add(
            PostObject(
                "Тестовый пост #1",
                "",
                "описание...",
                "01.01.0001",
                false
            )
        )
        post.add(
            PostObject(
                "Тестовый пост #2",
                "",
                "описание...",
                "01.01.0001",
                false
            )
        )
        post.add(
            PostObject(
                "Тестовый пост #3",
                "",
                "описание...",
                "01.01.0001",
                false
            )
        )

        return post
    }

    companion object FeedFragments{
        @JvmStatic
        fun myFyn(){

        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FeedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun showPost(postObject : PostObject, fragmentManager  : FragmentManager?, notifier : ()->Unit){
            val fragmentWholePost = FragmentWholePost(postObject, notifier)
            if (fragmentManager != null) {
                fragmentWholePost.show(fragmentManager,"SHOW_POST")
            }
        }
    }
}