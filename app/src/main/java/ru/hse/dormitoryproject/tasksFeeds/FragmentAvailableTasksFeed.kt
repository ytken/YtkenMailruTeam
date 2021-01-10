package ru.hse.dormitoryproject.tasksFeeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.newsFeed.FragmentCreatePost
import ru.hse.dormitoryproject.newsFeed.FragmentWholePost

class FragmentAvailableTasksFeed : Fragment() {
    private var pageNumb = 0;

    // TODO: Добавить SwipeRefreshLayout и возможность обновления ленты

    companion object{
        const val ARG_PAGE = "ARG_PAGE"

        fun getNewInstance(pageNum : Int) : FragmentAvailableTasksFeed{
            var args = Bundle();
            args.putInt(ARG_PAGE, pageNum)
            val newFragment = FragmentAvailableTasksFeed()
            newFragment.arguments = args
            return newFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            pageNumb = requireArguments().getInt(ARG_PAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks_new_tasks, container, false)

        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        refresh.setColorScheme(R.color.first_refresh_color, R.color.second_refresh_color, R.color.third_refresh_color, R.color.fourth_refresh_color)
        refresh.setOnRefreshListener {
            // update feed
        }

        // Inflate the layout for this fragment
        view?.findViewById<RecyclerView>(R.id.feed_recycler)?.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = AvailableTaskAdapter(arrayListOf(TaskObject("some text here...", 30, "1","Jake", TaskObject.Status.NOT_SELECTED.ordinal, "19.11.2021","Bake a cake"), TaskObject("some text here...", 28, "2","Oleg", TaskObject.Status.NOT_SELECTED.ordinal, "23.4.2021","Help with math")),
                activity?.supportFragmentManager, ::showTask)
        }

        return view
    }

    private fun showTask(taskObject : TaskObject, fragmentManager  : FragmentManager?, notifier : ()->Unit){
        val fragmentWholePost = FragmentAvailableWholeTask(taskObject, notifier)
        if (fragmentManager != null) {
            fragmentWholePost.show(fragmentManager,"SHOW_POST")
        }
    }
}