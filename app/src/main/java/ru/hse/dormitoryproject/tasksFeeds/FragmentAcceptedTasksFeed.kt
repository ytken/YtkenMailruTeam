package ru.hse.dormitoryproject.tasksFeeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.newsFeed.FragmentWholePost

class FragmentAcceptedTasksFeed : Fragment() {
    private var pageNumb = 0;

    // TODO: Добавить SwipeRefreshLayout и возможность обновления ленты

    companion object{
        const val ARG_PAGE = "ARG_PAGE"

        fun getNewInstance(pageNum : Int) : FragmentAcceptedTasksFeed{
            var args = Bundle();
            args.putInt(ARG_PAGE, pageNum)
            val newFragment = FragmentAcceptedTasksFeed()
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
        val view = inflater.inflate(R.layout.fragment_tasks_accepted, container, false)

        val refresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        refresh.setColorScheme(R.color.first_refresh_color, R.color.second_refresh_color, R.color.third_refresh_color, R.color.fourth_refresh_color)
        refresh.setOnRefreshListener {
            // update feed
        }

        // не доделал
        // Inflate the layout for this fragment
        view?.findViewById<RecyclerView>(R.id.feed_recycler)?.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = AcceptedTaskAdapter(arrayListOf(TaskObject("...", 30, "1","Me", TaskObject.Status.NOT_SELECTED.ordinal, "18.8.2021","Pls help me"), TaskObject("...", 28, "2","Me", TaskObject.Status.NOT_SELECTED.ordinal, "29.6.2021","Make me happy")),
                activity?.supportFragmentManager, ::showTask)
        }

        view?.findViewById<ImageButton>(R.id.feed_btn_new_post)?.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val fragmentNewTask = FragmentNewTask()
            if (fragmentManager != null) {
                fragmentNewTask.show(fragmentManager,"CREATE_TASK")
            }
        }

        return view
    }

    private fun showTask(taskObject : TaskObject, fragmentManager  : FragmentManager?, notifier : ()->Unit){
        val fragmentWholePost = FragmentAcceptedWholeTask(taskObject, notifier)
        if (fragmentManager != null) {
            fragmentWholePost.show(fragmentManager,"SHOW_POST")
        }
    }
}