package ru.hse.dormitoryproject.tasksFeeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase

class FragmentYourTasksFeed : Fragment() {
    private var pageNumb = 0;

    // TODO: Добавить SwipeRefreshLayout и возможность обновления ленты

    companion object{
        const val ARG_PAGE = "ARG_PAGE"

        fun getNewInstance(pageNum : Int) : FragmentYourTasksFeed{
            var args = Bundle();
            args.putInt(ARG_PAGE, pageNum)
            val newFragment = FragmentYourTasksFeed()
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
        var list = arrayListOf<TaskObject>()
        val view = inflater.inflate(R.layout.fragment_tasks_your, container, false)
        val adapter = YourTaskAdapter(list, activity?.supportFragmentManager, ::showTask)
        DataBase.readAllTasksPerformedByUser(list) { adapter.notifyDataSetChanged() }

        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        refresh.setColorScheme(R.color.first_refresh_color, R.color.second_refresh_color, R.color.third_refresh_color, R.color.fourth_refresh_color)
        refresh.setOnRefreshListener {
            DataBase.readAllTasksPerformedByUser(list) { adapter.notifyDataSetChanged() }
        }

        view?.findViewById<RecyclerView>(R.id.feed_recycler)?.apply {
            layoutManager = LinearLayoutManager(view.context)
            this.adapter = adapter
        }

        return view
    }

    private fun showTask(taskObject : TaskObject, fragmentManager  : FragmentManager?, notifier : ()->Unit){
        val fragmentWholePost = FragmentYourWholeTask(taskObject, notifier)
        if (fragmentManager != null) {
            fragmentWholePost.show(fragmentManager,"SHOW_POST")
        }
    }
}